package controllers.compiler;

import akka.actor.ActorRef;

public class VCGeneratorInvoker extends AbstractInvoker {

    public VCGeneratorInvoker(String[] args, ActorRef out) {
        super(args, out);
    }
}