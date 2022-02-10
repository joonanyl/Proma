package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import r8.view.navigation.GetView;
import r8.view.navigation.GlobalControllerRef;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private BorderPane mainViewPane;

    public void initialize() {
        GlobalControllerRef.getInstance().setLoginViewController(this);
        System.out.println("pewpew");
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView("login-credentials-view");
        System.out.println(view);
        mainViewPane.setCenter(view);
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData);
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(userData);
        mainViewPane.setCenter(view);
    }

    private LoginViewController getController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-view.fxml"));
        return loader.getController();
    }
}
