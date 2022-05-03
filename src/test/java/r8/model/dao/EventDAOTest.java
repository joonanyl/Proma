package r8.model.dao;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import r8.model.Event;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventDAOTest {

//    static Event event,event2,event3,event4,event5;
//    static EventDAO eventDAO;
//    static Task task;
//    static TaskDAO taskDAO;
//    static Sprint sprint;
//    static SprintDAO sprintDAO;
//
//    @BeforeAll
//    static void beforeTesting(){
//        eventDAO = new EventDAO();
//        taskDAO = new TaskDAO();
//        sprintDAO = new SprintDAO();
//
//        task = new Task("task name", TaskState.NOT_STARTED, new TaskType("tasktype"), 30, "task desc");
//        taskDAO.persist(task);
//        sprint = new Sprint("sprint", LocalDate.now(), LocalDate.now().plusDays(14), new Project("project", "project desc"));
//        sprintDAO.persist(sprint);  // ei salee toimi
//
//
//
//    }

}