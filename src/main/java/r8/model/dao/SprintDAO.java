package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Comment;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;
/**
 * Data Access Object for Sprint class.
 * @see Sprint
 * @author Joona Nylander
 */
public class SprintDAO extends DAO<Sprint> {
    private EntityManager em;

    public SprintDAO() {
        setClassType(Sprint.class);
    }

    /**
     *
     * @param project
     * @return Returns a list sprints that belongs to a project.
     */
    public List<Sprint> getByProject(Project project) {
        entityManager();

        try {
            return em.createQuery(
                    "SELECT s FROM Sprint s WHERE s.project = :project", Sprint.class)
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
     * @param task
     * @param sprint
     * Adds a task to a sprint
     */
    public void addTask(Task task, Sprint sprint) {
        doInTransaction(em -> sprint.addTask(task));
    }

    /**
     * Removes the Many-to-Many association between a task and a sprint. Does not remove any entities.
     * @param task
     * @param sprint
     */
    public void removeTaskAssociation(Task task, Sprint sprint) {
        doInTransaction(em -> sprint.removeTaskWithId(task.getTaskId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
