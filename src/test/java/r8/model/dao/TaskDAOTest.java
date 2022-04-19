package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Project;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskDAOTest {
    /*

    private static Task task;
    private static TaskDAO taskDAO;
    private static TaskType taskType;
    private static Project project;
    private static ProjectDAO projectDAO;
    private static TaskTypeDAO taskTypeDAO;


    @BeforeAll
    static void setUpBeforeTesting() {
        project = new Project("project", "desc");
        taskType = new TaskType("tasktype x");
        taskDAO = new TaskDAO();
        projectDAO = new ProjectDAO();
        taskTypeDAO = new TaskTypeDAO();
        taskTypeDAO.persist(taskType);
        projectDAO.persist(project);
        task = new Task("task", TaskState.NOT_STARTED, taskType, 3, "desc");
        task.setProject(project);
        System.out.println("project id: " + project.getProjectId());
    }

    @Test
    @Order(1)
    void persist() {
        taskDAO.persist(task);
        assertEquals(task.getName(), taskDAO.get(task.getTaskId()).getName(), "Tietokannasta haettu taskname ei täsmää");
    }

    @Test
    @Order(2)
    void getTaskBytId(){
        assertEquals(task.getTaskId(), taskDAO.get(task.getTaskId()).getTaskId(), "Tehtävän hakeminen tietokannasta id:n avulla epäonnistui");
    }

    // getAll pitää myös testata mut eka pitää tyhjentää db

    @Test
    @Order(3)
    void getByTeam(){
        // täää ei tuu toimii

    }

    @AfterAll
    static void clearDatabase(){
        projectDAO.remove(project);

        System.out.println("db cleared");
    }

     */
}