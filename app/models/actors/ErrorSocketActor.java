package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class ErrorSocketActor extends AbstractSocketActor {

    public ErrorSocketActor(ActorRef out) {
        super(out, "errorhandler");

        // Send the message through the websocket
        myWebSocketOut.tell("Undefined job request", self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

    public static Props props(ActorRef out) {
        return Props.create(ErrorSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) {
        // Should never get here
        throw new RuntimeException();
    }

}