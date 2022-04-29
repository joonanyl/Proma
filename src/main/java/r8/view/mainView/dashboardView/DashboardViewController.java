package r8.view.mainView.dashboardView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Event;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.TextLoader;
import r8.view.IViewController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class DashboardViewController {

    @FXML
    Label labelUserName;
    @FXML
    Label labelWelcome;
    @FXML
    Label labelSystemTimeDisplay;
    @FXML
    private Button btnCustomizeView;
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

    IControllerMain controller = new Controller();

    Account account = AppState.getInstance().getAccount();
    Set<Event> userEvents = new HashSet<>();
    private App app;

    @FXML
    private void initialize() {
        getEvents();
        initEventsTable();
        mockData();
    }

    private void getEvents() {
        Thread thread = new Thread(() -> {

            userEvents.addAll(controller.getEventDAO().getByAccount(account));

            Platform.runLater(this::updateEventsTable);
        });
        thread.start();
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        IViewController viewController = controller.getActiveViewController();
        viewController.handleNavigation(event);
    }

    private void mockData() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        labelUserName.setText(account.getFirstName() +" "+ account.getLastName());
        labelSystemTimeDisplay.setText(TextLoader.getInstance().getResource("dashboardDay") + " " + dtf.format(now));
    }

    private void updateEventsTable() {
        tableViewEvents.getItems().addAll(userEvents);
    }

    private void initEventsTable() {
        tableColDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        tableColTask.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        tableColTaskType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getTaskType().getName()));
        tableColProject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getName()));
    }
}

