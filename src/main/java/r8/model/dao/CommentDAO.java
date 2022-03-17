package r8.model.dao;

import r8.model.Comment;

import javax.persistence.EntityManager;
import java.util.List;

public class CommentDAO {
    private EntityManager entityManager;

    public CommentDAO() { this.entityManager = DAO.getEntityManager(); }

    public void addComment(Comment comment) {
        entityManager.getTransaction().begin();
        entityManager.persist(comment);
        entityManager.getTransaction().commit();
    }

    public Comment get(int commentId) {
        Comment comment = entityManager.getReference(Comment.class, commentId);
        entityManager.detach(comment);
        return comment;
    }

    public List<Comment> getAll() {
        List<Comment> results = null;
        try {
            results = entityManager.createQuery("SELECT c FROM Comment c", Comment.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Comment> getByParentComment(Comment comment) {
        List<Comment> results = null;
        try {
            results = entityManager.createQuery(
                    "SELECT c FROM Comment c WHERE c.parentComment = :comment", Comment.class)
                    .getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void update(Comment comment) {
        entityManager.getTransaction().begin();
        entityManager.merge(comment);
        entityManager.getTransaction().commit();
    }

    public void remove(Comment comment) {
        entityManager.getTransaction().begin();
        entityManager.remove(comment);
        entityManager.getTransaction().commit();
    }
 }
