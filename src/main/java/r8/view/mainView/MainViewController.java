package r8.view.mainView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

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
import java.util.Objects;

/**
 * Controller for applications Main View. All subviews are loaded within this parent view, giving the
 * software it's single page app feel.
 * @author Aarni Pesonen
 */
public class MainViewController implements IViewController {

    private App app;

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

    private String currentView;

    /**
     * Initializes the application main view
     */
    public void initialize() {

        initSubview(initialView);
        visibility.toggleOff(hBoxQuicknav);
    }

    /**
     * Retrieves requested subview information with {@link NavigationHandler}
     * and sets the returned view in display on the mainViewPane where active subview loaded and displayed.
     * @param event triggering the navigation
     */
    @FXML
    public void handleNavigation(ActionEvent event) {
        mainViewPane.setCenter(nav.handleNavigation(event));
        clearBreadCrumbs();
        createBreadcrumb(event);
        currentView = breadcrumbBar.getCurrentView();
    }

    /**
     * Handles MenuItem navigation based on the triggering ActionEvent userData value
     * @param event
     */
    @FXML
    private void handleMenuItemNavigation(ActionEvent event) {
        mainViewPane.setCenter(nav.handleMenuItemNavigation(event));
        clearBreadCrumbs();
        createMenuBreadcrumb(event);
    }

    /**
     * Used to handle subview navigation. Called by subview controllers
     * @param event triggering the subview navigation event
     */
    public void handleSubviewNavigation(ActionEvent event) {
        mainViewPane.setCenter(nav.handleNavigation(event));
        createBreadcrumb(event);
    }

    /**
     * Toggles the top quick navigation bar on/off
     * *feature is not yet fully implemented*
     */
    @FXML
    private void toggleQuicknav() {
        visibility.toggleVisibility(hBoxQuicknav);
    }

    /**
     * Loads the initial subview based on {@link BreadcrumbObject} data
     * @param bcObj containing navigation data
     */
    private void initSubview(BreadcrumbObject bcObj) {
        createBreadcrumb(initialView);
        GetView viewLoader = new GetView();
        view = viewLoader.getView(bcObj.getButtonInfo()[0]);
        mainViewPane.setCenter(view);
    }

    /**
     * Creates a new {@link BreadcrumbObject} in {@link BreadcrumbBar}
     * based on triggerin ActionEvent data
     * @param event
     */
    private void createBreadcrumb(ActionEvent event) {
        final Node eventSource = (Node) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())){
            //hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(event));
            System.out.println("Breadcrumb feature is currently disabled.");
        }
    }

    /**
     * Adds a new {@link BreadcrumbObject} to {@link BreadcrumbBar}
     * @param bcObj to be added to list of breadcrumbs
     */
    private void createBreadcrumb(BreadcrumbObject bcObj) {
        hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.add(bcObj));
    }

    /**+
     * Creates a new {@link BreadcrumbObject} based on triggering MenuItem event data
     * @param event created by triggering button press
     */
    private void createMenuBreadcrumb(ActionEvent event) {
        MenuItem eventSource = (MenuItem) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), breadcrumbBar.getCurrentView())) {
            hBoxBreadcrumb.getChildren().addAll(breadcrumbBar.addMenu(event));
        }
    }

    /**
     * Removes all {@link BreadcrumbObject} from {@link BreadcrumbBar}
     */
    private void clearBreadCrumbs() {
        hBoxBreadcrumb.getChildren().clear();
        breadcrumbBar.clear();
    }

    /**
     * Switches from parent main view to parent login view.
     * Used when user logs out of the application
     */
    @FXML
    private void backToLoginScene(){
        app.switchScene();
    }

    /**
     * Returns reference to the main application
     * @return main application reference
     */
    public App getApp() {
        return this.app;
    }

    /**
     * Sets the reference to main application
     * @param app main application reference
     */
    public void setApp(App app) {
        this.app = app;
    }
}
