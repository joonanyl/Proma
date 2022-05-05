package r8.model.dao;

import r8.model.*;
import r8.model.task.Task;


import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data Access Object for Event class.
 * @see Event
 * @author Joona Nylander
 */
public class EventDAO extends DAO<Event> {
    private EntityManager em;

    public EventDAO() {
        setClassType(Event.class);
    }

    /**
     *
     * @param account
     * @return Returns a list of events that belongs to a single account.
     */
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


    /**
     *
     * @param task
     * @return Returns a list of events that are assigned to a single task.
     */
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

    /**
     *
     * @param account
     * @param project
     * @return Returns a list of events which belongs to an account and are inside a project.
     */
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

    /**
     *
     * @param account
     * @param sprint
     * @return Returns a list of events which belongs to an account and are inside a sprint.
     */
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
