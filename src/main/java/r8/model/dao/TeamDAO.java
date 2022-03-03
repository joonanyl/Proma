package r8.model.dao;

import r8.model.Team;

import javax.persistence.EntityManager;

public class TeamDAO {
    private EntityManager entityManager;

    public TeamDAO() { this.entityManager = DAO.getEntityManager(); }

    public void addTeam(Team team) {
        entityManager.getTransaction().begin();

        entityManager.persist(team);
        entityManager.getTransaction().commit();
    }

    public Team getTeam(int teamId) {
        Team team = entityManager.find(Team.class, teamId);
        entityManager.detach(team);
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
