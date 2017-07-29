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
package actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * <p>This class contains error logic for handling miscellaneous job
 * requests that are not supported by the current WebAPI.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class ErrorSocketActor extends AbstractSocketActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new actor for handling erroneous
     * compiler jobs.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     */
    public ErrorSocketActor(ActorRef out, String job, String project) {
        super(out, job, project);

        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg", "Undefined job request");

        // Send the message through the websocket
        myWebSocketOut.tell(result.toString(), self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    public static Props props(ActorRef out, String job, String project) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(ErrorSocketActor.class, () -> new ErrorSocketActor(out, job, project));
    }

    /*@Override
    public void onReceive(Object message) {
        // Should never get here
        throw new RuntimeException();
    }*/

}
