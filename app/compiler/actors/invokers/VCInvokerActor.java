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
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import compiler.actors.AbstractCompilerActor;

/**
 * <p>This class handles all request for generating VCs.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class VCInvokerActor extends AbstractCompilerActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new compiler job for generating VCs.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    public VCInvokerActor(ActorRef out, String job, String project, String workspacePath) {
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
     * @return A {@link VCInvokerActor}.
     */
    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(VCInvokerActor.class,
                () -> new VCInvokerActor(out, job, project, workspacePath));
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
                /*JsonNode request = Json.parse((String) message);

                // Create a JSON Object informing we are starting the job
                ObjectNode result = Json.newObject();
                result.put("status", "info");
                result.put(
                        "msg",
                        "Received request with the following parameters: "
                                + myJob
                                + " and "
                                + myProject
                                + ". Launching the RESOLVE compiler with the specified arguments.");

                // Send the message through the websocket
                myWebSocketOut.tell(result.toString(), self());

                // Close the connection
                self().tell(PoisonPill.getInstance(), self());*/
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
