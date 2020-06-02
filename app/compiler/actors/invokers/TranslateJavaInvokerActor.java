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
package compiler.actors.invokers;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import compiler.actors.AbstractCompilerActor;
import compiler.inputmessage.CompilerMessage;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all request for translating a file to Java.
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class TranslateJavaInvokerActor extends AbstractCompilerActor {

  // ===========================================================
  // Constructors
  // ===========================================================

  /**
   * This creates a new compiler job for translating a file to Java.
   *
   * @param out Outgoing end of the stream.
   * @param job Name of the job to be executed.
   * @param project RESOLVE project folder to be used.
   * @param workspacePath Path to all the RESOLVE workspaces.
   */
  public TranslateJavaInvokerActor(ActorRef out, String job, String project, String workspacePath) {
    super(out, job, project, workspacePath);
  }

  // ===========================================================
  // Public Methods
  // ===========================================================

  /**
   * Props is a configuration object using in creating an {@code Actor}; It is immutable, so it is
   * thread-safe and fully shareable.
   *
   * @param out Outgoing end of the stream.
   * @param job Name of the job to be executed.
   * @param project RESOLVE project folder to be used.
   * @param workspacePath Path to all the RESOLVE workspaces.
   * @return A {@link TranslateJavaInvokerActor}.
   */
  public static Props props(ActorRef out, String job, String project, String workspacePath) {
    // https://doc.akka.io/docs/akka/current//actors.html
    return Props.create(
        TranslateJavaInvokerActor.class,
        () -> new TranslateJavaInvokerActor(out, job, project, workspacePath));
  }

  /**
   * This method overrides overrides the default {@code onReceive} method implementation to handle.
   *
   * @param message Message received by the input stream.
   */
  @Override
  public final void onReceive(Object message) {
    try {
      // Only deal with JsonNode
      if (message instanceof JsonNode) {
        /*JsonNode request = Json.parse((String) message);

        // Create a JSON Object informing we are starting the job
        ObjectNode result = Json.newObject();
        result.put("status", "info");
        result.put(
                "msg",
                "Received request with the following parameters: "
                        + myJob
                        + " and "
                        + myProject
                        + ". Launching the RESOLVE compiler with the specified arguments.");

        // Send the message through the websocket
        myWebSocketOut.tell(result.toString(), self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());*/
      } else {
        // Send an error message back to user and close
        // socket connection for all other types.
        unhandled(message);
      }
    } catch (Exception e) {
      // Notify the user that some kind of exception occurred.
      notifyCompilerException(e);
    }
  }

  // ===========================================================
  // Protected Methods
  // ===========================================================

  /**
   * An helper method that builds the input {@link ResolveFile} from a {@link CompilerMessage}.
   *
   * @param compilerMessage An input message.
   * @return A {@link ResolveFile} representing the input message.
   */
  @Override
  protected final ResolveFile buildInputResolveFile(CompilerMessage compilerMessage) {
    return null;
  }

  /**
   * An helper method that validates an input message from the user and adds any invalid fields to
   * the return list.
   *
   * @param compilerMessage An input message to be validated.
   * @return A list of invalid fields
   */
  @Override
  protected final List<String> validateInputMessage(CompilerMessage compilerMessage) {
    return new ArrayList<>();
  }
}
