package r8.model.dao;

import r8.model.task.Task;

import javax.persistence.EntityManager;

public class TaskDAO {
    private EntityManager entityManager;

    public TaskDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void persist(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    public Task get(int taskId) {
        Task task = entityManager.getReference(Task.class, taskId);
        entityManager.detach(task);
        return task;
    }

    public void update(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();
    }

    public void remove(Task task) {
        entityManager.getTransaction().begin();
        entityManager.remove(task);
        entityManager.getTransaction().commit();
    }

}
