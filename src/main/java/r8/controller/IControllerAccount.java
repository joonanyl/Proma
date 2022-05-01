package r8.controller;

import r8.model.Account;

public interface IControllerAccount {

    Account getAccount();
    boolean authenticatePassword(String password);
    void updateAccount(String firstName, String lastName, String email, String password);

}
