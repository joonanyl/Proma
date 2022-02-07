package hibis;

import hibis.Postinumero;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class SesFac {

    private SessionFactory sessionFactory = null;

    private Transaction transaction = null;


    public SesFac() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("SessionFactoryn luonti ei onnistunut: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void Finalize() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
                System.err.println("SessionFactoryn suljettu.");
            }
        } catch (Exception e) {
            System.err.println("SessionFactoryn sulkeminen epäonnistui: " + e.getMessage());
        }
    }

    public boolean createPostinumero(Postinumero pn) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(pn);
            transaction.commit();
            session.close();

        } catch (Exception e) {
            System.out.println(e);
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
        return false;
    }
}