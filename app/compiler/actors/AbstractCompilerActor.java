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
import java.io.File;
import org.slf4j.Logger;
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

    /** <p>Logger for Akka related items</p> */
    private final Logger myAkkaLogger;

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
    protected AbstractCompilerActor(ActorRef out, String job, String project,
            String workspacePath) {
        myAkkaLogger = org.slf4j.LoggerFactory.getLogger("akka");
        myJob = job;
        myProject = project;
        myWebSocketOut = out;
        myWorkspacePath = workspacePath;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method overrides overrides the default {@code unhandled}
     * method implementation.</p>
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

    // ===========================================================
    // Protected Methods
    // ===========================================================

    /**
     * <p>An helper method that notifies the user that some compiler
     * exception occurred.</p>
     *
     * @param e The {@link Exception} found while invoking
     *          the {@code RESOLVE} compiler.
     */
    protected final void notifyCompilerException(Exception e) {
        // Log this exception.
        myAkkaLogger.error("Compiler Exception: ", e);

        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg",
                "Unknown compiler exception. Please contact the administrators for support!");

        // Send the message through the websocket
        myWebSocketOut.tell(result, self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method for forming the specified project's
     * workspace path.</p>
     *
     * @return The project workspace path as a string.
     */
    private String formProjectWorkspacePath() {
        return myWorkspacePath + File.separator + myProject + File.separator + "RESOLVE" + File.separator + "Main" + File.separator;
    }

}