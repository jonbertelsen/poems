package app.routes;

import app.controllers.PoemController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {

    private final PoemController poemController;

    public PoemRoutes(PoemController poemController) {
        this.poemController = poemController;
    }

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", poemController::getPoems );
            get("/{id}", poemController::getById);
            post("/", poemController::createPoem);
            post("/batch", poemController::createPoems);
            delete("/{id}", poemController::delete);
            put("/{id}", poemController::update);
        };
    }
}
