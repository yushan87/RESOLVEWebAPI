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
package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.WebSocket;

/**
 * <p>This singleton class serves as the controller for handling the various
 * requests to the RESOLVE compiler.</p>
 *
 * <p>For more information, see: <a href=http://doc.akka.io/docs/akka/2.4/AkkaJava.pdf>AkkaJava</a></p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Singleton
public class RESOLVECompilerAPI extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>An actor system that keeps track of all user requests</p> */
    private ActorSystem myActorSystem;

    /** <p>Logger for Akka related items</p> */
    private Logger myAkkaLogger = org.slf4j.LoggerFactory.getLogger("akka");

    /** <p>Class that retrieves configurations</p> */
    @Inject
    private Configuration myConfiguration;

    /** <p>A factory that makes the streams we create run</p> */
    private Materializer myStreamMaterializer;

    // ===========================================================
    // Constructors
    // ===========================================================

    @Inject
    public RESOLVECompilerAPI(ActorSystem actorSystem, Materializer materializer) {
        myActorSystem = actorSystem;
        myStreamMaterializer = materializer;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    public WebSocket socket(String job, String project) {
        return null;
    }

    /*public LegacyWebSocket<String> socket(String job, String project) {
        WebSocket newRetVal = WebSocket.Text.accept(requestHeader -> {
            return Flow.of(String.class);
        });
    	// Still need to figure out how to create a Flow object
    	//return WebSocket.Text.accept(requestHeader -> {
    	// return a Flow<String, String, ?>
    	//});
    	
        LegacyWebSocket<String> retVal;
        String lowercaseJob = job.toLowerCase();

        if (lowercaseJob.equals("analyze")) {
            retVal = WebSocket.withActor(a -> AnalyzeSocketActor.props(a, lowercaseJob, project));
        }
        else if (lowercaseJob.equals("buildjar")) {
            retVal = WebSocket.withActor(a -> JarSocketActor.props(a, lowercaseJob, project));
        }
        else if (lowercaseJob.equals("ccverify")) {
            retVal = WebSocket.withActor(a -> CCVerifySocketActor.props(a, lowercaseJob, project));
        }
        else if (lowercaseJob.equals("genvcs")) {
            retVal = WebSocket.withActor(a -> VCSocketActor.props(a, lowercaseJob, project));
        }
        else if (lowercaseJob.equals("translatejava")) {
            retVal = WebSocket.withActor(a -> TranslateJavaSocketActor.props(a, lowercaseJob, project));
        }
        else {
            retVal = WebSocket.withActor(a -> ErrorSocketActor.props(a, lowercaseJob, project));
        }

        return retVal;
    }*/
}