package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private PoemsRoutes poemsRoutes = new PoemsRoutes();
    private PoemRoutes poemRoutes = new PoemRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", ctx -> ctx.result("Hello World"));
            path("/poems", poemsRoutes.getRoutes());
            path("/poem", poemRoutes.getRoutes());
        };
    }
}
