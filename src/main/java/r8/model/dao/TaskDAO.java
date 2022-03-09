package r8.model.dao;

import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskDAO {
    private EntityManager entityManager;

    public TaskDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void persist(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    public Task get(int taskId) {
        Task task = entityManager.getReference(Task.class, taskId);
        entityManager.detach(task);
        return task;
    }

    public List<Task> getAll() {
        List<Task> results = null;
        try {
            results = entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Task> getByTeam(Team team) {
        List<Task> results = null;
        try {
            results = entityManager.createQuery(
                    "SELECT t FROM Task t JOIN t.teams tm WHERE tm.teamId = :teamId", Task.class)
                    .setParameter("teamId", team.getTeamId())
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Task> getByProject(Project project) {
        List<Task> results = null;
        try {
            results = entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.project = :project", Task.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Task> getByProjectAndTeam(Project project, Team team) {
        List<Task> results = null;
        try {
            results = entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.project = :project AND ", Task.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void update(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();
    }

    public void remove(Task task) {
        entityManager.getTransaction().begin();
        entityManager.remove(task);
        entityManager.getTransaction().commit();
    }

}
