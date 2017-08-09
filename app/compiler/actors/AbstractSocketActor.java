/*
 *  ---------------------------------
 *  Copyright (c) 2017
 *  RESOLVE Software Research Group
 *  School of Computing
 *  Clemson University
 *  All rights reserved.
 *  ---------------------------------
 *  This file is subject to the terms and conditions defined in
 *  file 'LICENSE.txt', which is part of this source code package.
 */

package compiler.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * <p>This is the abstract base class for all the {@code Actors}
 * that handle the RESOLVE compiler invocation.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public abstract class AbstractSocketActor extends UntypedAbstractActor {

    // ===========================================================
    // Member Fields
    // ===========================================================

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
     * classes that inherits from {@link AbstractSocketActor}.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    protected AbstractSocketActor(ActorRef out, String job, String project,
            String workspacePath) {
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
        myWebSocketOut.tell(result.toString(), self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

}
