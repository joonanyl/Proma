package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Project;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectDAOTest {

    private static ProjectDAO projectDAO;
    private static Project project, project2;
    static Account account;
    static AccountDAO accountDAO;
    static Random rand;

    @BeforeAll
    static void setUpBeforeTesting() {
        projectDAO = new ProjectDAO();
        accountDAO = new AccountDAO();
        rand = new Random();

        project = new Project("project", "project description");
        project2 = new Project("project2", "project description2");
        account = new Account("etunimi", "sukunimi", "email"+rand.nextInt(150), "salasana");

    }

    @Test
    @Order(1)
    void persist(){
        projectDAO.persist(project);
        projectDAO.persist(project2);
        assertTrue(projectDAO.getAll().contains(project), "Projektia ei lisätty tietokantaan");
        assertTrue(projectDAO.getAll().contains(project2), "Projekti2:a ei lisätty tietokantaan");
    }

    @Test
    @Order(2)
    void remove(){
        projectDAO.remove(project2);
        assertFalse(projectDAO.getAll().contains(project2), "Projekti2:a ei postettu tietokannasta");

    }

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

    @Test
    @Order(4)
    void addAcc(){
        accountDAO.persist(account);
        projectDAO.addAccount(account, project);
        assertTrue(project.getAccounts().contains(account), "Käyttäjätilin lisääminen projektiin epäonnistui");

    }

    @AfterAll
    static void clearDB(){
        projectDAO.remove(project);
        accountDAO.remove(account);
        System.out.println("db cleared");
    }

}
