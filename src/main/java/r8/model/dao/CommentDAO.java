package r8.model.dao;

import org.hibernate.HibernateException;
import r8.model.Comment;

import javax.persistence.EntityManager;
import java.util.List;

public class CommentDAO {
    private EntityManager entityManager;

    public void persist(Comment comment) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public Comment get(int commentId) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.find(Comment.class, commentId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Comment> getAllReplies(Comment parentComment) {
        entityManager = DAOUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT c FROM Comment c WHERE c.parentComment = :parentComment", Comment.class)
                    .setParameter("parentComment", parentComment)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void update(Comment comment) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(comment);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void remove(Comment comment) {
        entityManager = DAOUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
            entityManager.getTransaction().commit();
        } catch (HibernateException e ) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
 }
