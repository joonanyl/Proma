package r8.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class which is used for storing an EntityManagerFactory and getting EntityManagers from it.
 */
public class DAOUtil {
    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("Proma");

    public static EntityManager getEntityManager() {
            return entityManagerFactory.createEntityManager();
        }
}
