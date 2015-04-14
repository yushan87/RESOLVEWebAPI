package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class JarSocketActor extends UntypedActor {

    private final ActorRef myWebSocketOut;

    public JarSocketActor(ActorRef out) {
        myWebSocketOut = out;
        myWebSocketOut.tell("Please tell me your name!", self());
    }

    public static Props props(ActorRef out) {
        return Props.create(GeneralSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            myWebSocketOut.tell("Hi " + message + "!", self());
            self().tell(PoisonPill.getInstance(), self());
        }
    }

}