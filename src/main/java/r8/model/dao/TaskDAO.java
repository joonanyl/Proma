package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.*;
import r8.model.task.Task;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data Access Object for Task class.
 * @see Task
 * @author Joona Nylander
 */
public class TaskDAO extends DAO<Task> {
    private EntityManager em;

    public TaskDAO() {
        setClassType(Task.class);
    }

    /**
     *
     * @param team
     * @return Returns a list of tasks which are assigned to a team.
     */
    public List<Task> getByTeam(Team team) {
        entityManager();
        try {
            return em.createQuery(
                            "SELECT t FROM Task t JOIN t.teams tm WHERE tm.teamId = :teamId", Task.class)
                    .setParameter("teamId", team.getTeamId())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param project
     * @return Returns a list of tasks which are assigned to a project.
     */
    public List<Task> getByProject(Project project) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT t FROM Task t WHERE t.project = :project", Task.class)
                    .setParameter("project", project)
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
     * @return Returns a list of tasks which are assigned to an account.
     */
    public List<Task> getByAccount(Account account) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT t FROM Task t join t.accounts a WHERE a.accountId = :accountId", Task.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param taskType
     * @return Returns a list of tasks which have the same TaskType.
     */
    public List<Task> getByTaskType(TaskType taskType) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT t FROM Task t WHERE t.taskType = :taskType", Task.class)
                    .setParameter("taskType", taskType)
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
     * @return Returns a list of tasks which belongs to the same projact and assigned to an account.
     */
    public List<Task> getByAccountAndProject(Account account, Project project) {
        entityManager();
        try {
            return em.createQuery("SELECT t FROM Task t JOIN t.accounts a " +
                    "WHERE a.accountId = :accountId AND t.project = :project", Task.class)
                    .setParameter("accountId", account.getAccountId())
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
     * @return Returns a list of tasks which belongs to the same sprint and are assigned to an account.
     */
    public List<Task> getByAccountAndSprint(Account account, Sprint sprint) {
        entityManager();
        try {
            return em.createQuery("SELECT t FROM Task t JOIN t.accounts a INNER JOIN t.sprints s" +
                    " WHERE a.accountId = :accountId AND s.sprintId = :sprintId", Task.class)
                    .setParameter("accountId", account.getAccountId())
                    .setParameter("sprintId", sprint)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Assigns a task to a team.
     * @param team
     * @param task
     */
    public void assignToTeam(Team team, Task task) {
        doInTransaction(em -> task.addTeam(team));
    }

    /**
     * Removes the Many-To-Many relation between a task and a team. Does not remove any entities.
     * @param team
     * @param task
     */
    public void removeTeamAssociation(Team team, Task task) {
        doInTransaction(em -> task.removeTeamWithId(team.getTeamId()));
    }

    /**
     * Assigns a task to an account.
     * @param account
     * @param task
     */
    public void assignToAccount(Account account, Task task) {
        doInTransaction(em -> task.addAccount(account));
    }

    /**
     * Removes the Many-To-Many relation between a task and an account. Does not remove any entities.
     * @param account
     * @param task
     */
    public void removeAccountAssociation(Account account, Task task) {
       doInTransaction(em -> task.removeAccountWithId(account.getAccountId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
