package r8.view.loginView;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import r8.App;
import r8.view.IViewController;
import r8.view.navigation.NavigationHandler;

public class LoginViewController implements IViewController {

    final String initialView = "login-credentials-view";

    @FXML
    private BorderPane mainViewPane;

    public void initialize() {
        Pane view = App.getView(initialView);
        mainViewPane.setCenter(view);
    }

    public void handleNavigation(ActionEvent event) throws IOException{
        /* NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event)); */
        App.handleNavigation(event);
    }

    @Override
    public App getApp() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setApp(App app) {
        // TODO Auto-generated method stub
        
    }
}
