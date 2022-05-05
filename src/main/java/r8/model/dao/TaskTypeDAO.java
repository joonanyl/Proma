package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Comment;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;
/**
 * Data Access Object for TaskType class.
 * @see TaskType
 * @author Joona Nylander
 */
public class TaskTypeDAO extends DAO<TaskType> {
    private EntityManager em;

    public TaskTypeDAO() {
        setClassType(TaskType.class);
    }

    /**
     *
     * @param name
     * @return Finds and returns a TaskType with the given name.
     */
    public TaskType getByName(String name) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT tt FROM TaskType tt WHERE tt.name LIKE :name", TaskType.class)
                    .setParameter("name", name)
                    .getSingleResult();
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
