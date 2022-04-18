package r8.model.dao;


import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectDAO {
    private EntityManager entityManager;

    public void persist(Project project) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(project);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Project project) {
        entityManager = DAOUtil.getEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(project);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public Project get(int projectId) {
        entityManager = DAOUtil.getEntityManager();

        try {
            return entityManager.find(Project.class, projectId);
        } catch (NullPointerException e) {
            System.out.println("Nothing was found :(");
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Project getByName(String name) {
        entityManager = DAOUtil.getEntityManager();

        try {
            return entityManager.createQuery("SELECT p FROM Project p WHERE p.name LIKE :name", Project.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NullPointerException e) {
            System.out.println("Nothing was found");
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Project> getByAccount(Account account) {
        entityManager = DAOUtil.getEntityManager();

        try {
            return entityManager.createQuery(
                            "SELECT p FROM Project p join p.accounts a " +
                                    "WHERE a.accountId = :accountId", Project.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Project> getAll() {
        entityManager = DAOUtil.getEntityManager();

        try {
            return entityManager.createQuery("SELECT a FROM Project a", Project.class).getResultList();
        } catch (NullPointerException e) {
            System.out.println("No projects found.");
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void remove(Project project) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void addAccount(Account account, Project project) {
        entityManager = DAOUtil.getEntityManager();
        project = entityManager.contains(project) ? project : entityManager.merge(project);
        account = entityManager.contains(account) ? account : entityManager.merge(account);

        try {
            entityManager.getTransaction().begin();
            project.addAccount(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void removeAccountAssociation(Account account, Project project) {
        entityManager = DAOUtil.getEntityManager();
        // Checks if the entities from parameters are currently managed
        // If not, eM will merge them so that they're managed, and will listen to changes
        account = entityManager.contains(account) ? account : entityManager.merge(account);
        project = entityManager.contains(project) ? project : entityManager.merge(project);

        try {
            entityManager.getTransaction().begin();
            project.removeAccount(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
