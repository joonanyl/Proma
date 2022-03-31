package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class SprintDAO {
    private EntityManager entityManager;

    public void persist(Sprint sprint) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(sprint);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public Sprint get(int sprintId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Sprint.class, sprintId);
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Sprint> getByProject(Project project) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT s FROM Sprint s WHERE s.project = :project", Sprint.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Sprint> getAll() {
        try {
            return entityManager.createQuery("SELECT s FROM Sprint s", Sprint.class)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void update(Sprint sprint) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(sprint);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void remove(Sprint sprint) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(sprint) ? sprint : entityManager.merge(sprint));
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void addTask(Task task, Sprint sprint) {
        entityManager = DAOUtil.getEntityManager();
        task = entityManager.contains(task) ? task : entityManager.merge(task);
        sprint = entityManager.contains(sprint) ? sprint : entityManager.merge(sprint);

        try {
            entityManager.getTransaction().begin();
            sprint.addTask(task);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void removeTaskAssociation(Task task, Sprint sprint) {
        entityManager = DAOUtil.getEntityManager();
        task = entityManager.contains(task) ? task : entityManager.merge(task);
        sprint = entityManager.contains(sprint) ? sprint : entityManager.merge(sprint);

        try {
            entityManager.getTransaction().begin();
            sprint.removeTask(task);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
