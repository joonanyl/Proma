package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Event;
import r8.model.Project;
import r8.model.Sprint;
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

    public List<Event> getByAccountAndProject(Account account, Project project) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Event e WHERE e.account = :account AND e.project = :project", Event.class)
                    .setParameter("account", account)
                    .setParameter("project", project)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Event> getByAccountAndSprint(Account account, Sprint sprint) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Event e WHERE e.account = :account AND e.sprint = :sprint", Event.class)
                    .setParameter("account", account)
                    .setParameter("sprint", sprint)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
