package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;

import javax.persistence.EntityManager;
import java.util.List;

public class TeamDAO extends DAO<Team> {
    private EntityManager em;

    public TeamDAO() {
        setClassType(Team.class);
    }

    public List<Team> getByAccount(Account account) {
        entityManager();
        try {
            return em.createQuery(
                    "SELECT t FROM Team t join t.accounts a WHERE a.accountId = :accountId", Team.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Team> getByProject(Project project) {
        entityManager();
        try {
             return em.createQuery(
                     "SELECT t FROM Team t WHERE t.project = :project", Team.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void addAccount(Account account, Team team) {
        doInTransaction(em -> team.addAccount(account));
    }

    public void removeAccountAssociation(Account account, Team team) {
        doInTransaction(em -> team.removeAccountById(account.getAccountId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
