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

package compiler.impl;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.clemson.cs.rsrg.parsing.data.Location;
import edu.clemson.cs.rsrg.statushandling.StatusHandler;
import edu.clemson.cs.rsrg.statushandling.exception.CompilerException;
import org.json.JSONObject;
import play.libs.Json;

/**
 * <p>This class outputs all debugging, errors and/or
 * other information coming from the compiler to a WebSocket.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class WebSocketStatusHandler implements StatusHandler {

    // ===========================================================
    // Member Fields
    // ===========================================================

    /** <p>This is the {@link ActorRef} associated with one of our compiler actors.</p> */
    private final ActorRef myActorRef;

    /** <p>Boolean flag to check to see if we encountered an error.</p> */
    private boolean myErrorFlag;

    /** <p>Boolean flag to check to see if we are still logging.</p> */
    private boolean myStopLoggingFlag;

    /** <p>This is the outgoing end of the stream.</p> */
    private final ActorRef myWebSocketOut;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This constructor takes in two {@link ActorRef} objects
     * that will be used to send the various information, warning
     * and error messages provided by the compiler.</p>
     *
     * @param actorRef An {@link ActorRef} associated with an actor.
     * @param outRef The outgoing end of the stream.
     */
    public WebSocketStatusHandler(ActorRef actorRef, ActorRef outRef) {
        myActorRef = actorRef;
        myErrorFlag = false;
        myStopLoggingFlag = false;
        myWebSocketOut = outRef;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This method displays the error message passed in.</p>
     *
     * @param l The location where we encountered the error.
     * @param msg Message to be displayed.
     */
    @Override
    public final void error(Location l, String msg) {
        myErrorFlag = true;

        // Create a JSON Object that contains the info to
        // be sent to the user.
        ObjectNode info = Json.newObject();
        info.put("status", "error");
        info.put("msg", msg);

        // Add the location detail if needed.
        if (l != null) {
            info.set("msgLocation", locationAsJSON(l));
        }

        // Send the message through the WebSocket
        myWebSocketOut.tell(info, myActorRef);
    }

    /**
     * <p>Checks to see if the compiler has encountered an error.</p>
     *
     * @return {@code true} if there is an error, {@code false} otherwise.
     */
    public final boolean hasError() {
        return myErrorFlag;
    }

    /**
     * <p>Checks to see if we are still logging information.</p>
     *
     * @return {@code true} if we are done logging, {@code false} otherwise.
     */
    @Override
    public final boolean hasStopped() {
        return myStopLoggingFlag;
    }

    /**
     * <p>This method displays the information passed in.</p>
     *
     * @param l The location where we encountered the error.
     * @param msg Message to be displayed.
     */
    @Override
    public final void info(Location l, String msg) {
        // Create a JSON Object that contains the info to
        // be sent to the user.
        ObjectNode info = Json.newObject();
        info.put("status", "info");
        info.put("msg", msg);

        // Add the location detail if needed.
        if (l != null) {
            info.set("msgLocation", locationAsJSON(l));
        }

        // Send the message through the WebSocket
        myWebSocketOut.tell(info, myActorRef);
    }

    /**
     * <p>This method prints the stack trace to the desired output
     * stream.</p>
     *
     * @param e The encountered compiler exception.
     */
    @Override
    public final void printStackTrace(CompilerException e) {}

    /**
     * <p>Stop logging anymore information.
     *
     * (Note: Should only be called when the compile process
     * is over or has been aborted due to an error.)</p>
     */
    @Override
    public final void stopLogging() {
        myStopLoggingFlag = true;
    }

    /**
     * <p>This method displays compiler warning passed in.</p>
     *
     * @param l The location where we encountered the error.
     * @param msg Message to be displayed.
     */
    @Override
    public final void warning(Location l, String msg) {
        // Create a JSON Object that contains the info to
        // be sent to the user.
        ObjectNode info = Json.newObject();
        info.put("status", "warning");
        info.put("msg", msg);

        // Add the location detail if needed.
        if (l != null) {
            info.set("msgLocation", locationAsJSON(l));
        }

        // Send the message through the WebSocket
        myWebSocketOut.tell(info, myActorRef);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method that transforms a {@link Location} into
     * a JSON object.</p>
     *
     * @param l The location where we encountered the error.
     *
     * @return A {@link JSONObject} containing the location details.
     */
    private ObjectNode locationAsJSON(Location l) {
        ObjectNode location = Json.newObject();
        location.put("file", l.getFilename());
        location.put("line", l.getLine());
        location.put("column", l.getColumn());

        return location;
    }

}