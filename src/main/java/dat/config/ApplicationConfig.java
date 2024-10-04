package dat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.routes.Routes;
import dat.security.controllers.SecurityController;
import dat.security.exceptions.ApiException;
import dat.security.routes.SecurityRoutes;
import dat.utils.Utils;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationConfig {

    private static Routes routes = new Routes();
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    public static void configuration(JavalinConfig config) {
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api"; // base path for all endpoints
        config.router.apiBuilder(routes.getRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }

    public static Javalin startServer(int port) {
        Javalin app = Javalin.create(ApplicationConfig::configuration);
        app.beforeMatched(ApplicationConfig::accessHandler);
        setGeneralExceptionHandling(app);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    private static void setGeneralExceptionHandling(Javalin app) {
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("An unhandled exception occurred", e.getMessage());
            ctx.json(Utils.convertErrorToJson(e.getMessage()));
        });

        app.exception(ApiException.class, (e, ctx) -> {
            ctx.status(e.getCode());
            logger.warn("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());

            ctx.json(Utils.convertErrorToJson(e.getMessage()));
        });
    }

    private static void accessHandler(Context ctx) {
        UserDTO user = ctx.attribute("user"); // the User was put in the context by the SecurityController.authenticate method (in a before filter on the route)
        Set<RouteRole> allowedRoles = ctx.routeRoles(); // roles allowed for the current route
        if (!securityController.authorize(user, allowedRoles)) {
            if (user != null){
                throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "Unauthorized with roles: " + user.getRoles() + ". Needed roles are: " + allowedRoles);
            } else {
                throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "You need to log in, dude!");
            }
        }
    }
}
