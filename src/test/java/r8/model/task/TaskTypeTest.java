package r8.model.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTypeTest {

    @Test
    void basics(){
        TaskType tp = new TaskType("tasktype");
        assertEquals("tasktype", tp.getName(), "TaskTypen nimi ei täsmää");
        tp.setName("TaskType");
        assertEquals("TaskType", tp.getName(), "TaskTypen nimi ei täsmää(2)");
        assertNotEquals("tasktype", tp.getName(), "TaskTypen nimi ei muuttunut");
    }


}