package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r8.App;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateLogin;
import java.io.IOException;


public class LoginCredentialsViewController {

    private App app;
    final IAppStateLogin appStateLogin = AppState.getInstance();

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() {

        if(appStateLogin.authenticateLogin(textFieldEmail.getText(), passwordField.getText())){
            toWorkScene();
        }
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateLogin.getLoginViewController().handleNavigation(event);
    }

    @FXML
    public void toWorkScene() {
        app = appStateLogin.getLoginViewController().getApp();
        app.switchToWorkScene();
    }

    //TODO: implement this without GlobalControllerReference

    public void setApp(App app) {
        this.app = app;
    }
}
