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
import compiler.impl.WebOutputListener;
import compiler.impl.WebSocketStatusHandler;
import edu.clemson.cs.rsrg.init.ResolveCompiler;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
import edu.clemson.cs.rsrg.init.output.OutputListener;
import edu.clemson.cs.rsrg.statushandling.StatusHandler;
import java.io.File;
import java.util.*;
import org.slf4j.Logger;
import play.libs.Json;

/**
 * <p>This is the abstract base class for all the {@code Actors}
 * that handle the {@code RESOLVE} compiler invocation or any error handlers.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public abstract class AbstractCompilerActor extends UntypedAbstractActor {

    // ===========================================================
    // Member Fields
    // ===========================================================

    // -----------------------------------------------------------
    // Compiler Actor-Related
    // -----------------------------------------------------------

    /** <p>Logger for Akka related items</p> */
    private final Logger myAkkaLogger;

    /** <p>This indicates the name of the job to be executed.</p> */
    private final String myJob;

    /** <p>This indicates which {@code RESOLVE} project folder to use.</p> */
    private final String myProject;

    /** <p>This is the outgoing end of the stream.</p> */
    private final ActorRef myWebSocketOut;

    // -----------------------------------------------------------
    // Compiler Argument-Related
    // -----------------------------------------------------------

    /** <p>This contains the arguments to be sent to the {@code RESOLVE} compiler.</p> */
    protected final List<String> myCompilerArgs;

    /** <p>This contains the user supplied {@link ResolveFile ResolveFiles}.</p> */
    protected final Map<String, ResolveFile> myFilesMap;

    /** <p>This is an implementation of the {@link OutputListener} for the {@code RESOLVE} compiler.</p> */
    private OutputListener myOutputListener;

    /** <p>This is an implementation of the {@link StatusHandler} for the {@code RESOLVE} compiler.</p> */
    private WebSocketStatusHandler myStatusHandler;

    /** <p>This indicates the path to all of our {@code RESOLVE} workspaces.</p> */
    private final String myWorkspacePath;

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
        myFilesMap = new LinkedHashMap<>();
        myJob = job;
        myProject = project;
        myWebSocketOut = out;
        myWorkspacePath = workspacePath;

        // Populate the common compiler arguments
        myCompilerArgs = new ArrayList<>();
        myCompilerArgs.add("-workspaceDir");
        myCompilerArgs.add(formProjectWorkspacePath());
        myCompilerArgs.add("-noFileOutput");
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method overrides overrides the default {@code postStop}
     * method implementation.</p>
     */
    @Override
    public final void postStop() {
        // If we haven't stopped logging in our status handler,
        // do it now!
        if (myStatusHandler != null && !myStatusHandler.hasStopped()) {
            myStatusHandler.stopLogging();
        }

        // Set these to null
        myStatusHandler = null;
        myOutputListener = null;
    }

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
     * <p>An helper method that invoke the {@code RESOLVE} compiler.</p>
     */
    protected final void invokeResolveCompiler() {
        myStatusHandler = new WebSocketStatusHandler(self(), myWebSocketOut);
        myOutputListener = new WebOutputListener(myStatusHandler);

        // Invoke the compiler
        ResolveCompiler compiler =
                new ResolveCompiler(myCompilerArgs.toArray(new String[0]));
        compiler.invokeCompiler(myFilesMap, myStatusHandler, myOutputListener);

        // Create a JSON Object that indicates we are done analyzing
        // the specified file if there are no error messages.
        if (!myStatusHandler.hasError()) {
            ObjectNode result = Json.newObject();
            result.put("status", "complete");
            result.put("job", myJob);
            result.put("result", "");

            // Send the message through the websocket
            myWebSocketOut.tell(result, self());
        }
    }

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
        self().tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    /**
     * <p>An helper method that notifies the user that we are
     * launching the compiler.</p>
     */
    protected final void notifyLaunchingCompilerJob() {
        // Create a JSON Object that indicates we are launching
        // the specified compiler job.
        ObjectNode info = Json.newObject();
        info.put("status", "info");
        info.put("msg", "Launching compiler job: " + myJob);

        // Send the message through the websocket
        myWebSocketOut.tell(info, self());
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
        return myWorkspacePath + File.separator + myProject + File.separator
                + "RESOLVE" + File.separator + "Main" + File.separator;
    }

}