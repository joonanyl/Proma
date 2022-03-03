package r8.model.dao;

import org.hibernate.Session;
import r8.model.Account;

import javax.persistence.EntityManager;


public class AccountDAO {

    private EntityManager entityManager;

    public AccountDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void addAccount(Account account) {
        entityManager.getTransaction().begin();

        entityManager.persist(account);
        entityManager.getTransaction().commit();

        /*
        TAI TÄHÄN TYYLIIN KENTIES?
        setterit haettaisiin kontrollerin avulla UI:sta

        Account account = new Account();
        account.setAccountId();
        account.setProjects();
         */
    }

    public void updateAccount(Account account) {
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        entityManager.getTransaction().commit();

    }

    public Account getAccount(int accountId) {
        Account account = entityManager.find(Account.class, accountId);
        entityManager.detach(account);
        return account;
    }

    public void removeAccount(Account account) {
        entityManager.getTransaction().begin();
        entityManager.remove(account);
        entityManager.getTransaction().commit();
    }

    public void removeAccountById(int accountId) {
        Account account = entityManager.find(Account.class, accountId);
        entityManager.remove(account);
    }
}
