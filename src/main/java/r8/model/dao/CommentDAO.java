package r8.model.dao;

import r8.model.Comment;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class CommentDAO extends DAO<Comment> {
    private EntityManager em;

    public CommentDAO() {
        setClassType(Comment.class);
    }

    public List<Comment> getAllReplies(Comment parentComment) {
        entityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Comment c WHERE c.parentComment = :parentComment", Comment.class)
                    .setParameter("parentComment", parentComment)
                    .getResultList();
        } catch (NullPointerException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Comment> getCommentsByTask(Task task){
        entityManager();
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.task = :task", Comment.class)
                    .setParameter("task", task).getResultList();
        } catch (NullPointerException e){
            return null;
        } finally {
            em.close();
        }
    }

    private void entityManager() {
        em = super.getEntityManager();
    }
 }
