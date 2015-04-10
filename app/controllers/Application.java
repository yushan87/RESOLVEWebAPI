package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

public class Application extends Controller {

    public static WebSocket<String> socket() {
        return WebSocket.whenReady((in, out) -> {
            // Send a single 'Hello <Name>!' message
            // and close the connection.
            out.write("Hello!");
            out.close();
        });
    }

    public static Result index() {
        return ok(index.render("The RESOLVE Web API is currently online."));
    }

}