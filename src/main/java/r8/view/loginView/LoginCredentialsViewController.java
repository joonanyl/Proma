package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import r8.App;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;
import java.util.Objects;

public class LoginCredentialsViewController {

    private App app;

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
