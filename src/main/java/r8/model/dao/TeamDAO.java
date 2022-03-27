package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;

import javax.persistence.EntityManager;
import java.util.List;

public class TeamDAO {
    private EntityManager entityManager;

    public void persist(Team team) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(team);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Team team) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(team);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public Team get(int teamId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Team.class, teamId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Team getByName(String name) {
        entityManager = DAOUtil.getEntityManager();
        Team team = null;
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Team t WHERE t.teamName LIKE :name", Team.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return team;
    }

    public List<Team> getByAccount(Account account) {
        entityManager = DAOUtil.getEntityManager();
        List<Team> results = null;
        try {
            results = entityManager.createQuery(
                            "SELECT t FROM Team t join t.accounts a WHERE a.accountId = :accountId", Team.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            System.out.println("NOTHING WAS FOUND");
        } finally {
            entityManager.close();
        }
        return results;
    }

    public List<Team> getByProject(Project project) {
        entityManager = DAOUtil.getEntityManager();
        List<Team> results = null;
        try {
            results = entityManager.createQuery(
                            "SELECT t FROM Team t WHERE t.project = :project", Team.class)
                    .setParameter("project", project)
                    .getResultList();
            System.out.println(results.size());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return results;
    }

    public void remove(Team team) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(team) ? team : entityManager.merge(team));
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void addAccount(Account account, Team team) {
        entityManager = DAOUtil.getEntityManager();
        account = entityManager.contains(account) ? account : entityManager.merge(account);
        team = entityManager.contains(team) ? team : entityManager.merge(team);

        try {
            entityManager.getTransaction().begin();
            team.addAccount(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void removeAccountAssociation(Team team, Account account) {
        entityManager = DAOUtil.getEntityManager();
        team = entityManager.contains(team) ? team : entityManager.merge(team);
        account = entityManager.contains(account) ? account : entityManager.merge(account);

        try {
            entityManager.getTransaction().begin();
            team.removeAccount(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}
