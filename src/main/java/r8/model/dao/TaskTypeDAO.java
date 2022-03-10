package r8.model.dao;

import r8.model.Account;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
import java.util.List;

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
        TaskType taskType = null;
        try {
            taskType = entityManager.getReference(TaskType.class, taskTypeId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return taskType;
    }

    public List<TaskType> getAll() {
        List<TaskType> results = null;
        try {
            results = entityManager.createQuery("SELECT t FROM TaskType t", TaskType.class)
                    .getResultList();
        } catch (NullPointerException e) {
            System.out.println("No TaskTypes found");
            e.printStackTrace();
        }
        return results;
    }

    public TaskType getByName(String name) {
        TaskType taskType = null;
            try {
                taskType = entityManager.createQuery(
                        "SELECT tt FROM TaskType tt WHERE tt.name LIKE :name", TaskType.class)
                        .getSingleResult();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        return taskType;
    }


    public void update(TaskType taskType) {
        entityManager.getTransaction().begin();
        entityManager.merge(taskType);
        entityManager.getTransaction().commit();
    }

    public void remove(TaskType taskType) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(taskType) ? taskType : entityManager.merge(taskType));
        entityManager.getTransaction().commit();
    }

}
