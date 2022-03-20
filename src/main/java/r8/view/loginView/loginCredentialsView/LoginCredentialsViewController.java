package r8.view.loginView.loginCredentialsView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r8.App;
import r8.controller.IControllerLogin;

public class LoginCredentialsViewController {

    private IControllerLogin controller;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    public void initialize() {
    }

    @FXML
    private void login() {
        if (!textFieldEmail.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            controller.authenticateLogin(textFieldEmail.getText(), passwordField.getText());
            toWorkScene();
        }
    }

    @FXML
    private void navigate(ActionEvent event) {
        App.handleNavigation(event);
    }

    @FXML
    private void toWorkScene() {
        App.switchToScene("main-view", "Proma - Project Manager v0.1", true, true);
    }
}
