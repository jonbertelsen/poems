package dat;

import dat.config.HibernateConfig;
import dat.daos.PoemDAO;
import dat.dtos.PoemDTO;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
        PoemDAO poemDAO = PoemDAO.getInstance(emf);

        System.out.println("Hello world!");

        Javalin app = Javalin
                .create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .post("/poem", ctx -> {
                    PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
                    PoemDTO newPoemDTO = poemDAO.create(poemDTO);
                    ctx.status(HttpStatus.CREATED);
                    ctx.json(newPoemDTO);
                })
                .delete("/poem/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    poemDAO.delete(id);
                    ctx.result("deleted: " + id);
                })
                .get("/poem/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    PoemDTO poemDTO = poemDAO.getPoemById(id);
                    if (poemDTO != null) {
                        ctx.json(poemDTO);
                    } else {
                        ctx.result("No poem found");
                    }
                })
                .put("/poem/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
                    poemDTO = poemDAO.update(id, poemDTO);
                    ctx.status(HttpStatus.OK);
                    ctx.json(poemDTO);
                })
                .get("/poems", ctx -> {
                    List<PoemDTO> poemDTOS = poemDAO.getPoems();
                    ctx.status(HttpStatus.OK);
                    ctx.json(poemDTOS);
                })
                .post("/poems", ctx -> {
                    // Modtag og konverter en liste af digte (fra json til dto)
                    PoemDTO[] poemDTOS = ctx.bodyAsClass(PoemDTO[].class);
                    // Gem alle digtene i databasen (dao) og modtag en liste af de nye digte
                    List<PoemDTO> newPoemDTOs = poemDAO.createFromList(poemDTOS);
                    ctx.status(HttpStatus.CREATED);
                    ctx.json(newPoemDTOs);
                })
                .start(7070);
    }
}