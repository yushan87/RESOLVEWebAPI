package controllers.compiler;

import edu.clemson.cs.r2jt.init2.ResolveCompiler;

public abstract class AbstractInvoker {

    protected final ResolveCompiler myCompilerInstance;

    protected AbstractInvoker(String[] args) {
        myCompilerInstance = new ResolveCompiler(args);
    }


}