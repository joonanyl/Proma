package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.view.IViewController;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;
import java.io.IOException;

public class LoginViewController implements IViewController {

    private App app;
    final String initialView = "login-credentials-view";

    @FXML
    private BorderPane mainViewPane;

    public void initialize() {
       IControllerLogin controller = new Controller();
       controller.setActiveViewController(this);
       setInitialView();
    }

    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event));
    }

    @Override
    public void handleSubviewNavigation(ActionEvent event)  {}

    private void setInitialView() {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(initialView);
        mainViewPane.setCenter(view);
    }

    public App getApp() {
        return this.app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
