package r8.model.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import r8.model.Account;

import java.util.List;

public class AccountDAO {

    public void addAccount(Account acc) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(acc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Account getAccountById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account account = (Account) session.get(Account.class, id);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Account> accountList = (List<Account>) session.createQuery("from Account").list();
            return accountList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
