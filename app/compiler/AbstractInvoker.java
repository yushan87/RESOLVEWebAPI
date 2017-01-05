/**
 * ---------------------------------
 * Copyright (c) 2017
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package compiler;

import akka.actor.ActorRef;
import edu.clemson.cs.rsrg.init.ResolveCompiler;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
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