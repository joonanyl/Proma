package r8.model.dao;

import r8.model.Comment;
import r8.model.task.Task;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data Access Object for Comment class.
 * @see Comment
 * @author Joona Nylander
 */

public class CommentDAO extends DAO<Comment> {
    private EntityManager em;

    public CommentDAO() {
        setClassType(Comment.class);
    }


    /**
     *
     * @param parentComment
     * @return Returns a list of all replies to a single Comment.
     */
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

    /**
     *
     * @param task
     * @return Returns a list of all comments under a task.
     */
    public List<Comment> getCommentsByTask(Task task){
        entityManager();
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.task = :task ORDER BY c.timeStamp DESC", Comment.class)
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
