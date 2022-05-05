package r8.model.dao;
import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintDAOTest {
    /**
     * A project that is required for initializing a sprint
     */
    private static Project project;
    /**
     * A sprint that will be used throughout SprintDAOTesting
     */
    private static Sprint sprint;
    /**
     * SprintDAO that will be used throughout SprintDAOTesting
     */
    private static SprintDAO sprintDAO;
    /**
     * ProjectDAO that will be used throughout SprintDAOTesting
     */
    private static ProjectDAO projectDAO;
    /**
     * A task that is required for initializing a sprint
     */
    private static Task task;
    /**
     * TaskDAO that will be used throughout SprintDAOTesting
     */
    private static TaskDAO taskDAO;

    /**
     * BeforeAll-method that initializes the DAOs, a project and a sprint
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        project = new Project("project name ", "desc");
        sprint = new Sprint("sprint", LocalDate.now(), LocalDate.now().plusDays(14), project);
        sprintDAO = new SprintDAO();
        projectDAO = new ProjectDAO();
        projectDAO.persist(project);
    }

    /**
     * Test case where saving and fetching a sprint from the DB is being tested
     */
    @Test
    @Order(1)
    void addAndGetSprint() {
        sprintDAO.persist(sprint);
        assertEquals(sprint.getSprintId(), sprintDAO.get(sprint.getSprintId()).getSprintId(), "Sprintin hakeminen tietokannasta epäonnistui");
    }

    /**
     * Test case where updating a sprint in a DB is being tested
     */
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

    /**
     * Test case where adding a task to a sprint that is already in DB is being tested
     */
    @Test
    @Order(3)
    void task(){
        TaskType tt = new TaskType("tasktype");
        TaskTypeDAO ttDAO = new TaskTypeDAO();
        ttDAO.persist(tt);
        task = new Task("task", TaskState.NOT_STARTED, tt, 10, "desc");
        task.setName("tehtävä");
        taskDAO = new TaskDAO();
        taskDAO.persist(task);
        sprintDAO.addTask(task, sprint);
        assertTrue(sprint.getTasks().contains(task), "Taskin lisääminen epäonnistui");
        taskDAO.remove(task);
    }
    /**
     * Clearing the DB after testing
     */
    @AfterAll
    static void clearDB(){
        sprintDAO.remove(sprint);
        taskDAO.remove(task);
        projectDAO.remove(project);
        System.out.println("db cleared");
    }

}
