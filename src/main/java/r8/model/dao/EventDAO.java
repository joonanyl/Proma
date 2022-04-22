package r8.model.dao;

import r8.model.Account;
import r8.model.Event;
import r8.model.task.Task;


import javax.persistence.EntityManager;
import java.util.List;

public class EventDAO extends DAO<Event> {
    private EntityManager em;

    public EventDAO() {
        setClassType(Event.class);
    }

    public List<Event> getByAccount(Account account) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Event e WHERE e.account = :account", Event.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Event> getByTask(Task task) {
        entityManager();
        try {
             return em.createQuery(
                    "SELECT e FROM Event e WHERE e.task = :task", Event.class)
                    .setParameter("task", task)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
