package r8.view.mainView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;

import java.io.IOException;

public class MainViewController {

    @FXML
    private BorderPane mainViewPane;

    public void initialize() {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView("dashboard-view");
        System.out.println(view);
        mainViewPane.setCenter(view);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData);
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(userData);
        mainViewPane.setCenter(view);
    }

    public void switchToLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login-view.fxml"));
        final Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        //loginStage.initStyle(StageStyle.UNDECORATED); // use only for login
        loginStage.setScene(scene);
        loginStage.show();
    }

    public void initButtonIcons() {
        //TODO import icons
    }
}
