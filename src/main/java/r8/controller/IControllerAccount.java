package r8.controller;

import r8.model.Account;
import r8.model.AuthService;
import r8.model.dao.AccountDAO;

/**
 * Interface for {@link Controller} with account specific methods
 * @author Aarni Pesonen
 */
public interface IControllerAccount {

    /**
     * Returns active user {@link Account} stored in AppState
     * @return actuve user
     */
    Account getAccount();

    /**
     * Interacts with {@link AuthService} to authenticate user login
     * @param password received as user input
     * @return operation success info as boolean
     */
    boolean authenticatePassword(String password);

    /**
     * Used to send user {@link Account} data to {@link AccountDAO} for updating
     * @param firstName to be updated
     * @param lastName to be updated
     * @param email to be updated
     * @param password to be updated
     */
    void updateAccount(String firstName, String lastName, String email, String password);

}
