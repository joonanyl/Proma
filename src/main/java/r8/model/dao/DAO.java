package r8.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;

/**
 * Abstract Data Access Object class. Defines CRUD methods for all classes.
 * @param <T> Generic Data Type
 */
public abstract class DAO<T> {
    private Class<T> classType;
    private EntityManager em;

    /**
     * Sets the data type
     * @param classType
     */
    public final void setClassType(final Class<T> classType) {
        this.classType = classType;
    }

    /**
     * @param id
     * @return Finds and returns an object by its id.
     */
    public T get(int id) {
        em = getEntityManager();
        return em.find(classType, id);
    }

    /**
     *
     * @return Returns all entities of a table from the database
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        em = getEntityManager();
        try {
            return em.createQuery("from " + classType.getName()).getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Saves an entity to the database.
     * @param t
     */
    public void persist(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.persist(t));
    }

    /**
     * Updates an existing entity in the database.
     * @param t
     */
    public void update(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.merge(t));
    }

    /**
     * Removes an entity from the database
     * @param t
     */
    public void remove(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.remove(em.merge(t)));
    }

    /**
     * Opens a transaction and then executes an action.
     * @param action An action to be called inside transaction.
     */
    protected void doInTransaction(Consumer<EntityManager> action) {
        em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    protected void setEntityManager(){
        em = DAOUtil.getEntityManager();
    }

    protected EntityManager getEntityManager() {
        return DAOUtil.getEntityManager();
    }
}
