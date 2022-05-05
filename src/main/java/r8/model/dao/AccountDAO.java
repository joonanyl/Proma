package r8.model.dao;


import r8.model.Account;
import r8.model.Project;
import r8.model.appState.AppState;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;


import java.util.List;

/**
 * Data Access Object for Account class.
 * @see Account
 * @author Joona Nylander
 */

public class AccountDAO extends DAO<Account> {

    private EntityManager em;

    public AccountDAO() {
        setClassType(Account.class);
    }

    /**
     *
     * @param email Uses an email address for searching an account from the database.
     * @return On match, returns an account object from the database.
     */
    public Account getByEmail(String email) {
        em = DAOUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Account a WHERE a.email LIKE :email", Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param email Finds an account from the database by the given email.
     * @return Returns the hashed password for authentication purposes.
     */
    public String getHashedPw(String email) {
        em = DAOUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Account a WHERE a.email LIKE :email", Account.class)
                    .setParameter("email", email)
                    .getSingleResult()
                    .getPassword();
        } catch (NullPointerException | NoResultException e) {
            System.out.println("No account found with the given email address");
            return null;
        }
    }

    /**
     *
     * @param email Finds if there is any account existing with the given email
     * @return Returns boolean, true if account already exists, false if not.
     */
    public boolean checkIfEmailExists(String email) {
        em = DAOUtil.getEntityManager();
        try {
            List<String> results = em.createQuery(
                    "SELECT a.email FROM Account a", String.class)
                    .getResultList();
            // Email is in database
            if (results.contains(email))
                return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
}
