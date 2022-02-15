package r8.view.mainView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import r8.App;
import r8.view.IController;
import r8.view.navigation.BreadcrumbObject;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainViewController implements IController {

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
    @FXML
    private List<BreadcrumbObject> breadcrumbs;

    @FXML
    private ObservableList<Button> breadcrumbButtons;

    // used to prevent loading current view again
    private String currentView;

    public void initialize() {
        //hBoxBreadcrumb = new HBox();
        System.out.println("initialize() called");
        GetView viewLoader = new GetView();
        view = viewLoader.getView(initialView.getButtonInfo()[0]);
        System.out.println(view);
        mainViewPane.setCenter(view);
        breadcrumbs = new ArrayList<>();
        breadcrumbs.add(initialView);
        createBreadcrumbs();
    }

    //overloaded method can receive String or ActionEvent as parameter
    @FXML
    private void handleNavigation(String viewName) throws IOException {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(viewName);
        mainViewPane.setCenter(view);
    }

    //TODO: refactor, WIP
    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        Button target = (Button) event.getTarget();
        String viewType = target.getText();
        if (!userData.equals(currentView)) {
            System.out.println("Clicked " + userData);
            // No need to use NavigationHandler
            mainViewPane.setCenter(nav.handleNavigation(event));
            currentView = userData;
            breadcrumbs.add(new BreadcrumbObject(userData, viewType));
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
            createBreadcrumbs();
        }
    }
    //TODO: refactor, WIP

    @FXML
    public void handleLeftBarNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        System.out.println(event);
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        Button target = (Button) event.getTarget();
        String viewType = (String) target.getText();
        if (!userData.equals(currentView)) {
            hBoxBreadcrumb.getChildren().clear();
            breadcrumbs.clear();
            breadcrumbButtons.clear();
            System.out.println("Clicked " + userData);
            mainViewPane.setCenter(nav.handleNavigation(event));
            currentView = userData;
            BreadcrumbObject test = new BreadcrumbObject(userData, viewType);
            breadcrumbs.add(test);
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
            createBreadcrumbs();
        }
    }

    @FXML
    private void handleMenuItemNavigation(ActionEvent event) throws IOException {
        MenuItem eventsource = (MenuItem) event.getSource();
        String userData = (String) eventsource.getUserData();
        handleNavigation(userData);
    }

    @FXML
    private void backToLoginScene(){
        app.switchToLoginScene();
    }

    public void initButtonIcons() {
        //TODO import icons
    }

    public void createBreadcrumbs() {
        breadcrumbButtons = FXCollections.observableArrayList();

        for(BreadcrumbObject bcObj : breadcrumbs){
            String[] buttonInfo = bcObj.getButtonInfo();
            Button bcButton = new Button(buttonInfo[1]);
            bcButton.setUserData(buttonInfo[0]);
            System.out.println("hbox buttons in list: " + breadcrumbButtons);
            breadcrumbButtons.add(bcButton);
            System.out.println("hbox buttons in list after adding: " + breadcrumbButtons);
        }
        hBoxBreadcrumb.getChildren().addAll(breadcrumbButtons);
    }

    public App getApp() {
        return this.app;
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }
}
