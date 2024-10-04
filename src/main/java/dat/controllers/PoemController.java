package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.PoemDAO;
import dat.dtos.PoemDTO;
import dat.utils.Utils;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class PoemController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");

    PoemDAO poemDAO = PoemDAO.getInstance(emf);

    private static final Logger logger = LoggerFactory.getLogger(PoemController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public PoemController(){
    }

    public void getPoems(Context ctx){
        List<PoemDTO> poemDTOS = poemDAO.getPoems();
        ctx.status(HttpStatus.OK);
        ctx.json(poemDTOS);
    }

    public void createPoems(Context ctx){
        // Modtag og konverter en liste af digte (fra json til dto)
        PoemDTO[] poemDTOS = ctx.bodyAsClass(PoemDTO[].class);
        // Gem alle digtene i databasen (dao) og modtag en liste af de nye digte
        List<PoemDTO> newPoemDTOs = poemDAO.createFromList(poemDTOS);
        logger.info("Poems created: " + newPoemDTOs.toString());
        ctx.status(HttpStatus.CREATED);
        ctx.json(newPoemDTOs);
    }

    public void createPoem(Context ctx){
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        PoemDTO newPoemDTO = poemDAO.create(poemDTO);
        logger.info("Poem created: " + poemDTO.toString());
        ctx.status(HttpStatus.CREATED);
        ctx.json(newPoemDTO);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        logger.info("Poem deleted with id = " + id);
        poemDAO.delete(id);
        ctx.json(Utils.convertToJsonMessage(ctx, "info","deleted: " + id));
    }

    public void update(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        poemDTO = poemDAO.update(id, poemDTO);
        logger.info("Poem updated: " + poemDTO.toString());
        ctx.status(HttpStatus.OK);
        ctx.json(poemDTO);
    }

    public void getById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDTO poemDTO = poemDAO.getPoemById(id);
        if (poemDTO != null) {
            ctx.json(poemDTO);
        } else {
            logger.warn("Poem not found with id = " + id);
            ctx.json(Utils.convertToJsonMessage(ctx, "info","Poem not found"));
        }
    }


}
