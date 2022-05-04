package r8.view.loginView.loginCredentialsView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r8.App;
import r8.model.appState.AppState;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.util.lang.LanguageHandler;
import r8.view.IViewController;

import java.io.IOException;

/**
 * View for entering user login details. This view is initially loaded within parent loginView.
 */
public class LoginCredentialsViewController {

    private App app;
    private IControllerLogin controller;
    private IViewController viewController;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    /**
     * Initializes the view. Retrieves active {@link IViewController} from {@link AppState}.
     */
    public void initialize() {
        controller = new Controller();
        viewController = controller.getActiveViewController();
    }

    /**
     * Called when "Login" button is pressed. Checks is input fields are correctly filled and tries to
     * authenticate user login. If authentication is successful, loads the mainView.
     */
    @FXML
    private void login() {
        if (!textFieldEmail.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            if(controller.authenticateLogin(textFieldEmail.getText(), passwordField.getText())) toWorkScene();
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(LanguageHandler.getText("invalidCredentials"));
                alert.show();
            }
        }
    }

    /**
     * Used to navigate between loginViews subviews
     * @param event
     */
    @FXML
    private void navigate(ActionEvent event) {
        viewController.handleNavigation(event);
    }

    /**
     * Switches from loginView to mainView.
     */
    @FXML
    public void toWorkScene() {
        viewController.getApp().switchScene();
    }

    /**
     * Sets reference for the main application. Required for reloading the parent views after laguage settings have
     * been changed.
     * @param app reference to the main application
     */
    public void setApp(App app) {
        this.app = app;
    }
}
