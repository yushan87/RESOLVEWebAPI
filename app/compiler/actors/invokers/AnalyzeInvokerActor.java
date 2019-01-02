/*
 * ---------------------------------
 * Copyright (c) 2019
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
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import compiler.actors.AbstractCompilerActor;
import compiler.inputmessage.CompilerMessage;
import edu.clemson.cs.rsrg.init.file.ResolveFile;
import edu.clemson.cs.rsrg.init.file.ResolveFileBasicInfo;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import play.libs.Json;

/**
 * <p>This class handles all request for analyzing a RESOLVE file, which is
 * simply populate and type check the file. This is mainly used to check to see
 * if a theory file is valid or not.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class AnalyzeInvokerActor extends AbstractCompilerActor {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates a new compiler job for analyzing a file.</p>
     *
     * @param out Outgoing end of the stream.
     * @param job Name of the job to be executed.
     * @param project RESOLVE project folder to be used.
     * @param workspacePath Path to all the RESOLVE workspaces.
     */
    private AnalyzeInvokerActor(ActorRef out, String job, String project,
            String workspacePath) {
        super(out, job, project, workspacePath);
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
     * @return An {@link AnalyzeInvokerActor}.
     */
    public static Props props(ActorRef out, String job, String project, String workspacePath) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(AnalyzeInvokerActor.class,
                () -> new AnalyzeInvokerActor(out, job, project, workspacePath));
    }

    /**
     * <p>This method overrides overrides the default {@code onReceive} method implementation
     * to handle.</p>
     *
     * @param message Message received by the input stream.
     */
    @Override
    public final void onReceive(Object message) {
        try {
            // Only deal with JsonNode
            if (message instanceof JsonNode) {
                // Validate the input message
                CompilerMessage compilerMessage =
                        Json.fromJson((JsonNode) message, CompilerMessage.class);
                List<String> errorMessages =
                        validateInputMessage(compilerMessage);

                // Only proceed if the validation step didn't generate an error message
                if (errorMessages.isEmpty()) {
                    // Send message to user about launching compiler job
                    notifyLaunchingCompilerJob();

                    // Convert the message into a file and
                    // add it to our user files map
                    String completeFileName =
                            compilerMessage.name
                                    + "."
                                    + getModuleType(compilerMessage.type)
                                            .getExtension();
                    myCompilingFilesMap.put(completeFileName,
                            buildInputResolveFile(compilerMessage));

                    // Setup items to be passed to the compiler
                    myCompilerArgs.add(completeFileName);

                    // Invoke the RESOLVE compiler
                    invokeResolveCompiler();

                    // Close the connection
                    self().tell(PoisonPill.getInstance(), ActorRef.noSender());
                }
                else {
                    // Send an error message back to user and close
                    // socket connection for all other types.
                    notifyMissingInputFields(errorMessages);
                }
            }
            else {
                // Send an error message back to user and close
                // socket connection for all other types.
                unhandled(message);
            }
        }
        catch (Exception e) {
            // Notify the user that some kind of exception occurred.
            notifyCompilerException(e);
        }
    }

    // ===========================================================
    // Protected Methods
    // ===========================================================

    /**
     * <p>An helper method that builds the input {@link ResolveFile} from
     * a {@link CompilerMessage}.</p>
     *
     * @param compilerMessage An input message.
     *
     * @return A {@link ResolveFile} representing the input message.
     */
    @Override
    protected final ResolveFile buildInputResolveFile(
            CompilerMessage compilerMessage) {
        return new ResolveFile(new ResolveFileBasicInfo(compilerMessage.name,
                compilerMessage.parent), getModuleType(compilerMessage.type),
                CharStreams.fromString(decode(compilerMessage.content)),
                Paths.get(formProjectWorkspacePath()), new ArrayList<>(), "");
    }

    /**
     * <p>An helper method that notifies the user that we have successfully
     * completed the compilation task.</p>
     */
    @Override
    protected final void notifyCompileSuccess() {
        ObjectNode result = Json.newObject();
        result.put("status", "complete");
        result.put("job", myJob);
        result.put(
                "result",
                "Done analyzing files: "
                        + Arrays.toString(myCompilingFilesMap.keySet()
                                .toArray()));

        // Send the message through the websocket
        myWebSocketOut.tell(result, self());
    }

    /**
     * <p>An helper method that validates an input message from the user
     * and adds any invalid fields to the return list.</p>
     *
     * @param compilerMessage An input message to be validated.
     *
     * @return A list of invalid fields
     */
    @Override
    protected final List<String> validateInputMessage(
            CompilerMessage compilerMessage) {
        List<String> invalidFields = new ArrayList<>();

        // Check to see if any of the fields are null or
        // don't match what we expect
        if (compilerMessage.name == null) {
            invalidFields.add("name");
        }

        if (compilerMessage.parent == null) {
            invalidFields.add("parent");
        }

        if (compilerMessage.type == null
                || getModuleType(compilerMessage.type) == null) {
            invalidFields.add("type");
        }

        if (compilerMessage.project == null
                || !compilerMessage.project.equals(myProject)) {
            invalidFields.add("project");
        }

        if (compilerMessage.content == null) {
            invalidFields.add("content");
        }

        return invalidFields;
    }

}