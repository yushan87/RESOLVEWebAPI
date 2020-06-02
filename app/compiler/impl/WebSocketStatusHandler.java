/*
 * ---------------------------------
 * Copyright (c) 2020
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
 * This class outputs all debugging, errors and/or other information coming from the compiler to a
 * WebSocket.
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class WebSocketStatusHandler implements StatusHandler {

  // ===========================================================
  // Member Fields
  // ===========================================================

  /** This is the {@link ActorRef} associated with one of our compiler actors. */
  private final ActorRef myActorRef;

  /** Boolean flag to check to see if we encountered an error. */
  private boolean myErrorFlag;

  /** Boolean flag to check to see if we are still logging. */
  private boolean myStopLoggingFlag;

  /** This is the outgoing end of the stream. */
  private final ActorRef myWebSocketOut;

  // ===========================================================
  // Constructors
  // ===========================================================

  /**
   * This constructor takes in two {@link ActorRef} objects that will be used to send the various
   * information, warning and error messages provided by the compiler.
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
   * This method displays the error message passed in.
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
   * Checks to see if the compiler has encountered an error.
   *
   * @return {@code true} if there is an error, {@code false} otherwise.
   */
  public final boolean hasError() {
    return myErrorFlag;
  }

  /**
   * Checks to see if we are still logging information.
   *
   * @return {@code true} if we are done logging, {@code false} otherwise.
   */
  @Override
  public final boolean hasStopped() {
    return myStopLoggingFlag;
  }

  /**
   * This method displays the information passed in.
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
   * This method prints the stack trace to the desired output stream.
   *
   * @param e The encountered compiler exception.
   */
  @Override
  public final void printStackTrace(CompilerException e) {}

  /**
   * Stop logging anymore information.
   *
   * <p>(Note: Should only be called when the compile process is over or has been aborted due to an
   * error.)
   */
  @Override
  public final void stopLogging() {
    myStopLoggingFlag = true;
  }

  /**
   * This method displays compiler warning passed in.
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
   * An helper method that transforms a {@link Location} into a JSON object.
   *
   * @param l The location where we encountered the error.
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
