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

package compiler.actors.errorhandlers;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import compiler.actors.AbstractCompilerActor;
import play.libs.Json;

/**
 * <p>This class contains error logic for informing the client
 * request that the requested {@code project} is not found.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class ProjectNotFoundActor extends AbstractCompilerActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new actor for handling missing project
     * directories.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    public ProjectNotFoundActor(ActorRef out, String job, String project,
            String workspacePath) {
        super(out, job, project, workspacePath);

        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg", "Project not found: " + myProject);

        // Send the message through the websocket
        myWebSocketOut.tell(result, self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Props is a configuration object using in creating an {@code Actor};
     * It is immutable, so it is thread-safe and fully shareable.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     *
     * @return An {@link ProjectNotFoundActor}.
     */
    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(ProjectNotFoundActor.class,
                () -> new ProjectNotFoundActor(out, job, project, workspacePath));
    }

    /**
     * <p>This method overrides overrides the default {@code onReceive} method implementation
     * to handle.</p>
     *
     * @param message Message received by the input stream.
     */
    @Override
    public final void onReceive(Object message) {
        // Should never get here
        throw new RuntimeException();
    }

}