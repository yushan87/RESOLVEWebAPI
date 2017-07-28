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
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import javax.inject.Inject;
import play.Play;
import play.libs.Json;

public abstract class AbstractSocketActor extends UntypedActor {

    protected final String myJob;
    protected final String myProject;
    protected final ActorRef myWebSocketOut;
    protected final String myWorkspacePath;

    /** <p>Class that retrieves configurations</p> */
    @Inject
    private final Config myConfiguration;

    protected AbstractSocketActor(ActorRef out, String job, String project) {
        myJob = job;
        myProject = project;
        myWebSocketOut = out;
        myWorkspacePath = myConfiguration.getString("workingdir");
    }

    @Override
    public void unhandled(Object message) {
        // Create the error JSON Object
        ObjectNode result = Json.newObject();
        result.put("status", "error");
        result.put("msg", "Error while parsing request as a JSON Object!");

        // Send the message through the websocket
        myWebSocketOut.tell(result.toString(), self());

        // Close the connection
        self().tell(PoisonPill.getInstance(), self());
    }

}
