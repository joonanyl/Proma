package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectDAOTest {


    private static ProjectDAO projectDAO;
    private static Project project1, project2, project3, project4;

    @BeforeAll
    static void setUpBeforeTesting() {
        projectDAO = new ProjectDAO();
        project1 = new Project("Project1", "desc1");
        project2 = new Project("Project2", "desc2");
        project3 = new Project("Project3", "desc3");
        project4 = new Project("Project4", "desc4");

    }

    @Test
    @Order(1)
     void persist() {
        projectDAO.persist(project1);
        assertEquals(project1, projectDAO.get(project1.getProjectId()), "Projektia ei löytynyt tietokannasta (2)");
    }

    @Test
    @Order(2)
    void update() {
        projectDAO.persist(project2);

        project2.setName(project1.getName());

        projectDAO.update(project2);

        Project result = projectDAO.get(project2.getProjectId());

        assertEquals(project1.getName(), result.getName(), "Projektin nimen muuttaminen tietokannassa asti epäonnistui");
    }

    @Test
    @Order(3)
    void deleteProjects(){

        projectDAO.persist(project3);
        projectDAO.persist(project4);


        projectDAO.remove(project3);
        projectDAO.remove(project4);

        List<Project> list = projectDAO.getAll();

        assertEquals(0, list.size(), "Projektien poistaminen epäonnistui");
    }

    @AfterEach
    void clearDatabase(){
        projectDAO.remove(project1);
        projectDAO.remove(project2);
        projectDAO.remove(project3);
        projectDAO.remove(project4);
        System.out.println("db cleared");
    }


}