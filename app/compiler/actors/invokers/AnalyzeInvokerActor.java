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

package compiler.actors.invokers;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import compiler.actors.AbstractCompilerActor;
import java.io.File;

/**
 * <p>This class handles all request for analyzing a RESOLVE file, which is
 * simply populate and type check the file. This is mainly used to check to see
 * if a theory file is valid or not.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class AnalyzeInvokerActor extends AbstractCompilerActor {

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
    public AnalyzeInvokerActor(ActorRef out, String job, String project,
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
     * @return An {@link AnalyzeInvokerActor}.
     */
    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(AnalyzeInvokerActor.class,
                () -> new AnalyzeInvokerActor(out, job, project, workspacePath));
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
                // Send message to user about launching compiler job
                notifyLaunchingCompilerJob();

                // Setup items to be passed to the compiler
                myCompilerArgs.add("Math_Units" + File.separator
                        + "Integer_Theory.mt");

                // Invoke the RESOLVE compiler
                invokeResolveCompiler();

                // Close the connection
                self().tell(PoisonPill.getInstance(), ActorRef.noSender());
            }
            else {
                // Send an error message back to user and close
                // socket connection for all other types.
                unhandled(message);
            }
        }
        catch (Exception e) {
            // Notify the user that some kind of exception occurred.
            notifyCompilerException(e);
        }
    }

}