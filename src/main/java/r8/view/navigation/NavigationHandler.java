package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class NavigationHandler {

    /*private Stage loginStage;
    private Scene scene;*/

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

   /* public void switchToLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login-view.fxml")));
        loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //loginStage.initStyle(StageStyle.UNDECORATED); // use only for login
        loginStage.setScene(scene);
        loginStage.show();
    }

    public void switchToWorkScene(ActionEvent event) throws IOException {
        loginStage.hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main-view.fxml")));
        Stage workStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        workStage.setTitle("Proma - Project Manager");
        //loginStage.initStyle(StageStyle.DECORATED);
        workStage.setScene(scene);
        workStage.show();
    }*/
}
