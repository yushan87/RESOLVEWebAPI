package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class TranslateSocketActor extends AbstractSocketActor {

    public TranslateSocketActor(ActorRef out) {
        super(out);
    }

    public static Props props(ActorRef out) {
        return Props.create(TranslateSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}