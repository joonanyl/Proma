package r8.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOUtil {
    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("Proma");

    public static EntityManager getEntityManager() {
            return entityManagerFactory.createEntityManager();
        }
}
