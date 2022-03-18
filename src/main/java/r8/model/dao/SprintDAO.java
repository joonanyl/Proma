package r8.model.dao;

import r8.model.Sprint;

import javax.persistence.EntityManager;

public class SprintDAO {
    private EntityManager entityManager;

    public SprintDAO() { this.entityManager = DAOUtil.getEntityManager(); }

    public void addSprint(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.persist(sprint);
        entityManager.getTransaction().commit();
    }

    public Sprint get(int sprintId) {
        Sprint sprint = entityManager.getReference(Sprint.class, sprintId);
        entityManager.detach(sprint);
        return sprint;
    }

    public void update(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.merge(sprint);
        entityManager.getTransaction().commit();
    }

    public void remove(Sprint sprint) {
        entityManager.getTransaction().begin();
        entityManager.remove(sprint);
        entityManager.getTransaction().commit();
    }

}
