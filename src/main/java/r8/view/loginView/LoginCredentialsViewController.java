package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;
import java.util.Objects;

public class LoginCredentialsViewController {

    @FXML
    private AnchorPane anchorPaneContent;

    @FXML
    public void switchToWorkScene(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(LoginCredentialsViewController.class.getResource("/fxml/main-view.fxml")));
        final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        stage.setTitle("Proma - Project Manager");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getLoginViewController().handleNavigation(event);
    }
}
