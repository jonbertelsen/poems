package app.controllers;

import app.daos.PoemDAO;
import app.dtos.PoemDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.List;
import java.util.Map;

public class PoemController {

    private final PoemDAO poemDAO;

    public PoemController(PoemDAO poemDAO){
        this.poemDAO = poemDAO;
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
        ctx.status(HttpStatus.CREATED);
        ctx.json(newPoemDTOs);
    }

    public void createPoem(Context ctx){
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        PoemDTO newPoemDTO = poemDAO.create(poemDTO);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newPoemDTO);
    }

    public void delete(Context ctx){
        int id = getId(ctx);
        boolean deleted = poemDAO.delete(id);
        if (deleted) {
            ctx.status(HttpStatus.OK);
            ctx.json(Map.of(
                    "message", "Poem deleted",
                    "id", id
            ));
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of(
                    "message", "No poem found with id",
                    "id", id
            ));
        }
    }

    public void update(Context ctx){
        int id = getId(ctx);
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        poemDTO = poemDAO.update(id, poemDTO);
        if (poemDTO != null) {
            ctx.status(HttpStatus.OK);
            ctx.json(poemDTO);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of(
                    "message", "No poem found with id",
                    "id", id
            ));
        }
    }

    public void getById(Context ctx){
        int id = getId(ctx);
        PoemDTO poemDTO = poemDAO.getPoemById(id);
        if (poemDTO != null) {
            ctx.status(HttpStatus.OK);
            ctx.json(poemDTO);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of(
                    "message", "No poem found with id",
                    "id", id
            ));
        }
    }

    private int getId(Context ctx) {
        return ctx.pathParamAsClass("id", Integer.class).get();
    }
}
