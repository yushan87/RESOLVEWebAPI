package models.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public abstract class AbstractSocketActor extends UntypedActor {

    protected final String myJob;
    protected final ActorRef myWebSocketOut;

    protected AbstractSocketActor(ActorRef out, String job) {
        myJob = job;
        myWebSocketOut = out;
    }

    @Override
    public abstract void onReceive(Object message) throws Exception;

}