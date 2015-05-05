package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class CCVerifySocketActor extends AbstractSocketActor {

    public CCVerifySocketActor(ActorRef out) {
        super(out, "ccverify");
    }

    public static Props props(ActorRef out) {
        return Props.create(CCVerifySocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}