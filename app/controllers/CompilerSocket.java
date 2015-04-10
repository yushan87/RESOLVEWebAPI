package controllers;

import play.mvc.Controller;
import play.mvc.WebSocket;

public class CompilerSocket extends Controller {

    public static WebSocket<String> socket() {
        return WebSocket.whenReady((in, out) -> {
            // Send a single 'Hello <Name>!' message
            // and close the connection.
            out.write("Hello!");
            out.close();
        });
    }

}