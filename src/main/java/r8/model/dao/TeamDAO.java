package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Account;
import r8.model.Comment;
import r8.model.Project;
import r8.model.Team;

import javax.persistence.EntityManager;
import java.util.List;
/**
 * Data Access Object for Team class.
 * @see Team
 * @author Joona Nylander
 */
public class TeamDAO extends DAO<Team> {
    private EntityManager em;

    public TeamDAO() {
        setClassType(Team.class);
    }

    /**
     *
     * @param account
     * @return Returns a list of teams which an account is part of.
     */
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

    /**
     *
     * @param project
     * @return Returns a list of teams which belongs to a project.
     */
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

    /**
     * Adds an account to a team.
     * @param account
     * @param team
     */
    public void addAccount(Account account, Team team) {
        doInTransaction(em -> team.addAccount(account));
    }

    /**
     * Removes a Many-To-Many relation between a team and an account. Does not remove any entities.
     * @param account
     * @param team
     */
    public void removeAccountAssociation(Account account, Team team) {
        doInTransaction(em -> team.removeAccountById(account.getAccountId()));
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
