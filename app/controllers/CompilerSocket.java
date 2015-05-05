package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.actors.*;
import play.mvc.Controller;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public static WebSocket<JsonNode> socket(String job) {
        WebSocket<JsonNode> retVal;
        String lowercaseJob = job.toLowerCase();

        if (lowercaseJob.equals("buildjar")) {
            retVal = WebSocket.withActor(JarSocketActor::props);
        }
        else if (lowercaseJob.equals("ccverify")) {
            retVal = WebSocket.withActor(CCVerifySocketActor::props);
        }
        else if (lowercaseJob.equals("genvcs")) {
            retVal = WebSocket.withActor(VCSocketActor::props);
        }
        else if (lowercaseJob.equals("translate")) {
            retVal = WebSocket.withActor(TranslateSocketActor::props);
        }
        else {
            retVal = WebSocket.withActor(ErrorSocketActor::props);
        }

        return retVal;
    }

}