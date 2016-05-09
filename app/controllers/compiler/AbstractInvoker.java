package controllers.compiler;

import edu.clemson.cs.rsrg.init.ResolveCompiler;

public abstract class AbstractInvoker {

    protected final ResolveCompiler myCompilerInstance;

    protected AbstractInvoker(String[] args) {
        myCompilerInstance = new ResolveCompiler(args);
    }

}