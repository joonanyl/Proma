package r8.controller;

import r8.model.AuthService;
import r8.view.IViewController;
import r8.model.Account;
import r8.model.dao.AccountDAO;

/**
 * Interface for {@link Controller} with login specific methods
 * @author Aarni Pesonen
 */
public interface IControllerLogin {

    /**
     * Used to send {@link Account} information to {@link AccountDAO}
     * for new account creation
     * @param firstName input used in creation
     * @param lastName input used in creation
     * @param email input used in creation
     * @param password input used in creation
     */
    void createAccount(String firstName, String lastName, String email, String password);

    /**
     * Interacts with {@link AuthService} to authenticate user login attempts
     * @param email received as user input
     * @param password received as user input
     * @return status of the login attemps success as a boolean
     */
    boolean authenticateLogin(String email, String password);

    /**
     * Return active {@link IViewController}
     * Used in subview navigation
     * @return active {@link IViewController} as interface
     */
    IViewController getActiveViewController();

    /**
     * Sets active {@link IViewController}
     * Used in subview navigation
     * @param viewController to be set as active
     */
    void setActiveViewController(IViewController viewController);

    boolean checkIfEmailExists(String email);
}
