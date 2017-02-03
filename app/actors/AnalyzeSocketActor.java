/**
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
package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import com.fasterxml.jackson.databind.JsonNode;
import compiler.TheoryAnalyzeInvoker;
import java.util.HashMap;
import play.libs.Json;

public class AnalyzeSocketActor extends AbstractSocketActor {

    public AnalyzeSocketActor(ActorRef out, String job, String project) {
        super(out, job, project);
    }

    public static Props props(ActorRef out, String job, String project) {
        // http://doc.akka.io/docs/akka/2.4/java/untyped-actors.html
        return Props.create(new Creator<AnalyzeSocketActor>() {

            private static final long serialVersionUID = 1L;

            @Override
            public AnalyzeSocketActor create() throws Exception {
                return new AnalyzeSocketActor(out, job, project);
            }
        });
    }

    @Override
    public void onReceive(Object message) {
        try {
            // Only deal with Strings
            if (message instanceof String) {
                JsonNode request = Json.parse((String) message);
                String[] args =
                        { "-main", myWorkspacePath, "-webinterface", "Test.mt" };
                TheoryAnalyzeInvoker invoker =
                        new TheoryAnalyzeInvoker(args, myWebSocketOut);
                invoker.executeJob(new HashMap<>());
            }
            else {
                // Send an error message back to user and close
                // socket connection for all other types.
                unhandled(message);
            }
        }
        catch (RuntimeException rte) {
            // Send an error message back to user and close
            // socket connection for all invalid JSON strings.
            unhandled(message);
        }
    }

}