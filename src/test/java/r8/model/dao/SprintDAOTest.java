package r8.model.dao;
import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintDAOTest {
    private static Project project;
    private static Sprint sprint;
    private static SprintDAO sprintDAO;
    private static ProjectDAO projectDAO;
    private static Task task;
    private static TaskDAO taskDAO;

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

    @AfterAll
    static void clearDB(){
        sprintDAO.remove(sprint);
        taskDAO.remove(task);
        projectDAO.remove(project);
        System.out.println("db cleared");
    }

}
