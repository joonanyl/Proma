package r8.model.dao;




import r8.model.Project;

import javax.persistence.EntityManager;

public class ProjectDAO {
    private EntityManager entityManager;

    public ProjectDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void addProject(Project project) {
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
    }

    public Project getProject(int projectId) {
        Project project = entityManager.getReference(Project.class, projectId);
        entityManager.detach(project);
        return project;
    }

    public void removeProject(Project project) {
        entityManager.remove(entityManager.merge(project));
    }

    public void removeProjectById(int projectId) {
        Project project = entityManager.find(Project.class, projectId);
        entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
    }
}
