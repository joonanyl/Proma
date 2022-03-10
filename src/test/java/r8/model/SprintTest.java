package r8.model;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintTest {

    private static Sprint sprint;
    private static LocalDate startD, endD;
    private static Project project, project2;

    @BeforeAll
    static void setUpBeforeTesting() {
        project = new Project("project", "desc");
        project2 = new Project("project2", "desc");

        startD = LocalDate.now();
        endD = startD.plusDays(14);
        sprint = new Sprint("sprint", startD, endD, project);
    }

    @Test
    @Order(1)
    void setName() {
        String newName = "sprint new name";
        sprint.setName(newName);

        assertEquals(newName, sprint.getName(), "Sprintin nimen vaihtaminen epäonnistui");
    }

    @Test
    @Order(2)
    void getStartDate() {
        LocalDate testDate = LocalDate.now();
        assertEquals(testDate, sprint.getStartDate(), "Alkupvm ei täsmää");
    }

    @Test
    @Order(3)
    void setStartDate() {
        int delayInDays = 3;
        sprint.setStartDate(LocalDate.now().plusDays(delayInDays));
        sprint.setEndDate(sprint.getEndDate().plusDays(delayInDays));

        assertEquals(LocalDate.now().plusDays(delayInDays), sprint.getStartDate(),"Alkupäivämäärän muuttaminen epäonnistui");
    }

    @Test
    @Order(4)
    void getProject() {
        assertEquals(project, sprint.getProject(), "Projekti ei täsmää");
        assertNotEquals(project2, sprint.getProject(), "Sprint palautti väärän projektin");
    }

    @Test
    @Order(5)
    void setProject() {
        sprint.setProject(project2);
        assertEquals(project2, sprint.getProject(), "Projektin hakeminen epäonnistui - palautettu projekti ei täsmää sprintille asetetun projektin kanssa");
        assertNotEquals(project, sprint.getProject(), "Sprint palautti väärän projektin");

    }
}