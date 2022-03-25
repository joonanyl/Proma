package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.task.Task;

import javax.persistence.EntityManager;

public class TaskDAO {
    private EntityManager entityManager;

    public void persist(Task task) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public Task get(int taskId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Task.class, taskId);
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void update(Task task) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(task);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void remove(Task task) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

}
