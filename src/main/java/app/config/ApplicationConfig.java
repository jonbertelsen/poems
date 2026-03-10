package app.config;

import app.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;

import java.util.Map;

public class ApplicationConfig {

    private final Routes routes;

    public ApplicationConfig(Routes routes) {
        this.routes = routes;
    }

    public void configuration(JavalinConfig config){
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api"; // base path for all endpoints
        config.routes.apiBuilder(routes.getRoutes());
        config.routes.exception(ValidationException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(Map.of(
                    "message", "Path parameter must be a number",
                    "path", ctx.path()
            ));
        });
    }

    public Javalin startServer(int port) {
        var app = Javalin.create(this::configuration);
        app.start(port);
        return app;
    }

    public void stopServer(Javalin app) {
        app.stop();
    }
}
