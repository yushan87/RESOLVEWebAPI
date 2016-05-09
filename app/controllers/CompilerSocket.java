package controllers;

import models.actors.*;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public LegacyWebSocket<String> socket(String job, String project) {
        LegacyWebSocket<String> retVal;
        String lowercaseJob = job.toLowerCase();

        if (lowercaseJob.equals("buildjar")) {
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