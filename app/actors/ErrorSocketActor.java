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
import akka.japi.Creator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class ErrorSocketActor extends AbstractSocketActor {

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

    public static Props props(ActorRef out, String job, String project) {
        // http://doc.akka.io/docs/akka/snapshot/java/untyped-actors.html
        return Props.create(new Creator<ErrorSocketActor>() {

            private static final long serialVersionUID = 1L;

            @Override
            public ErrorSocketActor create() throws Exception {
                return new ErrorSocketActor(out, job, project);
            }
        });
    }

    @Override
    public void onReceive(Object message) {
        // Should never get here
        throw new RuntimeException();
    }

}