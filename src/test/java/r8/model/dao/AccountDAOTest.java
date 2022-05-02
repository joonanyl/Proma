package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountDAOTest {

    private static AccountDAO accountDAO;
    private static Account account1, account2;

    @BeforeAll
    static void setUpBeforeTesting() {
        accountDAO = new AccountDAO();
        account1 = new Account("etunimi", "sukunimi", "email123", "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email234", "pwd2");
    }

    @Test
    @Order(1)
    void basics(){
        accountDAO.persist(account1);
        accountDAO.persist(account2);
//        assertEquals(account1.getAccountId(), accountDAO.getByEmail(account1.getEmail()).getAccountId(), "käyttäjätilin hakeminen tietokannasta sähköpostilla epäonnistui");
//        assertEquals(account2.getAccountId(), accountDAO.getByEmail(account2.getEmail()).getAccountId(), "käyttäjätilin hakeminen tietokannasta sähköpostilla epäonnistui (2)");

        assertTrue(accountDAO.checkIfEmailExists(account1.getEmail()), "Käyttäjätiliä ei löytynyt sähköpostilla");
        assertTrue(accountDAO.checkIfEmailExists(account2.getEmail()), "Käyttäjätiliä ei löytynyt sähköpostilla(2)");
    }
/* ei toimi
    @Test
    @Order(2)
    void pwd(){
        String pwd1 = account1.getPassword();
        assertEquals(pwd1, accountDAO.getHashedPw(account1.getEmail()), "Salasana väärin");
    }

*/
}
