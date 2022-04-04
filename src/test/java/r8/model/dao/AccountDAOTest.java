package r8.model.dao;

import org.junit.jupiter.api.*;
import r8.model.Account;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountDAOTest {

    private static AccountDAO accountDAO;
    private static Account account1, account2, account3, account4;

    @BeforeAll
    static void setUpBeforeTesting() {
        accountDAO = new AccountDAO();
        account1 = new Account("etunimi", "sukunimi", "email", "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email2", "pwd2");
        account3 = new Account("etunimi3", "sukunimi3", "email3", "pwd3");
        account4 = new Account("etunimi4", "sukunimi4", "email4", "pwd4");
    }

    @Test
    @Order(1)
    void persist(){
        accountDAO.persist(account1);
        assertEquals(account1.getFirstName(), accountDAO.get(account1.getAccountId()).getFirstName(), "Ei toimi");
        //assertTrue(accountDAO.getAll().contains(account1), "ei toimi");
    }


    @AfterAll
    static void clearDatabase(){
        accountDAO.removeAccount(account1);
        System.out.println("db cleared");
    }
}