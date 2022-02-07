package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginCredentialsViewController {

    public void switchToWorkScene(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(LoginCredentialsViewController.class.getResource("/fxml/main-view.fxml")));
        final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        stage.setTitle("Proma - Project Manager");
        stage.setScene(scene);
        stage.show();
    }
}
