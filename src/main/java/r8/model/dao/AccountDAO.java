package r8.model.dao;


import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
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
    }

    public void update(Account account) {
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        entityManager.getTransaction().commit();

    }

    public Account get(int accountId) {
        Account account = null;
        try {
            account = entityManager.getReference(Account.class, accountId);
            // entityManager.detach(account);
        } catch (NullPointerException e) {
            System.out.println("Account wasn't found");
            e.printStackTrace();
        }
        return account;
    }

    public Account getByEmail(String email) {
        Account account = null;
        try {
            account = (Account) entityManager.createQuery(
                            "SELECT a FROM Account a WHERE a.email LIKE :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NullPointerException e) {
            System.out.println("Account wasn't found");
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> getAll() {
        List<Account> results = null;
        try {
            results = entityManager.createQuery("SELECT a FROM Account a", Account.class)
                    .getResultList();
        } catch (NullPointerException e) {
            System.out.println("No accounts found.");
            e.printStackTrace();
        }
        return results;
    }

    public String getHashedPw(String email) {
        Account account = null;
        try {
            account = (Account) entityManager.createQuery(
                    "SELECT a FROM Account a WHERE a.email LIKE :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NullPointerException e) {
            System.out.println("No account found with given email address");
            e.printStackTrace();
        }

        assert account != null;
        return account.getPassword();
    }

    public void removeAccount(Account account) {
        entityManager.getTransaction().begin();
        // Check if removed-to-be account is detached or not
        entityManager.remove(entityManager.contains(account) ? account : entityManager.merge(account));
        entityManager.getTransaction().commit();
    }

    public void removeAccountById(int accountId) {
        Account account = null;
        try {
            account = entityManager.getReference(Account.class, accountId);
            entityManager.remove(entityManager.contains(account) ? account : entityManager.merge(account));
        } catch (NullPointerException e) {
            System.out.println("Account not found");
            e.printStackTrace();
        }
    }

    public boolean checkIfEmailExists(String email) {
        try {
            List<String> results = entityManager.createQuery(
                    "SELECT a.email FROM Account a", String.class)
                    .getResultList();
            // Email is in database
            if (results.contains(email))
                return true;
        } catch (NullPointerException e) {
            System.out.println("Spostia ei löytynyt!");
            e.printStackTrace();
        }
        // Email is not in database
        return false;
    }
}
