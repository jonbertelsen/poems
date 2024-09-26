package dat.config;

import dat.routes.Routes;
import io.javalin.config.JavalinConfig;

public class ApplicationConfig {

    private static Routes   routes = new Routes();

    public static void configuration(JavalinConfig config){
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api/v1"; // base path for all endpoints
        config.router.apiBuilder(routes.getRoutes());
    }
}
