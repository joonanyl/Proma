package r8.model.dao;

import r8.model.Account;
import r8.model.Event;
import r8.model.task.Task;


import javax.persistence.EntityManager;
import java.util.List;

public class EventDAO {
    private EntityManager entityManager;

    public EventDAO() { this.entityManager = DAO.getEntityManager(); }

    public void persist(Event event) {
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
    }

    public Event get(int eventId) {
        Event event = entityManager.getReference(Event.class, eventId);
        entityManager.detach(event);
        return event;
    }

    public List<Event> getByAccount(Account account) {
        List<Event> results = null;
        try {
            results = entityManager.createQuery(
                    "SELECT e FROM Event e WHERE e.account = :account", Event.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Event> getByTask(Task task) {
        List<Event> results = null;
        try {
            results = entityManager.createQuery(
                    "SELECT e FROM Event e WHERE e.task = :task", Event.class)
                    .setParameter("task", task)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Event> getAll() {
        List<Event> results = null;
        try {
            results = entityManager.createQuery("SELECT e FROM Event e", Event.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void update(Event event) {
        entityManager.getTransaction().begin();
        entityManager.merge(event);
        entityManager.getTransaction().commit();
    }

    public void remove(Event event) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(event) ? event : entityManager.merge(event));
        entityManager.getTransaction().commit();
    }
}
