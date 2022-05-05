package r8.model;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintTest {

    /**
     * A sprint that will be used throughout sprinttesting
     */
    private static Sprint sprint;
    /**
     * LocalDates that are required for testing and initializing a sprint
     */
    private static LocalDate startD, endD;
    /**
     * Projects that will be used throughout sprinttesting
     */
    private static Project project, project2;

    /**
     * BeforeAll-method that will initialize the projects, dates and the sprint for SprintTesting
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        project = new Project("project", "desc");
        project2 = new Project("project2", "desc");

        startD = LocalDate.now();
        endD = startD.plusDays(14);
        sprint = new Sprint("sprint", startD, endD, project);
    }

    /**
     * Test case where setting and getting the sprint's name is being tested ( as well as changing it )
     */
    @Test
    @Order(1)
    void setName() {
        String newName = "sprint new name";
        sprint.setName(newName);

        assertEquals(newName, sprint.getName(), "Sprintin nimen vaihtaminen epäonnistui");
    }

    /**
     * Test case where getting the sprint's date is being tested
     */
    @Test
    @Order(2)
    void getStartDate() {
        LocalDate testDate = LocalDate.now();
        assertEquals(testDate, sprint.getStartDate(), "Alkupvm ei täsmää");
    }

    /**
     * Test case where setting the start date of a sprint is being tested
     */
    @Test
    @Order(3)
    void setStartDate() {
        int delayInDays = 3;
        sprint.setStartDate(LocalDate.now().plusDays(delayInDays));
        sprint.setEndDate(sprint.getEndDate().plusDays(delayInDays));

        assertEquals(LocalDate.now().plusDays(delayInDays), sprint.getStartDate(),"Alkupäivämäärän muuttaminen epäonnistui");
    }

    /**
     * Test case, where getting the sprints project is being tested
     */
    @Test
    @Order(4)
    void getProject() {
        assertEquals(project, sprint.getProject(), "Projekti ei täsmää");
        assertNotEquals(project2.getName(), sprint.getProject().getName(), "Sprint palautti väärän projektin");
    }

    /**
     * Test case, where setting and changing a project of a sprint is being tested
     */
    @Test
    @Order(5)
    void setProject() {
        sprint.setProject(project2);
        assertEquals(project2, sprint.getProject(), "Projektin hakeminen epäonnistui - palautettu projekti ei täsmää sprintille asetetun projektin kanssa");
        assertNotEquals(project.getName(), sprint.getProject().getName(), "Sprint palautti väärän projektin");

    }
}
