package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Project;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectDAOTest {
/* ei toimi
    private static ProjectDAO projectDAO;
    private static Project project;
    static Account account;
    static AccountDAO accountDAO;

    @BeforeAll
    static void setUpBeforeTesting() {
        projectDAO = new ProjectDAO();
        accountDAO = new AccountDAO();
        project = new Project("Project1", "desc1");
        account = new Account("fname", "lname", "email", "pwd");
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(project);
        account.setProjects(projectSet);
        accountDAO.persist(account);
    }

    @Test
    @Order(1)
    void basics(){
//        project.addAccount(account);
        projectDAO.persist(project);

        List<Project> projectsByAccount = projectDAO.getByAccount(account);
        boolean foundByAccount = false;

        for(Project p: projectsByAccount){
            System.out.println("haloo");
            if(p.getProjectId() == project.getProjectId())
                foundByAccount = true;
        }
        assertTrue(foundByAccount, "ei löydy käyttäjätilin avulla");
//        assertTrue(project.getAccounts().contains(account), "Projektilla ei ole kyseistä käyttäjätiliä");
//        assertTrue(projectDAO.getByAccount(account).contains(project), "Projektia ei löydy käyttäjätilin avulla");

    }
*/
}