package r8.model.dao;

import r8.model.Account;

public class Test {

    public static void main(String args[]) {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.addAccount(new Account(
                "Toinen", "Testikayttaja", "111111111", "testi2@testi.com"));
    }
}
