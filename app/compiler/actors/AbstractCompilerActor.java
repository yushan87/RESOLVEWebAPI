/*
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

package compiler.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.clemson.cs.rsrg.init.ResolveCompiler;
import play.libs.Json;

/**
 * <p>This is the abstract base class for all the {@code Actors}
 * that handle the RESOLVE compiler invocation or any error handlers.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public abstract class AbstractCompilerActor extends UntypedAbstractActor {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>This is the entry point for the RESOLVE compiler.</p> */
    protected ResolveCompiler myCompiler;

    /** <p>This indicates the name of the job to be executed.</p> */
    protected final String myJob;

    /** <p>This indicates which RESOLVE project folder to use.</p> */
    protected final String myProject;

    /** <p>This is the outgoing end of the stream.</p> */
    protected final ActorRef myWebSocketOut;

    /** <p>This indicates the path to all of our RESOLVE workspaces.</p> */
    protected final String myWorkspacePath;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>An helper constructor that stores all common objects for
     * classes that inherits from {@link AbstractCompilerActor}.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    protected AbstractCompilerActor(ActorRef out, String job, String project, String workspacePath) {
        myJob = job;
        myProject = project;
        myWebSocketOut = out;
        myWorkspacePath = workspacePath;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method overrides overrides the default {@code unhandled} method implementation.</p>
     *
     * @param message Message to be displayed.
     */
    @Override
    public final void unhandled(Object message) {
        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg", "Error while parsing request as a JSON Object!");

        // Send the message through the websocket
        myWebSocketOut.tell(result, self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

}