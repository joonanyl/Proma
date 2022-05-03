package r8.model.dao;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Comment;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentDAOTest {

//    static Comment parent, parent2, reply, reply2;
//    static CommentDAO commentDAO;
//    static Account author, author2;
//    static AccountDAO accountDAO;
//    static Random rand;
//    static Task task;
//    static TaskDAO taskDAO;
//    static TaskType tt;
//    static TaskTypeDAO ttDAO;
//
//    @BeforeAll
//    static void setUpBeforeTesting(){
//        commentDAO = new CommentDAO();
//        accountDAO = new AccountDAO();
//        taskDAO = new TaskDAO();
//        ttDAO = new TaskTypeDAO();
//        rand = new Random();
//        author = new Account("author", "lastname", "email@something"+Integer.toString(rand.nextInt(100)), "password");
//        author2 = new Account("author2", "lastname2", "email@somethiing"+Integer.toString(rand.nextInt(100)), "passsword");
//        accountDAO.persist(author);
//        accountDAO.persist(author2);
//        tt = new TaskType("tasktype");
//        task = new Task("task", TaskState.NOT_STARTED, tt, 20, "desc");
//        ttDAO.persist(tt);
//        taskDAO.persist(task);
//        parent = new Comment(task, author, "parent comment content");
//    }
//
//    @Test
//    @Order(1)
//    void persist(){
//        commentDAO.persist(parent);
//        assertTrue(commentDAO.getAll().contains(parent));
//    }
//
//    @AfterAll
//    static void clearDB(){
//        commentDAO.remove(parent);
//
//    }
}