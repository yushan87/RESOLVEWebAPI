package models.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.compiler.TheoryAnalyzeInvoker;
import edu.clemson.cs.r2jt.init2.file.ResolveFile;
import java.util.HashMap;
import play.libs.Json;

public class AnalyzeSocketActor extends AbstractSocketActor {

    public AnalyzeSocketActor(ActorRef out) {
        super(out, "analyze");
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
                String[] args = {"-main", myWorkspacePath, "-webinterface", "Test.mt"};
                TheoryAnalyzeInvoker invoker = new TheoryAnalyzeInvoker(args, myWebSocketOut);
                invoker.executeJob(new HashMap<String, ResolveFile>());
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