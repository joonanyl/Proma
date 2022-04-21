package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class SprintDAO extends DAO<Sprint> {
    private EntityManager em;

    public SprintDAO() {
        setClassType(Sprint.class);
    }

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

    public void addTask(Task task, Sprint sprint) {
        doInTransaction(em -> sprint.addTask(task));
    }

    public void removeTaskAssociation(Task task, Sprint sprint) {
        doInTransaction(em -> sprint.removeTaskWithId(task.getTaskId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
