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

/**
 * Controller for the parent login view.
 * All login subviews are loaded within this view
 * creating a single page app feel for the software.
 * @author Aarni Pesonen
 */
public class LoginViewController implements IViewController {

    private App app;
    final String initialView = "login-credentials-view";

    @FXML
    private BorderPane mainViewPane;

    /**
     * Performs operations necessary for the view initialization.
     * Sets current {@link IControllerLogin} as active view controller.
     * Loads login credentials view as the initial subview
     */
    public void initialize() {
       IControllerLogin controller = new Controller();
       controller.setActiveViewController(this);
       setInitialView();
    }

    /**
     * Used to navigate to the main view of the application
     * @param event triggering the navigation
     */
    public void handleNavigation(ActionEvent event) {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event));
    }

    /**
     * Used to navigate between login subviews:
     * createAccountView, forgotPasswordView, loginCredentialsView
     * @param event triggering the navigation is a button click
     */
    @Override
    public void handleSubviewNavigation(ActionEvent event)  {}

    /**
     * Sets initial subview according to initialView variable
     */
    private void setInitialView() {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(initialView);
        mainViewPane.setCenter(view);
    }

    /**
     * Return reference to main application
     * @return main application reference
     */
    public App getApp() {
        return this.app;
    }

    /**
     * Sets reference to main application
     * @param app reference for main application
     */
    public void setApp(App app) {
        this.app = app;
    }
}
