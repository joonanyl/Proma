package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamDAOTest {
    static Team team, team2;
    static TeamDAO teamDAO;
    static ProjectDAO projectDAO;
    static Project project;

    @BeforeAll
    static void beforeTesting(){
        teamDAO = new TeamDAO();
        projectDAO = new ProjectDAO();
        project = new Project("project", "project kuvaus");
        team = new Team("team nimi", project);
        team2 = new Team("team2 nimi", project);
        projectDAO.persist(project);

    }

    @Order(1)
    @Test
    void persist(){
        teamDAO.persist(team);
        teamDAO.persist(team2);
        assertEquals(team.getTeamId(), teamDAO.get(team.getTeamId()).getTeamId(), "Tiimiä ei löydy tietokannasta");
        assertEquals(team2.getTeamId(), teamDAO.get(team2.getTeamId()).getTeamId(), "Tiimiä ei löydy tietokannasta(2)");
    }

    @Order(2)
    @Test
    void byProject(){
        List<Team> byProject = teamDAO.getByProject(project);
        boolean foundByPr = false;
        for(Team t:byProject){
            if (t.getTeamId() == team.getTeamId()) {
                foundByPr = true;
                break;
            }
        }
        assertTrue(foundByPr, "Tiimiä ei saatu haettua projektin avulla");
        foundByPr = false;
        for(Team t:byProject){
            if (t.getTeamId() == team2.getTeamId()) {
                foundByPr = true;
                break;
            }
        }
        assertTrue(foundByPr, "Tiimiä 2 ei saatu haettua projektin avulla");
    }

    @Test
    @Order(3)
    void del(){
        teamDAO.remove(team2);
        List<Team> byProject = teamDAO.getByProject(project);
        boolean foundByPr = false;
        for(Team t:byProject){
            if (t.getTeamId() == team2.getTeamId()) {
                foundByPr = true;
                break;
            }
        }
        assertFalse(foundByPr, "Team2 ei poistunut tietokannasta");
        assertFalse(teamDAO.getAll().contains(team2), "Team2 ei poistunut tietokannasta(2)");
    }

    @AfterAll
    static void clearDB(){
        teamDAO.remove(team);
        projectDAO.remove(project);
        System.out.println("db cleared");
    }

}