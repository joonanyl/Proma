package r8.model.appState;

public interface IAppStateLogin {

    void createAccount(String firstName, String lastName, String email, String password);
    boolean authenticateLogin(String email, String password);
}
