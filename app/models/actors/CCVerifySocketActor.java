package models.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class CCVerifySocketActor extends AbstractSocketActor {

    public CCVerifySocketActor(ActorRef out) {
        super(out, "ccverify");
    }

    public static Props props(ActorRef out) {
        return Props.create(CCVerifySocketActor.class, out);
    }

    @Override
    public void onReceive(Object message) {
        try {
            // Only deal with Strings
            if (message instanceof String) {
                JsonNode request = Json.parse((String) message);
            }
            else {
                // Send an error message back to user and close
                // socket connection for all other types.
                unhandled(message);
            }
        }
        catch (RuntimeException rte) {
            // Send an error message back to user and close
            // socket connection for all invalid JSON strings.
            unhandled(message);
        }
    }

}