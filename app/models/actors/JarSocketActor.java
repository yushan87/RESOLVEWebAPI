package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class JarSocketActor extends AbstractSocketActor {

    public JarSocketActor(ActorRef out) {
        super(out, "buildjar");
    }

    public static Props props(ActorRef out) {
        return Props.create(JarSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}