package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final PoemRoutes poemRoutes;

    public Routes(PoemRoutes poemRoutes) {
        this.poemRoutes = poemRoutes;
    }

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", ctx -> ctx.result("Hello World"));
            path("/poems", poemRoutes.getRoutes());
        };
    }
}
