package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.PoemDAO;

import io.javalin.Javalin;

import jakarta.persistence.EntityManagerFactory;


public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin
                .create(ApplicationConfig::configuration)
                .start(7070);
    }
}