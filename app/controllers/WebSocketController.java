/*
 * ---------------------------------
 * Copyright (c) 2018
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
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import compiler.actors.invokers.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.libs.Json;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.WebSocket;

/**
 * <p>This singleton class serves as the controller for handling the various
 * requests to the RESOLVE compiler.</p>
 *
 * <p>For more information, see:
 * <a href="https://www.playframework.com/documentation/latest/JavaWebSockets">Play WebSockets</a></p>
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
        myStreamMaterializer = materializer;
        myWorkspaceDir = config.getString("webapi.workingdir");
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
    public final WebSocket socket(String job, String project) {
        // YS: As noted in the documentation, Play's WebSocket is built using Akka streams.
        //     Therefore, we are using the helper method that Play supplies to build
        //     a Flow of Json objects. To make things simpler, we have created Actors for
        //     each of the different compiler actions and add the various different messages
        //     depending on what we encounter.
        return WebSocket.Json.accept((Http.RequestHeader request) -> {
            // Check to see if that project folder exists
            if (projectExists(project)) {
                // Create the invokers to handle the specified job request.
                String lowercaseJob = job.toLowerCase();

                // Create a WebSocket using the appropriate compiler actor
                // to construct a flow.
                Flow<JsonNode, JsonNode, ?> flow;
                switch (lowercaseJob) {
                    case "analyze":
                        flow = ActorFlow.actorRef(out ->
                                        AnalyzeInvokerActor.props(out, job, project, myWorkspaceDir),
                                myActorSystem, myStreamMaterializer);
                        break;
                    case "buildjar":
                        flow = ActorFlow.actorRef(out ->
                                        JarInvokerActor.props(out, job, project, myWorkspaceDir),
                                myActorSystem, myStreamMaterializer);
                        break;
                    case "ccverify":
                        flow = ActorFlow.actorRef(out ->
                                        CCVerifyInvokerActor.props(out, job, project, myWorkspaceDir),
                                myActorSystem, myStreamMaterializer);
                        break;
                    case "genvcs":
                        flow = ActorFlow.actorRef(out ->
                                        VCInvokerActor.props(out, job, project, myWorkspaceDir),
                                myActorSystem, myStreamMaterializer);
                        break;
                    case "translatejava":
                        flow = ActorFlow.actorRef(out ->
                                        TranslateJavaInvokerActor.props(out, job, project, myWorkspaceDir),
                                myActorSystem, myStreamMaterializer);
                        break;
                    default:
                        flow = null;
                        break;
                }

                // Check to see if we have constructed the appropriate actor flow for handling
                // the specified job request.
                if (flow != null) {
                    return flow;
                }
                else {
                    // Ignore all input from the user
                    Sink<JsonNode, ?> in = Sink.ignore();

                    // Create an JSON object informing that the specified job is unsupported.
                    ObjectNode result = Json.newObject();
                    result.put("status", "error");
                    result.put("msg", "Unsupported job request: " + job);

                    // Send the message and close the socket
                    Source<JsonNode, ?> out = Source.single(result);

                    return Flow.fromSinkAndSource(in, out);
                }
            }
            else {
                // Ignore all input from the user
                Sink<JsonNode, ?> in = Sink.ignore();

                // Create an JSON object informing that the project was not found.
                ObjectNode result = Json.newObject();
                result.put("status", "error");
                result.put("msg", "Project not found: " + project);

                // Send the message and close the socket
                Source<JsonNode, ?> out = Source.single(result);

                return Flow.fromSinkAndSource(in, out);
            }
        });
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
        return Files.exists(
                new File(myWorkspaceDir + File.separator + project).toPath(),
                LinkOption.NOFOLLOW_LINKS);
    }
}