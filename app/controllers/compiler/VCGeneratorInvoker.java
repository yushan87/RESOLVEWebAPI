package controllers.compiler;

import akka.actor.ActorRef;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
import java.util.Map;

public class VCGeneratorInvoker extends AbstractInvoker {

    public VCGeneratorInvoker(String[] args, ActorRef out) {
        super(args, out);
    }

    @Override
    public void executeJob(Map<String, ResolveFile> fileMap) {

    }

}