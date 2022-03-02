package r8.model.dao;

import r8.model.Comment;

import javax.persistence.EntityManager;

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
