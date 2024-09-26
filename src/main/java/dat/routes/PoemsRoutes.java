package dat.routes;

import dat.controllers.PoemController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class PoemsRoutes {

    PoemController poemController = new PoemController();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", poemController::getPoems );
            post("/", poemController::createPoems);
        };
    }

}
