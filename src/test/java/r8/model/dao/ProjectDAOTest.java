package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Project;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectDAOTest {
    /**
     * ProjectDAO that will be used throughout ProjectDAOTesting
     */
    private static ProjectDAO projectDAO;
    /**
     * Projects that will be used throughout ProjectDAOTesting
     */
    private static Project project, project2;
    static Account account;
    /**
     * AccountDAO that will be used throughout ProjectDAOTesting
     */
    static AccountDAO accountDAO;
    /**
     * Required for generating a random number (accounts email)
     */
    static Random rand;

    /**
     * BeforeAll-method where DAOs, rand and projects are initialized
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        projectDAO = new ProjectDAO();
        accountDAO = new AccountDAO();
        rand = new Random();

        project = new Project("project", "project description");
        project2 = new Project("project2", "project description2");
        account = new Account("etunimi", "sukunimi", "email"+rand.nextInt(150), "salasana");

    }

    /**
     * Test case where saving a project to a DB is being tested
     */
    @Test
    @Order(1)
    void persist(){
        projectDAO.persist(project);
        projectDAO.persist(project2);
        assertTrue(projectDAO.getAll().contains(project), "Projektia ei lisätty tietokantaan");
        assertTrue(projectDAO.getAll().contains(project2), "Projekti2:a ei lisätty tietokantaan");
    }

    /**
     * Test case where removing a project from DB is being tested
     */
    @Test
    @Order(2)
    void remove(){
        projectDAO.remove(project2);
        assertFalse(projectDAO.getAll().contains(project2), "Projekti2:a ei postettu tietokannasta");

    }

    /**
     * Test case where updating a project in a DB is being tested
     */
    @Test
    @Order(3)
    void update(){
        String newName = "uusi nimi projektille";
        String oldName = project.getName();
        project.setName(newName);
        projectDAO.update(project);

        assertNotEquals(oldName, projectDAO.get(project.getProjectId()).getName(), "Projektin nimen päivittäminen epäonnistui");
        assertEquals(newName, projectDAO.get(project.getProjectId()).getName(), "Projektin nimen päivittäminen epäonnistui (2)");
    }

    /**
     * Test case where adding an account to a project in a DB is being tested
     */
    @Test
    @Order(4)
    void addAcc(){
        accountDAO.persist(account);
        projectDAO.addAccount(account, project);
        assertTrue(project.getAccounts().contains(account), "Käyttäjätilin lisääminen projektiin epäonnistui");

    }
    /**
     * Clearing the DB after testing
     */
    @AfterAll
    static void clearDB(){
        projectDAO.remove(project);
        accountDAO.remove(account);
        System.out.println("db cleared");
    }

}
