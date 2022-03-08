package r8.model.dao;

import r8.model.Team;

import javax.persistence.EntityManager;

public class TeamDAO {
    private EntityManager entityManager;

    public TeamDAO() { this.entityManager = DAO.getEntityManager(); }

    public void persist(Team team) {
        entityManager.getTransaction().begin();
        entityManager.persist(team);
        entityManager.getTransaction().commit();
    }

    public void update(Team team) {
        entityManager.getTransaction().begin();
        entityManager.persist(team);
        entityManager.getTransaction().commit();
    }

    public Team get(int teamId) {
        Team team = entityManager.find(Team.class, teamId);
        entityManager.detach(team);
        return team;
    }

    public Team getByName(String name) {
        Team team = null;
        try {
            team = (Team) entityManager.createQuery(
                    "SELECT t FROM Team t WHERE t.teamName LIKE :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return team;
    }

    public void removeTeam(Team team) {
        entityManager.getTransaction().begin();
        entityManager.remove(team);
        entityManager.getTransaction().commit();
    }

    public void removeTeamById(int teamId) {
        Team team = entityManager.find(Team.class, teamId);
        entityManager.remove(team);
    }
}
