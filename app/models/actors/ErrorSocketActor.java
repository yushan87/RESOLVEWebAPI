package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ErrorSocketActor extends UntypedActor {

    private final ActorRef myWebSocketOut;

    public ErrorSocketActor(ActorRef out) {
        myWebSocketOut = out;
        myWebSocketOut.tell("The specified 'job' and/or 'project' is not valid!", self());
        self().tell(PoisonPill.getInstance(), self());
    }

    public static Props props(ActorRef out) {
        return Props.create(ErrorSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}