package app.daos;

import app.dtos.PoemDTO;
import app.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class PoemDAO {

    private final EntityManagerFactory emf;

    public PoemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<PoemDTO> getPoems(){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Poem> query = em.createQuery("SELECT p FROM Poem p", Poem.class);
            return PoemDTO.toDTOList(query.getResultList());
        }
    }

    public PoemDTO create(PoemDTO poemDTO) {
        Poem poem = new Poem(poemDTO);
        try (EntityManager em = emf.createEntityManager()){
          em.getTransaction().begin();
          em.persist(poem);
          em.getTransaction().commit();
        }
        return new PoemDTO(poem);
    }

    public List<PoemDTO> createFromList(PoemDTO[] poemDTOS) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<PoemDTO> createdPoems = new ArrayList<>();
            for (PoemDTO poemDTO : poemDTOS) {
                Poem poem = new Poem(poemDTO);
                em.persist(poem);
                createdPoems.add(new PoemDTO(poem));
            }
            em.getTransaction().commit();
            return createdPoems;
        }
    }

    public PoemDTO getPoemById(int id){
        try (EntityManager em = emf.createEntityManager()){
            Poem poem = em.find(Poem.class, id);
            if (poem != null){
                return new PoemDTO(poem);
            }
            return null;
        }
    }

    public boolean delete(int id){
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Poem poem = em.find(Poem.class, id);
            if (poem != null){
                em.remove(poem);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        }
    }

    public PoemDTO update(int id, PoemDTO poemDTO){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Poem poem = em.find(Poem.class, id);
            if (poem != null) {
                poem.setTitle(poemDTO.getTitle());
                poem.setPoem(poemDTO.getPoem());
                poem.setStyle(poemDTO.getStyle());
                em.getTransaction().commit();
                return new PoemDTO(poem);
            }
            em.getTransaction().rollback();
            return null;
        }
    }
}
