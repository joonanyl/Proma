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
import r8.util.UIElementVisibility;
import r8.view.navigation.BreadcrumbBar;
import r8.view.navigation.BreadcrumbObject;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;
import r8.view.IViewController;
import r8.App;
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
    private HBox hBoxQuicknav = new HBox();

    @FXML
    private BorderPane quicknavContent;

    @FXML
    private HBox hBoxBreadcrumb;

    @FXML
    private final BreadcrumbObject initialView = new BreadcrumbObject("dashboard-view", "Dashboard");
    private final BreadcrumbBar breadcrumbBar = new BreadcrumbBar();

    private NavigationHandler nav = new NavigationHandler();
    private UIElementVisibility visibility = new UIElementVisibility();

    // used to prevent loading current view repeatedly
    // TODO must change when top bar nav is used
    private String currentView;

    public void initialize() {

        initSubview(initialView);
        visibility.toggleOff(hBoxQuicknav);
    }

    // reference to active view controller currently in AppState.
    // method is called by left nav bar buttons
    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        mainViewPane.setCenter(nav.handleNavigation(event));
        clearBreadCrumbs();
        createBreadcrumb(event);
        currentView = breadcrumbBar.getCurrentView();
    }

    // Topbar dropdown menuItem navigation
    @FXML
    private void handleMenuItemNavigation(ActionEvent event) throws IOException {
        mainViewPane.setCenter(nav.handleMenuItemNavigation(event));
        clearBreadCrumbs();
        createMenuBreadcrumb(event);
    }

    // called by subview controllers navigate()
    public void handleSubviewNavigation(ActionEvent event) throws IOException {
        mainViewPane.setCenter(nav.handleNavigation(event));
    }

    @FXML
    private void toggleQuicknav() {
        visibility.toggleVisibility(hBoxQuicknav);
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
        createBreadcrumb(initialView);
        GetView viewLoader = new GetView();
        view = viewLoader.getView(bcObj.getButtonInfo()[0]);
        mainViewPane.setCenter(view);
    }

    private void createBreadcrumb(ActionEvent event) {
        final Node eventSource = (Node) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())){
            hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(event));
        }
    }

    private void createBreadcrumb(BreadcrumbObject bcObj) {
        hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(bcObj));
    }

    private void createMenuBreadcrumb(ActionEvent event) {
        MenuItem eventSource = (MenuItem) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())) {
            hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.addMenu(event));
        }
    }

    // called by left and top navbars
    private void clearBreadCrumbs() {
        hBoxBreadcrumb.getChildren().clear();
        breadcrumbBar.clear();
    }

    @FXML
    private void backToLoginScene(){
        app.switchScene();
    }

    public App getApp() {
        return this.app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
