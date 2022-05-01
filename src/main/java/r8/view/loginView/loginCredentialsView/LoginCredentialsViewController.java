package r8.view.loginView.loginCredentialsView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.view.IViewController;

import java.io.IOException;

public class LoginCredentialsViewController {

    private App app;
    private IControllerLogin controller;
    private IViewController viewController;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    public void initialize() {
        controller = new Controller();
        viewController = controller.getActiveViewController();
    }

    @FXML
    private void login() {
        if (!textFieldEmail.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            if(controller.authenticateLogin(textFieldEmail.getText(), passwordField.getText())) toWorkScene();
        }
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        viewController.handleNavigation(event);
    }

    // Scene switching is handled by App. Need to get its reference here.
    // Reference initially stored in parent controller.
    @FXML
    public void toWorkScene() {
        viewController.getApp().switchScene();
    }

    //TODO remove if unnecessary
    public void setApp(App app) {
        this.app = app;
    }
}
