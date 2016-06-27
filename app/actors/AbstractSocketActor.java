package actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Play;
import play.libs.Json;

public abstract class AbstractSocketActor extends UntypedActor {

    protected final String myJob;
    protected final String myProject;
    protected final ActorRef myWebSocketOut;
    protected final String myWorkspacePath;

    protected AbstractSocketActor(ActorRef out, String job, String project) {
        myJob = job;
        myProject = project;
        myWebSocketOut = out;
        myWorkspacePath = Play.application().configuration().getString("workingdir");
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