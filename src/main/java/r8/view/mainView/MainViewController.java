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
import r8.view.dashboardView.DashboardViewController;
import r8.view.navigation.GetView;
import r8.view.navigation.GlobalControllerRef;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {

    @FXML
    private BorderPane mainViewPane;

    @FXML
    private Pane view;

    @FXML
    DashboardViewController dvc;

    public void initialize() {
        GetView viewLoader = new GetView();
        view = viewLoader.getView("dashboard-view");
        System.out.println(view);
        mainViewPane.setCenter(view);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        GlobalControllerRef.getInstance().setMainViewController(this);
        System.out.println(event);
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData);
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(userData);
        mainViewPane.setCenter(view);
    }

    @FXML
    private void handleNavigation(String value) throws IOException {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(value);
        mainViewPane.setCenter(view);
    }

    @FXML
    private void handleProfileClick() throws IOException {
        handleNavigation("profile-view");
    }

    @FXML
    public void switchToLoginScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login-view.fxml")));
        final Stage loginStage = (Stage) mainViewPane.getScene().getWindow();
        final Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.show();
    }

    public void initButtonIcons() {
        //TODO import icons
    }
}
