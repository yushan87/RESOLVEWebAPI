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

import akka.stream.javadsl.Flow;
import actors.*;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public LegacyWebSocket<String> socket(String job, String project) {
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
    }
}