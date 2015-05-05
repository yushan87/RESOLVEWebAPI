package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ErrorSocketActor extends AbstractSocketActor {

    public ErrorSocketActor(ActorRef out) {
        super(out);
    }

    public static Props props(ActorRef out) {
        return Props.create(ErrorSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}