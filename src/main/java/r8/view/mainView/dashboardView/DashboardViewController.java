package r8.view.mainView.dashboardView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Event;
import r8.model.activeTracker.ActiveTracker;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.util.UIElementVisibility;
import r8.util.lang.ResourceHandler;
import r8.view.IViewController;
import java.io.IOException;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Controller for applications main view - dashboard subview.
 * @author Aarni Pesonen
 */

public class DashboardViewController {

    @FXML
    Label labelUserName;
    @FXML
    Label labelWelcome;
    @FXML
    private Button btnCustomizeView;
    @FXML
    private Button btnToggleTracking;
    @FXML
    private Button activeTrackingTooltip;
    @FXML
    private Button timeManagementTooltip;
    @FXML
    private ComboBox<Task> comboBoxActiveTrackingTasks = new ComboBox<>();
    @FXML
    private TableColumn<Event, String> tableColDate;
    @FXML
    private TableColumn<Event, String> tableColProject;
    @FXML
    private TableColumn<Event, String> tableColTask;
    @FXML
    private TableColumn<Event, String> tableColTaskType;
    @FXML
    private TableView<Event> tableViewEvents;

    ResourceBundle rb = ResourceHandler.getInstance().getBundle();
    UIElementVisibility visibility = new UIElementVisibility();
    IControllerMain controller = new Controller();
    ActiveTracker activeTracker = ActiveTracker.getInstance();
    Account account = AppState.getInstance().getAccount();
    Set<Event> userEvents = new HashSet<>();
    Set<Task> userTasks = new HashSet<>();
    private App app;

    /**
     * Includes operations necessary for loading the class first time
     *
     */
    @FXML
    private void initialize() {
        labelUserName.setText(account.getFirstName() + " " +account.getLastName());
        Button[] tooltips = {activeTrackingTooltip, timeManagementTooltip};
        visibility.setTooltipVisibility(tooltips);
        getEvents();
        getTasks();
        initEventsTable();
        String btnText = activeTracker.isActive() ? rb.getString("stopTracking") : rb.getString("startTracking");
        btnToggleTracking.setText(btnText);
    }

    /**
     * Retrieves user work events from db and displays them in the user events table
     * threaded operation
     */
    private void getEvents() {
        Thread thread = new Thread(() -> {

            userEvents.addAll(controller.getEventDAO().getByAccount(account));

            Platform.runLater(this::updateEventsTable);
        });
        thread.start();
    }

    /**
     * Retrieves user tasks from db and populates active tracker combobox with these events
     */
    private void getTasks() {
        Thread thread = new Thread(() -> {

            userTasks.addAll(controller.getTaskDAO().getByAccount(account));
            System.out.println("userTasks: " + userTasks.toString());
            Platform.runLater(() -> {

                comboBoxActiveTrackingTasks.getItems().addAll(controller.getTaskDAO().getByAccount(account));
            });
        });
        thread.start();
    }

    /**
     * Handles navigation calls made from dashboard view
     * @param event triggering the navigation event
     */
    @FXML
    private void navigate(ActionEvent event) {
        IViewController viewController = controller.getActiveViewController();
        viewController.handleNavigation(event);
    }

    /**
     * Updates user work events table with latest data
     */
    private void updateEventsTable() {
        tableViewEvents.getItems().clear();
        tableViewEvents.getItems().addAll(userEvents);
        sortTableView();
    }

    /**
     * Updates combobox containing users tasks
     */
    private void updateTasksComboBox() {
        comboBoxActiveTrackingTasks.getItems().addAll(userTasks);
    }

    /**
     * Initializes user work events table
     */
    private void initEventsTable() {
        tableColDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate(String.valueOf(rb.getLocale()))));
        tableColTask.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        tableColTaskType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getTaskType().getName()));
        tableColProject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getName()));
    }

    /**
     * Toggles ActiveTracking on/off and calls the appropriate method from tracker singleton
     */
    @FXML
    private void toggleTracking() {
        if (!activeTracker.isActive()) {
           setActiveTrackerButtonText();
           Task task = comboBoxActiveTrackingTasks.getSelectionModel().getSelectedItem();
            Event event = new Event("Event created by Active Tracker", account, task, task.getProject());
            activeTracker.startTracking(event);
        } else {
           setActiveTrackerButtonText();
            Event event = activeTracker.stopTracking();
            Thread thread = new Thread(() -> {
                controller.getEventDAO().persist(event);
                userEvents.add(event);
                Platform.runLater(this::updateEventsTable);
            });
            thread.start();
        }
    }

    /**
     * Changes ActiveTracker button text according to tracker status
     */
    private void setActiveTrackerButtonText() {
        if (!activeTracker.isActive())
            btnToggleTracking.setText(rb.getString("stopTracking"));
        else
            btnToggleTracking.setText(rb.getString("startTracking"));
    }

    /**
     * Arranges items in user work events tableview based on events dates
     * descending order
     */
    private void sortTableView() {
        tableColDate.setSortType(TableColumn.SortType.DESCENDING);
        tableViewEvents.getSortOrder().add(tableColDate);
        tableViewEvents.sort();
    }
}


