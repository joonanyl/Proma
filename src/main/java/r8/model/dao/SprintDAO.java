package r8.model.dao;

import r8.model.Sprint;

import javax.persistence.EntityManager;
import java.util.List;

public class SprintDAO {
    private EntityManager entityManager;

    public SprintDAO() { this.entityManager = DAO.getEntityManager(); }

    public void persist(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.persist(sprint);
        entityManager.getTransaction().commit();
    }

    public Sprint get(int sprintId) {
        Sprint sprint = entityManager.getReference(Sprint.class, sprintId);
        entityManager.detach(sprint);
        return sprint;
    }

    public List<Sprint> getAll() {
        List<Sprint> results = null;
        try {
            results = entityManager.createQuery("SELECT s FROM Sprint s", Sprint.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void update(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.merge(sprint);
        entityManager.getTransaction().commit();
    }

    public void remove(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(sprint) ? sprint : entityManager.merge(sprint));
        entityManager.getTransaction().commit();
    }

}
