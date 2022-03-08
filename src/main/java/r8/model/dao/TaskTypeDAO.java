package r8.model.dao;

import r8.model.task.TaskType;

import javax.persistence.EntityManager;

public class TaskTypeDAO {
    private EntityManager entityManager;

    public TaskTypeDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void persist(TaskType taskType) {
        entityManager.getTransaction().begin();
        entityManager.persist(taskType);
        entityManager.getTransaction().commit();
    }

    public TaskType get(int taskTypeId) {
        TaskType taskType = entityManager.getReference(TaskType.class, taskTypeId);
        entityManager.detach(taskType);
        return taskType;
    }

    public void update(TaskType taskType) {
        entityManager.getTransaction().begin();
        entityManager.merge(taskType);
        entityManager.getTransaction().commit();
    }

    public void remove(TaskType taskType) {
        entityManager.getTransaction().begin();
        entityManager.remove(taskType);
        entityManager.getTransaction().commit();
    }
}
