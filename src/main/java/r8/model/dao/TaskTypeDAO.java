package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.task.TaskType;

import javax.persistence.EntityManager;

public class TaskTypeDAO extends DAO<TaskType> {
    private EntityManager em;

    public TaskTypeDAO() {
        setClassType(TaskType.class);
    }

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
