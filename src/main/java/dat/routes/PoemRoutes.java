package dat.routes;

import dat.controllers.PoemController;
import dat.security.controllers.SecurityController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {

    PoemController poemController = new PoemController();
    SecurityController securityController = SecurityController.getInstance();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/{id}", poemController::getById, Role.ANYONE);
            post("/", poemController::createPoem, Role.USER);
            delete("/{id}", poemController::delete, Role.USER);
            put("/{id}", poemController::update, Role.USER);
        };
    }
}
