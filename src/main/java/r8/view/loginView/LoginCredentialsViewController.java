package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r8.App;
import r8.controller.Controller;
import r8.view.navigation.GlobalControllerReference;
import java.io.IOException;


public class LoginCredentialsViewController {

    private App app;
    private Controller daoController = new Controller();

    @FXML
    TextField textFieldEmail;

    @FXML
    PasswordField passwordField;

    @FXML
    private void login() {

        if(daoController.authenticateLogin(textFieldEmail.getText(), passwordField.getText())){
            toWorkScene();
        }
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getLoginViewController().handleNavigation(event);
    }

    @FXML
    public void toWorkScene() {
        app = GlobalControllerReference.getInstance().getLoginViewController().getApp();
        app.switchToWorkScene();
    }

    //TODO: implement this without GlobalControllerReference

    public void setApp(App app) {
        this.app = app;
    }
}
