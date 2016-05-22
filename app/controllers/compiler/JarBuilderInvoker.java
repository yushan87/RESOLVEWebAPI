package controllers.compiler;

import akka.actor.ActorRef;
import edu.clemson.cs.r2jt.init2.file.ResolveFile;
import java.util.Map;

public class JarBuilderInvoker extends AbstractInvoker {

    public JarBuilderInvoker(String[] args, ActorRef out) {
        super(args, out);
    }

    @Override
    public void executeJob(Map<String, ResolveFile> fileMap) {

    }

}