/*
 * ---------------------------------
 * Copyright (c) 2017
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package compiler.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.clemson.cs.rsrg.init.ResolveCompiler;
import play.libs.Json;

import java.io.File;

/**
 * <p>This class handles all request for analyzing a RESOLVE file, which is
 * simply populate and type check the file. This is mainly used to check to see
 * if a theory file is valid or not.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class AnalyzeSocketActor extends AbstractSocketActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new compiler job for analyzing a file.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    public AnalyzeSocketActor(ActorRef out, String job, String project,
            String workspacePath) {
        super(out, job, project, workspacePath);
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Props is a configuration object using in creating an {@code Actor};
     * It is immutable, so it is thread-safe and fully shareable.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     *
     * @return An {@link AnalyzeSocketActor}.
     */
    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(AnalyzeSocketActor.class,
                () -> new AnalyzeSocketActor(out, job, project, workspacePath));
    }

    /**
     * <p>This method overrides overrides the default {@code onReceive} method implementation
     * to handle.</p>
     *
     * @param message Message received by the input stream.
     */
    @Override
    public final void onReceive(Object message) {
        try {
            // Only deal with JsonNode
            if (message instanceof JsonNode) {
                // Create a JSON Object that indicates we are done analyzing
                // the specified file.
                ObjectNode info = Json.newObject();
                info.put("status", "info");
                info.put("msg", "Launching compiler job: " + myJob);

                // Send the message through the websocket
                myWebSocketOut.tell(info, self());

                // Invoke compiler
                String filePath = "Integer_Theory.mt";
                String workspacePath =
                        myWorkspacePath + "RESOLVE" + File.separator + "Main"
                                + File.separator + "Math_Units"
                                + File.separator;
                String[] args =
                        { "-workspaceDir", workspacePath, "-noFileOutput",
                                filePath };
                myCompiler = new ResolveCompiler(args);
                myCompiler.invokeCompiler();

                // Create a JSON Object that indicates we are done analyzing
                // the specified file.
                ObjectNode result = Json.newObject();
                result.put("status", "complete");
                result.put("job", myJob);
                result.put("result", "");

                // Send the message through the websocket
                myWebSocketOut.tell(result, self());
            }
            else {
                // Send an error message back to user and close
                // socket connection for all other types.
                unhandled(message);
            }
        }
        catch (RuntimeException rte) {
            // Send an error message back to user and close
            // socket connection for all invalid JSON strings.
            unhandled(message);
        }
    }

}
