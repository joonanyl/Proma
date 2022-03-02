package r8.model.dao;

import r8.model.Account;

import java.util.List;

public class Test {

    public static void main(String args[]) {
        AccountDAO accountDAO = new AccountDAO();
        /*
        accountDAO.addAccount(new Account(
                "Neljäs", "Testikayttaja", "444444444", "testi4@testi.com"));

         */
        Account account = accountDAO.getAccountById(4);
        System.out.println(account);
        List<Account> accounts = accountDAO.getAllAccounts();

        for (Account a: accounts) {
            System.out.println(a);
        }
    }
}
