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
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import play.libs.Json;

public class AnalyzeSocketActor extends AbstractSocketActor {

    public AnalyzeSocketActor(ActorRef out, String job, String project) {
        super(out, job, project);
    }

    static Props props(ActorRef out, String job, String project) {
        // https://doc.akka.io/docs/akka/current//actors.html
        return Props.create(AnalyzeSocketActor.class, () -> new AnalyzeSocketActor(out, job, project));
    }

    /*@Override
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
    }*/

}
