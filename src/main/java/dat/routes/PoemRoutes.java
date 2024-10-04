package dat.routes;

import dat.controllers.PoemController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {

    PoemController poemController = new PoemController();

    public EndpointGroup getRoutes(){
        return () -> {
            post("/", poemController::createPoem, Role.ADMIN);
            delete("/{id}", poemController::delete, Role.ADMIN);
            put("/{id}", poemController::update, Role.ADMIN);
            get("/{id}", poemController::getById, Role.ANYONE);
        };
    }
}
