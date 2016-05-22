package controllers.compiler;

import akka.actor.ActorRef;
import edu.clemson.cs.r2jt.init2.ResolveCompiler;
import edu.clemson.cs.r2jt.init2.file.ResolveFile;
import java.util.Map;

public abstract class AbstractInvoker {

    protected final ResolveCompiler myCompilerInstance;
    protected final ActorRef myWebSocketOut;

    protected AbstractInvoker(String[] args, ActorRef out) {
        myCompilerInstance = new ResolveCompiler(args);
        myWebSocketOut = out;
    }

    public abstract void executeJob(Map<String, ResolveFile> fileMap);

}