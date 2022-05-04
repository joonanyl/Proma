package r8.model.appState;

import r8.view.loginView.LoginViewController;

/**
 * Interface for {@link AppState} used in conjunction with login specific views
 * Only contains {@link AppState} methods appropriate for login specific tasks
 * @author Aarni Pesonen
 */
public interface IAppStateLogin {

    /**
     * Used when a new account is created
     * requires user input for
     * @param firstName as a string
     * @param lastName as a string
     * @param email as a string in email format
     * @param password as a string with specific requirements
     */
    void createAccount(String firstName, String lastName, String email, String password);

    /**
     * For authenticating user logic with bCrypt
     * @param email used as an account identifier
     * @param password needs to match with account and existing hash information
     * @return boolean value indicating the success of the login operation
     */
    boolean authenticateLogin(String email, String password);

    /**
     * Used by login view to get active {@link LoginViewController} used for subview navigation
     * @return currently active {@link LoginViewController}
     */
    LoginViewController getLoginViewController();

    /**
     * Used by login view to set active {@link LoginViewController} used for subview navigation
     * @param loginViewController currently active
     */
    void setLoginViewController(LoginViewController loginViewController);
}
