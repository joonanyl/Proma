package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamDAOTest {
    /**
     * Teams that will be required throughout TeamDAOTesting
     */
    static Team team, team2;
    /**
     * TeamDAO that will be required throughout TeamDAOTesting
     */
    static TeamDAO teamDAO;
    /**
     * ProjectDAO that will be required throughout TeamDAOTesting
     */
    static ProjectDAO projectDAO;
    /**
     * A project that will be required throughout TeamDAOTesting
     */
    static Project project;

    /**
     * BeforeAll-method that will initialize the required DAOs, project and teams required for testing
     */
    @BeforeAll
    static void beforeTesting(){
        teamDAO = new TeamDAO();
        projectDAO = new ProjectDAO();
        project = new Project("project", "project kuvaus");
        team = new Team("team nimi", project);
        team2 = new Team("team2 nimi", project);
        projectDAO.persist(project);

    }

    /**
     * Test case where adding a team to the DB is being tested
     */
    @Order(1)
    @Test
    void persist(){
        teamDAO.persist(team);
        teamDAO.persist(team2);
        assertEquals(team.getTeamId(), teamDAO.get(team.getTeamId()).getTeamId(), "Tiimiä ei löydy tietokannasta");
        assertEquals(team2.getTeamId(), teamDAO.get(team2.getTeamId()).getTeamId(), "Tiimiä ei löydy tietokannasta(2)");
    }

    /**
     * Test case where fetching a team by an assigned project from DB is being tested
     */
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

    /**
     * Test case where deleting teams from DB is being tested
     */
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

    /**
     * Clearing the DB after testing
     */
    @AfterAll
    static void clearDB(){
        teamDAO.remove(team);
        projectDAO.remove(project);
        System.out.println("db cleared");
    }

}
