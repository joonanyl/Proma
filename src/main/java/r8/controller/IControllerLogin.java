package r8.controller;

import r8.view.IViewController;

public interface IControllerLogin {

    void createAccount(String firstName, String lastName, String email, String password);
    boolean authenticateLogin(String email, String password);

    IViewController getActiveViewController();

    void setActiveViewController(IViewController viewController);
}
