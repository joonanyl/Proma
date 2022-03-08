package r8.model.dao;


import r8.model.Project;

import javax.persistence.EntityManager;

public class ProjectDAO {
    private EntityManager entityManager;

    public ProjectDAO() {
        this.entityManager = DAO.getEntityManager();
    }

    public void persist(Project project) {
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
    }

    public void update(Project project) {
        entityManager.getTransaction().begin();
        entityManager.merge(project);
        entityManager.getTransaction().commit();
    }

    public Project get(int projectId) {
        Project project = null;

        try {
            project = entityManager.getReference(Project.class, projectId);
            entityManager.detach(project);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return project;
    }

    public Project getByName(String name) {
       Project project = null;
        try {
            project = (Project) entityManager.createQuery
                            ("SELECT p FROM Project p WHERE p.name LIKE :name")
                    .setParameter("name", name)
                    .getSingleResult();
       } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return project;
    }

    public void removeProject(Project project) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
        entityManager.getTransaction().commit();
    }

    public void removeProjectById(int projectId) {
        Project project = null;
        try {
            project = entityManager.find(Project.class, projectId);
            entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
