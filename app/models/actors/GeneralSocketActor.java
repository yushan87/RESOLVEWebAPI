package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class GeneralSocketActor extends UntypedActor {

    private final ActorRef myWebSocketOut;

    public GeneralSocketActor(ActorRef out) {
        myWebSocketOut = out;
        myWebSocketOut.tell("Handle General Errors!", self());
        self().tell(PoisonPill.getInstance(), self());
    }

    public static Props props(ActorRef out) {
        return Props.create(GeneralSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement (Probably won't ever get here)
    }

}