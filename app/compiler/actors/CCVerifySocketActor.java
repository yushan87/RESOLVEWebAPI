/**
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
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * <p>This class handles all request for verifying a file.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class CCVerifySocketActor extends AbstractSocketActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new compiler job for verifying a file.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    public CCVerifySocketActor(ActorRef out, String job, String project, String workspacePath) {
        super(out, job, project, workspacePath);
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(CCVerifySocketActor.class,
                () -> new CCVerifySocketActor(out, job, project, workspacePath));
    }

    /*@Override
    public void onReceive(Object message) {
        try {
            // Only deal with Strings
            if (message instanceof String) {
                JsonNode request = Json.parse((String) message);

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
                self().tell(PoisonPill.getInstance(), self());
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
    }*/

}
