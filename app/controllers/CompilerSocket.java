package controllers;

import models.WebSocketActor;
import play.mvc.Controller;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public static WebSocket<String> socket() {
        return WebSocket.withActor(WebSocketActor::props);
    }

}