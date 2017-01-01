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
package controllers.compiler;

import edu.clemson.cs.rsrg.init.ResolveCompiler;

public abstract class AbstractInvoker {

    protected final ResolveCompiler myCompilerInstance;

    protected AbstractInvoker(String[] args) {
        myCompilerInstance = new ResolveCompiler(args);
    }

}