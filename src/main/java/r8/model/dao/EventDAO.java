package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Event;
import r8.model.task.Task;


import javax.persistence.EntityManager;
import java.util.List;

public class EventDAO {
    private EntityManager entityManager;

    public void persist(Event event) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(event);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

    }

    public Event get(int eventId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Event.class, eventId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Event> getByAccount(Account account) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT e FROM Event e WHERE e.account = :account", Event.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Event> getByTask(Task task) {
        entityManager = DAOUtil.getEntityManager();
        try {
             return entityManager.createQuery(
                    "SELECT e FROM Event e WHERE e.task = :task", Event.class)
                    .setParameter("task", task)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Event> getAll() {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery("SELECT e FROM Event e", Event.class)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void update(Event event) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(event);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void remove(Event event) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(event) ? event : entityManager.merge(event));
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
