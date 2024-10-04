package dat.routes;

import dat.controllers.PoemController;
import dat.security.controllers.SecurityController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {

    PoemController poemController = new PoemController();
    SecurityController securityController = SecurityController.getInstance();

    public EndpointGroup getRoutes(){
        return () -> {

            get("/{id}", poemController::getById, Role.ANYONE);
            before(securityController.authenticate()); // check if there is a valid token in the header
            post("/", poemController::createPoem, Role.ADMIN);
            delete("/{id}", poemController::delete, Role.ADMIN);
            put("/{id}", poemController::update, Role.ADMIN);
                   };
    }
}
