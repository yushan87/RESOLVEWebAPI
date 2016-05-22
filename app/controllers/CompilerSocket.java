package controllers;

import models.actors.*;
import play.mvc.Controller;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public static WebSocket<String> socket(String job) {
        WebSocket<String> retVal;
        String lowercaseJob = job.toLowerCase();

        if (lowercaseJob.equals("analyze")) {
            retVal = WebSocket.withActor(AnalyzeSocketActor::props);
        }
        else if (lowercaseJob.equals("buildjar")) {
            retVal = WebSocket.withActor(JarSocketActor::props);
        }
        else if (lowercaseJob.equals("ccverify")) {
            retVal = WebSocket.withActor(CCVerifySocketActor::props);
        }
        else if (lowercaseJob.equals("genvcs")) {
            retVal = WebSocket.withActor(VCSocketActor::props);
        }
        else if (lowercaseJob.equals("translatejava")) {
            retVal = WebSocket.withActor(TranslateJavaSocketActor::props);
        }
        else {
            retVal = WebSocket.withActor(ErrorSocketActor::props);
        }

        return retVal;
    }

}