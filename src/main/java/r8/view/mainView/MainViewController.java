package r8.view.mainView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import r8.App;
import r8.view.IViewController;
import r8.view.navigation.BreadcrumbBar;
import r8.view.navigation.BreadcrumbObject;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;
import java.io.IOException;


// TODO refactor methods
public class MainViewController implements IViewController {

    private App app;

    @FXML
    private Label labelPromaLogo = new Label();

    @FXML
    private BorderPane mainViewPane;

    @FXML
    private Pane view;

    @FXML
    private HBox hBoxBreadcrumb;

    @FXML
    private final BreadcrumbObject initialView = new BreadcrumbObject("dashboard-view", "Dashboard");

    // used to prevent loading current view repeatedly
    // TODO must change when top bar nav is used
    private String currentView;

    public void initialize() {

        GetView viewLoader = new GetView();
        view = viewLoader.getView(initialView.getButtonInfo()[0]);
        mainViewPane.setCenter(view);

        BreadcrumbBar breadcrumbBar = new BreadcrumbBar();
        breadcrumbBar.add(initialView);

        hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.createBreadcrumbs());
    }

    // reference to active view controller currently in AppState.
    // method is called by left nav bar and subviews
    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event));

        /*final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        Button target = (Button) event.getTarget();
        String viewType = target.getText();

            hBoxBreadcrumb.getChildren().clear();
            breadcrumbs.clear();
            breadcrumbButtons.clear();
            System.out.println("Clicked " + userData);
            mainViewPane.setCenter(nav.handleNavigation(event));
            currentView = userData;
            BreadcrumbObject test = new BreadcrumbObject(userData, viewType);
            breadcrumbs.add(test);
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
            createBreadcrumbs();*/

        this.currentView = nav.getCurrentView();
    }

    // Topbar dropdown menuItem navigation
    @FXML
    private void handleMenuItemNavigation(ActionEvent event) {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleMenuItemNavigation(event));
        this.currentView = nav.getCurrentView();
    }

    @FXML
    private void backToLoginScene(){
        app.switchScene();
    }

    // Update UI while running
    public void updateUI() {
        Platform.runLater(new Runnable() {
            public void run() {
                // tarvitaanko ainoastaan kellonajan näyttämiseen?
            }
        });
    }

    public App getApp() {
        return this.app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
