package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Task> getAll() {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Task t", Task.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Task> getByTeam(Team team) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t JOIN t.teams tm WHERE tm.teamId = :teamId", Task.class)
                    .setParameter("teamId", team.getTeamId())
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Task> getByProject(Project project) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.project = :project", Task.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Task> getByAccount(Account account) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t join t.accounts a WHERE a.accountId = :accountId", Task.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Task> getByTaskType(TaskType taskType) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.taskType = :taskType", Task.class)
                    .setParameter("taskType", taskType)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
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

    public void assignToTeam(Team team, Task task) {
        entityManager = DAOUtil.getEntityManager();
        team = entityManager.contains(team) ? team : entityManager.merge(team);
        task = entityManager.contains(task) ? task : entityManager.merge(task);

        try {
            entityManager.getTransaction().begin();
            task.assignToTeam(team);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void removeTeamAssociation(Team team, Task task) {
        entityManager = DAOUtil.getEntityManager();
        team = entityManager.contains(team) ? team : entityManager.merge(team);
        task = entityManager.contains(task) ? task : entityManager.merge(task);

        try {
            entityManager.getTransaction().begin();
            task.removeFromTeam(team);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
