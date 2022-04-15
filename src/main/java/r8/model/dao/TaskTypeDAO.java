package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskTypeDAO {
    private EntityManager entityManager;

    public void persist(TaskType taskType) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(taskType);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public TaskType get(int taskTypeId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(TaskType.class, taskTypeId);
        } catch (NullPointerException e) {
            System.out.println("Mitään ei löytynyt.");
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<TaskType> getAll() {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM TaskType t", TaskType.class)
                    .getResultList();
        } catch (NullPointerException e) {
            System.out.println("No TaskTypes found");
            return null;
        } finally {
            entityManager.close();
        }
    }

    public TaskType getByName(String name) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT tt FROM TaskType tt WHERE tt.name LIKE :name", TaskType.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }


    public void update(TaskType taskType) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(taskType);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void remove(TaskType taskType) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(taskType);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

}
