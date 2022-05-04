package r8.controller;

import r8.model.Account;

/**
 * Interface for {@link Controller} with account specific methods
 * @author Aarni Pesonen
 */
public interface IControllerAccount {

    /**
     * Returns
     * @return
     */
    Account getAccount();
    boolean authenticatePassword(String password);
    void updateAccount(String firstName, String lastName, String email, String password);

}
