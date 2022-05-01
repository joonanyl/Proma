package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.Sprint;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintDAOTest {
    private static Project project;
    private static Sprint sprint;
    private static SprintDAO sprintDAO;
    private static ProjectDAO projectDAO;

    @BeforeAll
    static void setUpBeforeTesting() {
        project = new Project("project name ", "desc");
        sprint = new Sprint("sprint", LocalDate.now(), LocalDate.now().plusDays(14), project);
        sprintDAO = new SprintDAO();
        projectDAO = new ProjectDAO();
        projectDAO.persist(project);
    }

    @Test
    @Order(1)
    void addAndGetSprint() {
        sprintDAO.persist(sprint);
        assertEquals(sprint.getSprintId(), sprintDAO.get(sprint.getSprintId()).getSprintId(), "Sprintin hakeminen tietokannasta epäonnistui");
    }

    @Test
    @Order(2)
    void update() {
        String oldName = sprint.getName();
        sprint.setName("new name");
        sprintDAO.update(sprint);
        Sprint sprintFromDB = sprintDAO.get(sprint.getSprintId());

        assertEquals(sprint.getName(), sprintFromDB.getName(), "Tietokannasta haettu sprint ei täsmää");
        assertNotEquals(oldName, sprintFromDB.getName(), "Sprintin nimen päivittääminen tietokannassa epäonnistui");
    }

    @Test
    @Order(3)
    void removeAndGetAll() {
        Sprint sprint2 = new Sprint("sprint2", LocalDate.now(), LocalDate.now().plusDays(14), project);

        sprintDAO.persist(sprint2);

        List<Sprint> dbList = sprintDAO.getAll();

//        assertTrue(dbList.contains(sprint2), "Sprint2 ei löydy tietokannan listasta");

        sprintDAO.remove(sprint2);
        dbList = sprintDAO.getAll();

        assertFalse(dbList.contains(sprint2), "Sprint2 ei poistunut tietokannan listasta");

    }

}