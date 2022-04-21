package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskDAO extends DAO<Task> {
    private EntityManager em;

    public TaskDAO() {
        setClassType(Task.class);
    }

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

    public void assignToTeam(Team team, Task task) {
        doInTransaction(em -> task.addTeam(team));
    }

    public void removeTeamAssociation(Team team, Task task) {
        doInTransaction(em -> task.removeTeamWithId(team.getTeamId()));
    }

    public void assignToAccount(Account account, Task task) {
        doInTransaction(em -> task.addAccount(account));
    }

    public void removeAccountAssociation(Account account, Task task) {
       doInTransaction(em -> task.removeAccountWithId(account.getAccountId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
