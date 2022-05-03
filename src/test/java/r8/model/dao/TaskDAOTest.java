package r8.model.dao;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskDAOTest {

//    static TaskDAO taskDAO;
//    static Task task;
//    static TaskType tt;
//    static TaskTypeDAO ttDAO;
//    static Project project;
//    static ProjectDAO projectDAO;
//    static Account account;
//    static AccountDAO accountDAO;
//    static Team team;
//    static TeamDAO teamDAO;
//
//    @BeforeAll
//    static void setUpBeforeTesting() {
//        taskDAO = new TaskDAO();
//        ttDAO = new TaskTypeDAO();
//        projectDAO = new ProjectDAO();
//        accountDAO = new AccountDAO();
//        teamDAO = new TeamDAO();
//
//        tt = new TaskType("test bug");
//        project = new Project("project", "project desc");
//        account = new Account("fname", "lname", "email@proma.com", "password");
//        team = new Team("team1", project);
//
//        task = new Task("task1", TaskState.NOT_STARTED, tt, 30, "task desc");
//        task.addTeam(team);
//        team.setProject(project);
//        project.addTask(task);
//        task.addAccount(account);
//
//        Set<Task> tasks = new HashSet<>();
//        tasks.add(task);
//        project.setTasks(tasks);
//        account.setTask(task);
//
//        ttDAO.persist(tt);
//        accountDAO.persist(account);
////        projectDAO.persist(project);
////        teamDAO.persist(team);
//    }
//
//    @Test
//    @Order(1)
//    void basics(){
//        taskDAO.persist(task);
//
//        assertEquals(task.getTaskId(), taskDAO.get(task.getTaskId()).getTaskId(), "EI TOIMI");
//
//        List<Task> tasksByProject = taskDAO.getByProject(project);
//        boolean foundByProject = false;
//
//        for(Task t : tasksByProject){
//            System.out.println("HERE WE ARE");
//            System.out.println("task: "+t.getTaskId());
//            if(t.getTaskId() == task.getTaskId())
//                foundByProject = true;
//        }
//
//        assertTrue(foundByProject, "Ei löydy projektin avulla");
//
//
//        List<Task> tasksByAccount = taskDAO.getByAccount(account);
//        boolean foundByAccount = false;
//
//        for(Task t : tasksByAccount){
//            System.out.println("HERE WE ARE");
//            System.out.println("task: "+t.getTaskId());
//            if(t.getTaskId() == task.getTaskId())
//                foundByAccount = true;
//        }
//        assertTrue(foundByAccount, "Ei löydy käyttäjätilin avulla");
//
//    }

}
