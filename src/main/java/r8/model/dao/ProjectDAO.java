package r8.model.dao;


import r8.model.Account;
import r8.model.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectDAO extends DAO<Project> {
    private EntityManager em;
    public ProjectDAO() {
        setClassType(Project.class);
    }

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

    public void addAccount(Account account, Project project) {
        doInTransaction(em -> {
            em.merge(account);
            project.addAccount(account);
        });
    }

    public void removeAccountAssociation(Account account, Project project) {
        doInTransaction(em -> project.removeAccountWithId(account.getAccountId()));
    }

    public void removeAccount(Account account, Project project) {
        doInTransaction(em -> {
            em.merge(account);
            project.removeAccount(account);
        });
    }

    private void entityManager() {
        em = getEntityManager();
    }
}
