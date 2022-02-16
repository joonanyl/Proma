package r8.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import r8.model.Account;
import r8.model.Project;

public class ProjectDAO {

    public void addProject(Project project) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(project);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Project getProjectById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Project project = (Project) session.get(Project.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
