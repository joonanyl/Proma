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
        taskDAO = new TaskDAO();
        projectDAO = new ProjectDAO();
        project = new Project("project", "desc");
        taskType = new TaskType("tasktype x");
        task = new Task("task", TaskState.NOT_STARTED, taskType, 3, "desc");
        task.setProject(project);
        taskTypeDAO = new TaskTypeDAO();
    }

    @Test
    @Order(1)
    void persist() {
        taskTypeDAO.persist(taskType);
        projectDAO.persist(project);
        taskDAO.persist(task);
    }

    @Test
    @Order(2)
    void get() {
        assertEquals(task, taskDAO.get(task.getTaskId()), "Taskin hakeminen tietokannasta epäonnistui");
    }
    @Disabled
    @Test
    void getAll() {

    }
    @Disabled
    @Test
    void getByTeam() {

    }
    @Disabled
    @Test
    void getByProject() {

    }
    @Disabled
    @Test
    void getByAccount() {

    }
    @Disabled
    @Test
    void getByTaskType() {

    }
    @Disabled
    @Test
    void update() {

    }
    @Disabled
    @Test
    void remove() {

    }

    @AfterAll
    static void clearDatabase(){
        taskTypeDAO.remove(taskType);
        taskDAO.remove(task);
        projectDAO.removeProject(project);

    } */
}