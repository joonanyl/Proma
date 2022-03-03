package r8.model.dao;

import r8.model.Account;
import r8.model.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO {
    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("Proma");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
