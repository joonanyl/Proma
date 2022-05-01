package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;


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
    void persist(){

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