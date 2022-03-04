package r8.model.dao;

import org.hibernate.Session;
import r8.model.Account;

import javax.persistence.EntityManager;
import java.util.List;


public class AccountDAO {

    private EntityManager entityManager;

    public AccountDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void persist(Account account) {
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

    public void update(Account account) {
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        entityManager.getTransaction().commit();

    }

    public Account get(int accountId) {
        Account account = entityManager.find(Account.class, accountId);
        entityManager.detach(account);
        return account;
    }

    public List<Account> getAll() {
        return entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }

    public String getLoginInfo(String login) {
        Account account = (Account) entityManager.createQuery("SELECT a FROM Account a WHERE a.login LIKE :login")
                .setParameter("login", login)
                .getSingleResult();
        return account.getPassword();
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
