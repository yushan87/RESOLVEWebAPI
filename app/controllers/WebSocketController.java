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

package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.typesafe.config.Config;
import compiler.actors.errorhandlers.*;
import compiler.actors.invokers.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;

/**
 * <p>This singleton class serves as the controller for handling the various
 * requests to the RESOLVE compiler.</p>
 *
 * <p>For more information, see:
 * <a href="https://www.playframework.com/documentation/2.6.x/JavaWebSockets">Play WebSockets</a></p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Singleton
public class WebSocketController extends Controller {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>An actor system that keeps track of all user requests</p> */
    private final ActorSystem myActorSystem;

    /** <p>Class that retrieves configurations</p> */
    private final Config myConfiguration;

    /** <p>A factory that makes the streams we create run.</p> */
    private final Materializer myStreamMaterializer;

    /**
     * <p>The directory where all the {@code RESOLVE} workspaces
     * are located.</p>
     */
    private final String myWorkspaceDir;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates an object for handling the various different
     * {@code WebSocket} requests to this application.</p>
     *
     * @param actorSystem An actor system for keeping track of all user requests.
     * @param materializer A factory that makes the streams we create run.
     */
    @Inject
    public WebSocketController(ActorSystem actorSystem,
            Materializer materializer, Config config) {
        myActorSystem = actorSystem;
        myConfiguration = config;
        myStreamMaterializer = materializer;
        myWorkspaceDir = myConfiguration.getString("webapi.workingdir");
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method creates a {@code WebSocket} for handling different compiler
     * requests.</p>
     *
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     *
     * @return A {@link WebSocket} object.
     */
    public final CompletionStage<WebSocket> socket(String job, String project) {
        WebSocket socket;

        // Check to see if that project folder exists
        if (projectExists(project)) {
            // Create the invokers to handle the specified job request.
            String lowercaseJob = job.toLowerCase();
            switch (lowercaseJob) {
                case "analyze":
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            AnalyzeInvokerActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
                case "buildjar":
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            JarInvokerActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
                case "ccverify":
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            CCVerifyInvokerActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
                case "genvcs":
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            VCInvokerActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
                case "translatejava":
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            TranslateJavaInvokerActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
                default:
                    socket = WebSocket.Json.accept(request ->
                            ActorFlow.actorRef(out ->
                                            JobNotSupportedActor.props(out, job, project, myWorkspaceDir),
                                    myActorSystem, myStreamMaterializer));
                    break;
            }
        }
        else {
            socket = WebSocket.Json.accept(request ->
                    ActorFlow.actorRef(out ->
                                    ProjectNotFoundActor.props(out, job, project, myWorkspaceDir),
                            myActorSystem, myStreamMaterializer));
        }

        return CompletableFuture.completedFuture(socket);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method that checks if the project name specified by
     * the user's request is valid.</p>
     *
     * @param project RESOLVE project folder to be used.
     *
     * @return {@code true} if a directory exists with that name,
     * {@code false} otherwise.
     */
    private boolean projectExists(String project) {
        return Files.exists(new File(myWorkspaceDir + File.separator + project).toPath(),
                LinkOption.NOFOLLOW_LINKS);
    }
}