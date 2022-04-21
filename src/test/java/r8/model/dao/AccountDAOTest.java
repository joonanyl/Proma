package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountDAOTest {

    /*
        Tänne myös tiimien, tehtävien ja projektien vaihtamista jne
     */
/*
    private static AccountDAO accountDAO;
    private static Account account1, account2;
    private static int countInDB;

    @BeforeAll
    static void setUpBeforeTesting() {
        accountDAO = new AccountDAO();
        account1 = new Account("etunimi", "sukunimi", "email", "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email2", "pwd2");
        countInDB = accountDAO.getAll().size();
    }

    @Test
    @Order(1)
    void persist(){
        accountDAO.persist(account1);
        assertEquals(account1.getFirstName(), accountDAO.get(account1.getAccountId()).getFirstName(), "Ei toimi");
        assertNotEquals(countInDB, accountDAO.getAll().size(), "Käyttäjätili ei suurella todennäköisyydellä päätynyt tietokantaan");
    }

    @Test
    @Order(2)
    void update(){
        String newName = "vaihdettu etunimi";
        String newLName= "vaihdettu sukunimi";
        String newEmail = "vaihdettu sposti";

        account1.setFirstName(newName);
        accountDAO.update(account1);
        assertNotEquals("etunimi", accountDAO.get(account1.getAccountId()).getFirstName(), "Käyttäjälle ei onnistuttu vaihtamaan etunimeä tietokannassa" );
        assertEquals(newName, accountDAO.get(account1.getAccountId()).getFirstName(), "Käyttäjälle ei onnistuttu vaihtamaan etunimeä tietokannassa(2)");

        account1.setLastName(newLName);
        accountDAO.update(account1);
        assertNotEquals("sukunimi", accountDAO.get(account1.getAccountId()).getLastName(), "Käyttäjälle ei onnistuttu vaihtamaan sukunimeä tietokannassa" );
        assertEquals(newLName, accountDAO.get(account1.getAccountId()).getLastName(), "Käyttäjälle ei onnistuttu vaihtamaan sukunimeä tietokannassa(2)");

        account1.setEmail(newEmail);
        accountDAO.update(account1);
        assertNotEquals("email", accountDAO.get(account1.getAccountId()).getEmail(), "Käyttäjälle ei onnistuttu vaihtamaan spostia tietokannassa" );
        assertEquals(newEmail, accountDAO.get(account1.getAccountId()).getEmail(), "Käyttäjälle ei onnistuttu vaihtamaan spostia tietokannassa(2)");

        String oldPwd = accountDAO.getHashedPw(account1.getEmail());
        account1.setPassword("uusi salasana");
        accountDAO.update(account1);
        assertNotEquals(oldPwd, accountDAO.getHashedPw(account1.getEmail()), "Salasanan vaihto epäonnistui");

        accountDAO.persist(account2);
        assertTrue(accountDAO.checkIfEmailExists(account2.getEmail()), "Käyttäjätiliä ei löydy sähköpostin avulla");
        String account2Email = "uusi sposti.com";
        account2.setEmail(account2Email);
        accountDAO.update(account2);
        assertTrue(accountDAO.checkIfEmailExists(account2Email), "Käyttäjätiliä ei löydy sähköpostin vaihdon jälkeen");
        assertTrue(accountDAO.checkIfEmailExists(account2.getEmail()), "Käyttäjätiliä ei löydy sähköpostin vaihdon jälkeen(2)");
    }

    @Test
    @Order(3)
    void sizeCheck(){
        boolean sizeCheck = false;
        System.out.println("getallsize "+accountDAO.getAll().size());
        if(accountDAO.getAll().size() >= 2)
            sizeCheck = true;

        assertTrue(sizeCheck, "Tietokannassa pitäisi tässä vaiheessa olla vähintään 2 käyttäjätiliä");
    }

    @AfterAll
    static void clearDatabase(){
        accountDAO.removeAccount(account1);
        accountDAO.removeAccount(account2);

        assertEquals(countInDB, accountDAO.getAll().size(), "Testissä käytetyt käyttäjätilit eivät suurella todennäköisyydellä poistuneet tietokannasta");
        assertFalse(accountDAO.checkIfEmailExists(account1.getEmail()), "Account1 poisto taisi epäonnistua");
        assertFalse(accountDAO.checkIfEmailExists(account2.getEmail()), "Account2 poisto taisi epäonnistua");

        System.out.println("db cleared");
    }

 */
}