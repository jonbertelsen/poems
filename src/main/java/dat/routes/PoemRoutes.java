package dat.routes;

import dat.controllers.PoemController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {

    PoemController poemController = new PoemController();

    public EndpointGroup getRoutes(){
        return () -> {
            post("/", poemController::createPoem);
            delete("/{id}", poemController::delete);
            put("/{id}", poemController::update);
            get("/{id}", poemController::getById);
        };
    }
}
