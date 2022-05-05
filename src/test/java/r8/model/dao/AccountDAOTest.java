package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountDAOTest {
    /**
     * AccountDAO that will be used throughout AccountDAOTesting
     */
    private static AccountDAO accountDAO;
    /**
     * Accounts that will be used throughout AccountDAOTesting
     */
    private static Account account1, account2;
    /**
     * Required for generating a random number (accounts email)
     */
    static Random rand;

    /**
     * BeforeAll-method that will initialize the rand, accountDAO and accounts for testing
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        rand = new Random();
        accountDAO = new AccountDAO();
        account1 = new Account("etunimi", "sukunimi", "email321" + rand.nextInt(1000), "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email4321"+ rand.nextInt(1000), "pwd2");
    }

    /**
     * Test cases where saving, fetching and updating accounts from a DB is being tested
     */
    @Test
    @Order(1)
    void basics(){
        accountDAO.persist(account1);
        accountDAO.persist(account2);
        assertEquals(account1.getAccountId(), accountDAO.getByEmail(account1.getEmail()).getAccountId(), "käyttäjätilin hakeminen tietokannasta sähköpostilla epäonnistui");
        assertEquals(account2.getAccountId(), accountDAO.getByEmail(account2.getEmail()).getAccountId(), "käyttäjätilin hakeminen tietokannasta sähköpostilla epäonnistui (2)");

        assertTrue(accountDAO.checkIfEmailExists(account1.getEmail()), "Käyttäjätiliä ei löytynyt sähköpostilla");
        assertTrue(accountDAO.checkIfEmailExists(account2.getEmail()), "Käyttäjätiliä ei löytynyt sähköpostilla(2)");

        assertEquals(account1.getAccountId(), accountDAO.get(account1.getAccountId()).getAccountId(), "Käyttäjätiliä ei löytynyt");
        assertEquals(account2.getAccountId(), accountDAO.get(account2.getAccountId()).getAccountId(), "Käyttäjätiliä ei löytynyt(2)");

        assertTrue(accountDAO.getAll().contains(account1), "Acc1 ei löydy tietokannasta");
        assertTrue(accountDAO.getAll().contains(account2), "Acc2 ei löydy tietokannasta");

        String newName = "new first name";
        String oldName = account1.getFirstName();
        account1.setFirstName(newName);
        accountDAO.update(account1);
        assertNotEquals(oldName, accountDAO.get(account1.getAccountId()).getFirstName(), "Etunimen päivitys epäonnistui");

    }

    /**
     * Test case where changing an account's password is being tested
     */
    @Test
    @Order(2)
    void pwd(){
        String pwd1 = account1.getPassword();
        assertEquals(pwd1, accountDAO.getHashedPw(account1.getEmail()), "Salasana väärin");
    }

    /**
     * Test case where an account is removed from a DB
     */
    @Test
    @Order(3)
    void remove(){
        accountDAO.remove(account2);
        assertFalse(accountDAO.getAll().contains(account2), "Käyttäjätilin poistaminen epäonnistui");

    }

    /**
     * Clearing the DB after testing
     */
    @AfterAll
    static void clearDB(){
        accountDAO.remove(account1);
        System.out.println("db cleared");
    }

}
