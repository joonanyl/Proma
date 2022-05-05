package r8.model.dao;


import r8.model.Account;
import r8.model.Comment;
import r8.model.Project;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data Access Object for Project class.
 * @see Project
 * @author Joona Nylander
 */
public class ProjectDAO extends DAO<Project> {
    private EntityManager em;
    public ProjectDAO() {
        setClassType(Project.class);
    }

    /**
     *
     * @param account
     * @return Returns a list of projects where an account belongs to.
     */
    public List<Project> getByAccount(Account account) {
        entityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Project p JOIN p.accounts a " +
                                    "WHERE a.accountId = :accountId", Project.class)
                    .setParameter("accountId", account.getAccountId())
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Adds an account to a project.
     * @param account
     * @param project
     */
    public void addAccount(Account account, Project project) {
        doInTransaction(em -> {
            em.merge(account);
            project.addAccount(account);
        });
    }

    /**
     * Removes the Many-to-Many relation between an account and a project. Does not remove the entities.
     * @param account
     * @param project
     */
    public void removeAccountAssociation(Account account, Project project) {
        doInTransaction(em -> {
            em.merge(project);
            project.removeAccountWithId(account.getAccountId());
        });
    }

    private void entityManager() {
        em = getEntityManager();
    }
}
