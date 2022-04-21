package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;

public abstract class DAO<T> {
    private Class<T> classType;
    private EntityManager em;

    public final void setClassType(final Class<T> classType) {
        this.classType = classType;
    }

    public T get(int id) {
        em = getEntityManager();
        return em.find(classType, id);
    }

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

    // Tähän ehkä getByX()?

    public void persist(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.persist(t));
    }

    public void update(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.merge(t));
    }

    public void remove(T t) {
        em = getEntityManager();
        doInTransaction(em -> em.remove(em.merge(t)));
    }

    protected void doInTransaction(Consumer<EntityManager> action) {
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

    protected EntityManager getEntityManager() {
        return DAOUtil.getEntityManager();
    }
}
