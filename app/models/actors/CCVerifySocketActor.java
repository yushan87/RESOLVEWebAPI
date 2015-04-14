package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class CCVerifySocketActor extends UntypedActor {

    private final ActorRef myWebSocketOut;

    public CCVerifySocketActor(ActorRef out) {
        myWebSocketOut = out;
        myWebSocketOut.tell("CCVerify!", self());
        self().tell(PoisonPill.getInstance(), self());
    }

    public static Props props(ActorRef out) {
        return Props.create(GeneralSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}