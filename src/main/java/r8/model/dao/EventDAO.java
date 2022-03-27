package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Event;


import javax.persistence.EntityManager;

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
