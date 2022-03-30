package r8.model.dao;


import org.hibernate.HibernateException;
import r8.model.Account;

import javax.persistence.EntityManager;

import java.util.List;


public class AccountDAO {

    private EntityManager entityManager;

    public void persist(Account account) {
        entityManager = DAOUtil.getEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Account account) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(account);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

    }

    public Account get(int accountId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Account.class, accountId);
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Account getByEmail(String email) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Account a WHERE a.email LIKE :email", Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Account> getAll() {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Account a", Account.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public String getHashedPw(String email) {
        entityManager = DAOUtil.getEntityManager();
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
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(account) ? account : entityManager.merge(account));
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public boolean checkIfEmailExists(String email) {
        entityManager = DAOUtil.getEntityManager();
        try {
            List<String> results = entityManager.createQuery(
                            "SELECT a.email FROM Account a", String.class)
                    .getResultList();
            // Email is in database
            if (results.contains(email))
                return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
}
