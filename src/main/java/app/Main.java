package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.PoemController;
import app.daos.PoemDAO;
import app.routes.PoemRoutes;
import app.routes.Routes;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        PoemDAO poemDAO = new PoemDAO(emf);
        PoemController poemController = new PoemController(poemDAO);
        PoemRoutes poemRoutes = new PoemRoutes(poemController);
        Routes routes = new Routes(poemRoutes);
        ApplicationConfig applicationConfig = new ApplicationConfig(routes);
        Javalin app = applicationConfig.startServer(7070);
    }
}
