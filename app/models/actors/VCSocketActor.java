package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class VCSocketActor extends UntypedActor {

    private final ActorRef myWebSocketOut;

    public VCSocketActor(ActorRef out) {
        myWebSocketOut = out;
        myWebSocketOut.tell("VCs!", self());
        self().tell(PoisonPill.getInstance(), self());
    }

    public static Props props(ActorRef out) {
        return Props.create(VCSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}