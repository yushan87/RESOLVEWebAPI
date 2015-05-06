package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public abstract class AbstractSocketActor extends UntypedActor {

    protected final String myJob;
    protected final ActorRef myWebSocketOut;

    protected AbstractSocketActor(ActorRef out, String job) {
        myJob = job;
        myWebSocketOut = out;
    }

    @Override
    public void unhandled(Object message) {
        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg", "Error while parsing request as a JSON Object!");

        // Send the message through the websocket
        myWebSocketOut.tell(result.toString(), self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

}