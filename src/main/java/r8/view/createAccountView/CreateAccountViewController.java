package r8.view.createAccountView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import r8.view.navigation.GlobalControllerRef;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerRef.getInstance().getLoginViewController().handleNavigation(event);
    }
}
