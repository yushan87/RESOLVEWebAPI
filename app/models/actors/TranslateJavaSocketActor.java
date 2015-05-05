package models.actors;

import akka.actor.ActorRef;
import akka.actor.Props;

public class TranslateJavaSocketActor extends AbstractSocketActor {

    public TranslateJavaSocketActor(ActorRef out) {
        super(out, "translatejava");
    }

    public static Props props(ActorRef out) {
        return Props.create(TranslateJavaSocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        // TODO: Need to implement
    }

}