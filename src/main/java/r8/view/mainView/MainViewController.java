package r8.view.mainView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import java.util.Objects;


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
    private final BreadcrumbBar breadcrumbBar = new BreadcrumbBar();

    // used to prevent loading current view repeatedly
    // TODO must change when top bar nav is used
    private String currentView;

    public void initialize() {
        initSubview(initialView);
    }

    // reference to active view controller currently in AppState.
    // method is called by left nav bar and subviews
    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event));
        addBreadcrumb(event);
    }

    // Topbar dropdown menuItem navigation
    @FXML
    private void handleMenuItemNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleMenuItemNavigation(event));
        this.currentView = nav.getCurrentView();
        clearBreadCrumbs();
        addMenuBreadcrumb(event);
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

    // loads initial subview based on BreadcrumbObject received as parameter
    private void initSubview(BreadcrumbObject bcObj) {
        addBreadcrumb(initialView);
        GetView viewLoader = new GetView();
        view = viewLoader.getView(bcObj.getButtonInfo()[0]);
        mainViewPane.setCenter(view);
    }

    private void addBreadcrumb(ActionEvent event) {
        final Node eventSource = (Node) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())){
            clearBreadCrumbs();
            hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(event));
        }
    }

    private void addBreadcrumb(BreadcrumbObject bcObj) {
        hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(bcObj));
    }

    private void addMenuBreadcrumb (ActionEvent event) {
        MenuItem eventSource = (MenuItem) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())) {
            clearBreadCrumbs();
            hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.addMenu(event));
        }
    }

    // called by left and top navbars
    private void clearBreadCrumbs() {
        hBoxBreadcrumb.getChildren().clear();
        breadcrumbBar.clear();
    }

    public App getApp() {
        return this.app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
