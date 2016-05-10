package controllers.compiler;

import akka.actor.ActorRef;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
import java.util.Map;

public class JavaTranslatorInvoker extends AbstractInvoker {

    public JavaTranslatorInvoker(String[] args, ActorRef out) {
        super(args, out);
    }

    @Override
    public void executeJob(Map<String, ResolveFile> fileMap) {

    }

}