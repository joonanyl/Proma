package r8.view.mainView.timeManagementView.trackerView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.model.task.TaskType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class TrackerViewAllController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddProject;

    @FXML
    private Button btnAddSprint;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSubProject;

    @FXML
    private Button btnSubSprint;

    @FXML
    private ComboBox<Task> comboBoxEventName;

    @FXML
    private ComboBox<TaskType> comboBoxEventType;

    @FXML
    private DatePicker datePickerEvent;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableColumn<Event, String> tableColDate;

    @FXML
    private TableColumn<Event, String> tableColTask;

    @FXML
    private TableColumn<Event, String> tableColEventType;

    @FXML
    private TableColumn<Event, String> tableColHoursWorked;

    @FXML
    private TableColumn<Event, Integer> tableColProject;

    @FXML
    private TableColumn<Event, String> tableColDescription;

    @FXML
    private TableView<Event> tableViewAll = new TableView<>();

    @FXML
    private TextField textFieldDayDisplay;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldHoursWorked;

    @FXML
    private TextField textFieldMonthDisplay;

    private IControllerMain controller = new Controller();
    private Account account = AppState.getInstance().getAccount();
    private Set<Project> projects = new HashSet<>();
    private Set<Sprint> sprints;
    private Set<Task> tasks = new HashSet<>();
    private Set<Team> teams;
    private Set<Event> events = new HashSet<>();
    ObservableList<Event> eventsToDisplay = FXCollections.observableArrayList();
    private int indexToRemove;

    public void initialize() {

        initDatePicker();
        tableViewAll.getColumns().clear();

        projects.addAll(controller.getProjectDAO().getByAccount(account));
        events.addAll(controller.getEventDAO().getByAccount(account));

        eventsToDisplay.addAll(events);
        addToTableView();
        updateComboBoxes();
        sortTableView();
        tableViewListener();
    }

    private void initDatePicker() {
        datePickerEvent.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
    }

    private void addToTableView() {

        tableColDate = new TableColumn<>("Date");
        tableColDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDate().toString()));

        tableColTask = new TableColumn<>("Task");
        tableColTask.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTask().getName()));

        tableColEventType = new TableColumn<>("Event Type");
        tableColEventType.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTask().getTaskType().getName()));

        tableColHoursWorked = new TableColumn<>("Hours Worked");
        tableColHoursWorked.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getHoursString()));

        tableColDescription = new TableColumn<>("Description");
        tableColDescription.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));

        tableViewAll.getColumns().addAll(tableColDate, tableColDescription, tableColTask, tableColEventType, tableColHoursWorked);

        for (Event event : eventsToDisplay) {
            System.out.println("Adding to table view: " +event);
            tableViewAll.getItems().add(event);
        }
    }

    public void saveEvent() {
        Event event = new Event();
        event.setAccount(account);
        event.setDate(datePickerEvent.getValue());
        event.setTask(comboBoxEventName.getSelectionModel().getSelectedItem());
        event.getTask().setTaskType(comboBoxEventType.getSelectionModel().getSelectedItem());
        event.setHours(Float.parseFloat(textFieldHoursWorked.getText()));
        event.setDescription(textFieldDescription.getText());

        controller.getEventDAO().persist(event);
        tableViewAll.getItems().add(event);
        clearFields();
        updateComboBoxes();
        sortTableView();
        updateData();
    }

    public void updateEvent() {
        Event event = new Event();
        event.setDate(datePickerEvent.getValue());
        event.setHours(Float.parseFloat(textFieldHoursWorked.getText()));
        event.setDescription(textFieldDescription.getText());
        event.setTask(comboBoxEventName.getValue());
        event.getTask().setTaskType(comboBoxEventType.getValue());
        controller.getEventDAO().update(event);
        tableViewAll.getItems().remove(indexToRemove);
        tableViewAll.getItems().add(event);
        sortTableView();
        clearFields();
    }

    public void deleteEvent() {
        Event event = tableViewAll.getSelectionModel().getSelectedItem();
        tableViewAll.getItems().remove(tableViewAll.getSelectionModel().getSelectedItem());
        controller.getEventDAO().remove(event);
        sortTableView();
    }

    public void getSelectedEvent() {
        Event event = tableViewAll.getSelectionModel().getSelectedItem();
        indexToRemove = tableViewAll.getSelectionModel().getSelectedIndex();
        if (event != null) {
            datePickerEvent.setValue(event.getDate());
            textFieldHoursWorked.setText(Float.toString(event.getHours()));
            comboBoxEventName.setValue(event.getTask());
            comboBoxEventType.setValue(event.getTask().getTaskType());
            textFieldDescription.setText(event.getDescription());
        }
    }

    // Updates view comboboxes with all users tasks and also retrieves all available task types
    private void updateComboBoxes() {
        for (Project project : projects) {
            tasks.addAll(project.getTasks());
        }
        comboBoxEventName.getItems().clear();
        comboBoxEventName.getItems().addAll(tasks);
        comboBoxEventType.getItems().clear();
        comboBoxEventType.getItems().addAll(controller.getAllTaskTypes());
    }

    public void clearFields() {
        datePickerEvent.setValue(null);
        comboBoxEventName.valueProperty().set(null);
        comboBoxEventType.valueProperty().set(null);
        textFieldHoursWorked.clear();
        textFieldDescription.clear();
    }

    private void tableViewListener(){
        tableViewAll.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                clearFields();
            }
        });
    }

    private void sortTableView() {
        tableColDate.setSortType(TableColumn.SortType.DESCENDING);
        tableViewAll.getSortOrder().add(tableColDate);
        tableViewAll.sort();
    }

    private void updateData() {
        Platform.runLater(() -> {
            projects.addAll(controller.loadProjects(account));
            events.addAll(controller.getEventDAO().getByAccount(account));
        });
    }
}
