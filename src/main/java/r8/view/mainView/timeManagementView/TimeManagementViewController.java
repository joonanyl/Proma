package r8.view.mainView.timeManagementView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.model.task.TaskType;
import r8.model.util.UIElementVisibility;
import r8.view.navigation.NavigationHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Aarni Pesonen
 *
 */
public class TimeManagementViewController {

    @FXML
    private HBox hBoxProjectSprint = new HBox();

    @FXML
    private HBox hBoxSprint;

    @FXML
    private Button btnAddNewEvent;

    @FXML
    private Button btnAddProject2;

    @FXML
    private Button btnAddSprint1;

    @FXML
    private Button btnCreateNewEvent1;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSubProject2;

    @FXML
    private Button btnSubSprint1;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<Task> comboBoxEventName;

    @FXML
    private ComboBox<Task> comboBoxEventTask = new ComboBox<>();

    @FXML
    private ComboBox<TaskType> comboBoxEventType;

    @FXML
    private DatePicker datePickerCalendarEntry;

    @FXML
    private DatePicker datePickerEvent;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label labelUserName;

    @FXML
    private Label labelWelcome;

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
    private TableView<Event> tableView = new TableView<>();

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldHoursWorked;

    @FXML
    private TextField textFieldProjectDisplay;

    @FXML
    private TextField textFieldSprintDisplay;

    @FXML
    private TextField textHoursWorked;

    @FXML
    private TextArea textNewEntryDescription;

    @FXML
    private TextField textNewEventName;

    @FXML
    private TextArea textNewEventDescription;

    @FXML
    private VBox vBoxEntry;

    @FXML
    private VBox vBoxEvent;

    @FXML
    private VBox vBoxProjectSelect;

    @FXML
    private VBox vBoxSprintSelect = new VBox();

    @FXML
    private VBox vBoxToggle;

    @FXML
    private ComboBox<Project> comboBoxEventProject = new ComboBox<>();

    private final UIElementVisibility visibility = new UIElementVisibility();

    private IControllerMain controller = new Controller();
    private Account account = AppState.getInstance().getAccount();
    private Set<Project> projects = new HashSet<>();
    private Set<Sprint> sprints;
    private Set<Task> tasks = new HashSet<>();
    private Set<Team> teams;
    private Set<Event> events = new HashSet<>();
    ObservableList<Event> eventsToDisplay = FXCollections.observableArrayList();
    ObservableList<Project> projectsToDisplay = FXCollections.observableArrayList();
    ObservableList<Sprint> sprintsToDisplay = FXCollections.observableArrayList();
    private int indexToRemove;
    private int projectIndex = 0;
    private int sprintIndex;
    private String activeFilter;

    @FXML
    private void initialize() throws IOException {

        initDatePickers();
        tableView.getColumns().clear();

        projects.addAll(controller.getProjectDAO().getByAccount(account));
        projectsToDisplay.addAll(projects);
        events.addAll(controller.getEventDAO().getByAccount(account));

        eventsToDisplay.addAll(events);
        addToTableView();
        updateComboBoxes();
        sortTableView();
        tableViewListener();
        filterProjectListener();
        visibility.toggleOff(vBoxToggle);
        filterAll();
    }

    private void initDatePickers() {
        DatePicker[] datePickers = {datePickerCalendarEntry, datePickerEvent};
        for (DatePicker dp : datePickers) {
            dp.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
                }
            });
        }
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

        tableView.getColumns().addAll(tableColDate, tableColDescription, tableColTask, tableColEventType, tableColHoursWorked);

        for (Event event : eventsToDisplay) {
            System.out.println("Adding to table view: " +event);
            tableView.getItems().add(event);
        }
    }

    public void saveEvent() {
        Event event = new Event();
        event.setAccount(account);
        event.setDate(datePickerCalendarEntry.getValue());
        event.setTask(comboBoxEventTask.getSelectionModel().getSelectedItem());
        //event.getTask().setTaskType(comboBoxEventType.getSelectionModel().getSelectedItem());
        event.setHours(Float.parseFloat(textHoursWorked.getText()));
        event.setDescription(textNewEntryDescription.getText());

        controller.getEventDAO().persist(event);
        tableView.getItems().add(event);
        clearEntryFields();
        updateComboBoxes();
        sortTableView();
        updateData();
    }

    public void createEvent() {
        controller.getTaskDAO().persist(new Task(textNewEventName.getText(), textNewEventDescription.getText()));
        updateComboBoxes();
        clearEventFields();
    }

    private void clearEntryFields() {
        datePickerCalendarEntry.setValue(null);
        comboBoxEventProject.setValue(null);
        comboBoxEventTask.valueProperty().set(null);
        textHoursWorked.clear();
        textNewEntryDescription.clear();
    }

    private void clearEventFields() {
        textNewEventName.clear();
        textNewEventDescription.clear();
    }

    public void updateEvent() {
        Event event = new Event();
        event.setDate(datePickerEvent.getValue());
        event.setHours(Float.parseFloat(textFieldHoursWorked.getText()));
        event.setDescription(textFieldDescription.getText());
        event.setTask(comboBoxEventName.getValue());
        event.getTask().setTaskType(comboBoxEventType.getValue());
        controller.getEventDAO().update(event);
        tableView.getItems().remove(indexToRemove);
        tableView.getItems().add(event);
        sortTableView();
        clearFields();
    }

    public void deleteEvent() {
        Event event = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        controller.getEventDAO().remove(event);
        sortTableView();
    }

    public void getSelectedEvent() {
        Event event = tableView.getSelectionModel().getSelectedItem();
        indexToRemove = tableView.getSelectionModel().getSelectedIndex();
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

        System.out.println("Get tasks by account: " +controller.getTaskDAO().getByAccount(account));
        System.out.println("Get projects by account: " +controller.getProjectDAO().getByAccount(account));
        for (Project project : projects) {
            tasks.addAll(project.getTasks());
        }
        comboBoxEventProject.getItems().clear();
        comboBoxEventProject.getItems().addAll(projects);
        comboBoxEventTask.getItems().clear();
        comboBoxEventTask.getItems().addAll(controller.getTaskDAO().getByAccount(account));
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
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                clearFields();
            }
        });
    }

    private void filterProjectListener(){
        comboBoxEventProject.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

            if(newValue != null){
                Project project = comboBoxEventProject.getSelectionModel().getSelectedItem();
                comboBoxEventTask.getItems().clear();
                comboBoxEventTask.getItems().addAll(project.getTasks());
            }
        });

    }

    private void sortTableView() {
        tableColDate.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(tableColDate);
        tableView.sort();
    }

    private void updateData() {
        Platform.runLater(() -> {
            projects.addAll(controller.loadProjects(account));
            events.addAll(controller.getEventDAO().getByAccount(account));
        });
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
    }

    public void filterAll() {
        visibility.toggleOff(hBoxProjectSprint);
        activeFilter = "all";
    }

    /**
     * filter project specific user work events
     */
    public void filterProjects() {
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOff(vBoxSprintSelect);
        displayProjectEvents(projectsToDisplay.get(projectIndex).getProjectId());
        System.out.println(textFieldProjectDisplay.getText());
        if (!activeFilter.equals("projects")) {
            projectIndex = 1;
            nextProject();
        }
        activeFilter = "projects";
    }

    /**
     * filter sprint specific user work events
     */
    public void filterSprints() {
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOn(vBoxSprintSelect);
        activeFilter = "sprints";
    }

    @FXML
    void nextProject() {
        projectIndex++;

        if (projectIndex > projects.size()) {
            projectIndex = 1;
        }

        textFieldProjectDisplay.setText(projectsToDisplay.get(projectIndex-1).getName());
        sprintsToDisplay.addAll(projectsToDisplay.get(projectIndex-1).getSprints());
        displayProjectEvents(projectsToDisplay.get(projectIndex-1).getProjectId());
    }

    @FXML
    void previousProject() {
        projectIndex--;

        if (projectIndex < 1)
            projectIndex = projects.size();

        textFieldProjectDisplay.setText(projectsToDisplay.get(projectIndex-1).getName());
        System.out.println("Selected project is " + projectsToDisplay.get(projectIndex-1).getName());
        sprintsToDisplay.addAll(projectsToDisplay.get(projectIndex-1).getSprints());
        displayProjectEvents(projectsToDisplay.get(projectIndex-1).getProjectId());
    }

    @FXML
    void nextSprint(ActionEvent event) {
        sprintIndex++;

        if (sprintIndex > sprintsToDisplay.size())
            sprintIndex = 1;

        if(sprintsToDisplay != null)
        textFieldSprintDisplay.setText(sprintsToDisplay.get(sprintIndex-1).getName());
    }

    @FXML
    void previousSprint(ActionEvent event) {
        sprintIndex--;
        if (sprintIndex < 1)
            sprintIndex = sprintsToDisplay.size();

        if(sprintsToDisplay != null)
        textFieldSprintDisplay.setText(sprintsToDisplay.get(sprintIndex-1).getName());
    }

    //TODO refactor, working with getByProject
    private void displayProjectEvents(int projectId) {
        tasks.clear();
        tasks.addAll(controller.getTaskDAO().getByAccount(account));
        System.out.println("Tasks now contain " + tasks);
        tableView.getItems().clear();
        for (Event event : eventsToDisplay) {
            if (event.getTask().getProject().getProjectId() == projectId)
                tableView.getItems().add(event);
        }
        /*for (Event event : eventsToDisplay) {
            for (Task task : tasks) {
                if (event.getTask().getTaskId() == task.getTaskId())
                    tableView.getItems().add(event);
                    System.out.println("Adding to tableview" +event);
            }
        }*/
        sortTableView();
    }

    @FXML
    public void toggleNewEvent() {
        visibility.toggleVisibility(vBoxToggle);
    }
}
