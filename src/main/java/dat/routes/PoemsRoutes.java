package dat.routes;

import dat.controllers.PoemController;
import dat.security.controllers.SecurityController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class PoemsRoutes {

    PoemController poemController = new PoemController();
    SecurityController securityController = SecurityController.getInstance();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", poemController::getPoems, Role.ANYONE );
            post("/", poemController::createPoems, Role.USER);
        };
    }

}
