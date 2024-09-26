package dat;

import dat.config.ApplicationConfig;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin
                .create(ApplicationConfig::configuration)
                .start(7070);
    }
}