package r8.view.mainView.timeManagementView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.model.task.TaskType;
import r8.util.ExportUtil;
import r8.util.UIElementVisibility;
import r8.util.lang.ResourceHandler;
import r8.view.navigation.NavigationHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
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
    private Label labelUserName;
    @FXML
    private TableColumn<Event, String> tableColDate;
    @FXML
    private TableColumn<Event, String> tableColTask;
    @FXML
    private TableColumn<Event, String> tableColEventType;
    @FXML
    private TableColumn<Event, String> tableColHoursWorked;
    @FXML
    private TableColumn<Event, String> tableColProject;
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
    private VBox vBoxSprintSelect = new VBox();
    @FXML
    private VBox vBoxToggle;
    @FXML
    private ComboBox<Project> comboBoxEventProject = new ComboBox<>();

    ResourceBundle rb = ResourceHandler.getInstance().getBundle();
    private final UIElementVisibility visibility = new UIElementVisibility();
    private final IControllerMain controller = new Controller();
    private final Account account = AppState.getInstance().getAccount();
    private Set<Project> projectsSet = new HashSet<>();
    private Set<Task> tasksSet = new HashSet<>();
    private Set<Event> eventsSet = new HashSet<>();
    private Set<Sprint> sprintsSet  =new HashSet<>();
    private Set<Team> teamsSet;
    ObservableList<Event> eventsList = FXCollections.observableArrayList();
    ObservableList<Project> projectsList = FXCollections.observableArrayList();
    ObservableList<Sprint> sprintsList = FXCollections.observableArrayList();
    private int indexToRemove;
    private int projectIndex = 0;
    private int sprintIndex = 0;
    private String activeFilter;

    @FXML
    private void initialize() {

        labelUserName.setText(account.getFirstName() + " " + account.getLastName() + " ");

        initDatePickers();
        initTableView();
        updateComboBoxes();

        tableViewListener();
        filterProjectListener();

        filterAll();
    }

    /**
     * Retrieves user input values and attempts to save it to database
     */
    public void addCalendarEntry() {
        Event event = new Event(textNewEntryDescription.getText(), datePickerCalendarEntry.getValue(),
                Float.parseFloat(textHoursWorked.getText()), account, comboBoxEventTask.getSelectionModel().getSelectedItem(),
                comboBoxEventTask.getSelectionModel().getSelectedItem().getProject());

        System.out.println(event);

        if  (comboBoxEventTask.getSelectionModel().getSelectedItem().getSprints() != null) {
            event.setSprint(comboBoxEventTask.getSelectionModel().getSelectedItem().getActiveSprint());
        }

        controller.getEventDAO().persist(event);
        tableView.getItems().add(event);
        clearNewEntryFields();
        updateComboBoxes();
        sortTableView();
        updateData();
    }

    /**
     * Creates a new generic task to database
     * Used to log non-task related work events to database
     */
    public void createEvent() {
        controller.getTaskDAO().persist(new Task(textNewEventName.getText(), textNewEventDescription.getText()));
        updateComboBoxes();
        clearNewEventFields();
    }

    /**
     * Clears user work event input fields
     */
    private void clearNewEntryFields() {
        datePickerCalendarEntry.setValue(null);
        comboBoxEventProject.setValue(null);
        comboBoxEventTask.valueProperty().set(null);
        textHoursWorked.clear();
        textNewEntryDescription.clear();
    }

    /**
     * Clears work event data edit fields
     */
    private void clearEditFields() {
        datePickerEvent.setValue(null);
        comboBoxEventName.valueProperty().set(null);
        comboBoxEventType.valueProperty().set(null);
        textFieldHoursWorked.clear();
        textFieldDescription.clear();
    }

    /**
     * Clears "Add work event" input fields
     */
    private void clearNewEventFields() {
        textNewEventName.clear();
        textNewEventDescription.clear();
    }

    /**
     * Updates work event info to database
     */
    public void updateEvent() {
        Event event = tableView.getSelectionModel().getSelectedItem();
        event.setDate(datePickerEvent.getValue());
        event.setHours(Float.parseFloat(textFieldHoursWorked.getText()));
        event.setDescription(textFieldDescription.getText());
        event.setProject(comboBoxEventName.getValue().getProject());
        event.setTask(comboBoxEventName.getValue());
        event.getTask().setTaskType(comboBoxEventType.getValue());
        controller.getEventDAO().update(event);
        int indexToRemove = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(indexToRemove);

        Platform.runLater(() -> {
            tableView.getItems().add(event);
            sortTableView();
            clearEditFields();
        });
    }

    /**
     * Deletes work event info from database and UI tableView
     */
    public void deleteEvent() {
        Event event = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        controller.getEventDAO().remove(event);
        sortTableView();
    }

    /**
     * Sets work event details to UI fields for editing
     */
    @FXML
    private void getSelectedEvent() {
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

    // TODO refactor Updates view comboboxes with all users tasks and also retrieve all available task types

    /**
     * Updates UI comboboxes with latest database entries
     */
    private void updateComboBoxes() {

        comboBoxEventName.getItems().clear();
        comboBoxEventProject.getItems().clear();
        comboBoxEventTask.getItems().clear();
        comboBoxEventType.getItems().clear();

        projectsSet.clear();
        sprintsSet.clear();

        for (Project project : projectsSet) {
            tasksSet.addAll(project.getTasks());
        }

        for (Project project : projectsSet) {
            sprintsSet.addAll(project.getSprints());
        }

        Thread thread = new Thread(() -> {

            projectsSet.addAll(controller.getProjectDAO().getByAccount(account));

            Platform.runLater(() -> {
                // Do in a single method
                comboBoxEventProject.getItems().addAll(projectsSet);
                //comboBoxEventTask.getItems().addAll(controller.getTaskDAO().getByAccount(account));
                //testing
                comboBoxEventTask.getItems().addAll(controller.getTaskDAO().getAll());
                comboBoxEventType.getItems().addAll(controller.getAllTaskTypes());
                comboBoxEventName.getItems().addAll(tasksSet);
                //projectsList.addAll(projectsSet);
                System.out.println("Sprints not yet loaded at start" +sprintsSet.toString());
            });
        });
        thread.start();
    }

    /**
     * Arranges table view in descending order based on date column value
     */
    private void sortTableView() {
        tableColDate.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(tableColDate);
        tableView.sort();
    }

    private void clearTableView() {
        tableView.getItems().clear();
    }

    /**
     * Displays eventSet items in tableView
     */
    private void displayEvents() {
        tableView.getItems().clear();
        tableView.getItems().addAll(eventsSet);
        System.out.println("displayEvents event set contains " +eventsSet.toString());
        sortTableView();
    }

    private void getEventsBySprint() {
        eventsSet.addAll(controller.getEventDAO().getByAccountAndSprint(account,sprintsList.get(sprintIndex)));
    }

    private void getProjectsByAccount() {
        projectsList.clear();
        projectsList.addAll(controller.getProjectDAO().getByAccount(account));
    }

    /**
     * Updates project and event set data
     */
    private void updateData() {
        Thread thread = new Thread(() -> {
            projectsSet.clear();
            eventsSet.clear();
            //projectsSet.addAll(controller.getProjectDAO().getByAccount(account));
            eventsSet.addAll(controller.getEventDAO().getByAccount(account));
            });

        thread.start();
        Platform.runLater(() -> System.out.println("Project + Event data updated."));
    }

    /**
     * Fetches all user account related events from database
     * and adds the retuned event to tableView
     */
    private void displayAllEvents() {
        clearTableView();
        eventsSet.clear();
        Thread thread = new Thread(() -> {

            eventsSet.addAll(controller.getEventDAO().getByAccount(account));

            Platform.runLater(this::displayEvents);
        });
        thread.start();
    }

    /**
     * Fetches user account events related to selected project from database
     * and adds the returned events to tableView
     */
    private void displayProjectEvents() {
        eventsSet.clear();
        Thread thread = new Thread(() -> {

            eventsSet.addAll(controller.getEventDAO().getByAccountAndProject(account, projectsList.get(projectIndex)));

            Platform.runLater(this::displayEvents);
        });
        thread.start();
    }

    /**
     * Fetches user account events filtered by chosen project and sprint
     */
    private void displaySprintEvents() {
        eventsSet.clear();
        sprintsList.clear();
        Thread thread = new Thread(() -> {

            if (projectsList.get(projectIndex).getSprints() != null)
            sprintsList.addAll(projectsList.get(projectIndex).getSprints());

            if (sprintsList.size() == 0) {
                textFieldSprintDisplay.setText("No sprints under selected project");
            }
            else {
                textFieldSprintDisplay.setText(sprintsList.get(sprintIndex).getName());
                getEventsBySprint();

                Platform.runLater(this::displayEvents);
            }
        });
        thread.start();
    }

    /**
     * Filter events to be displayed
     * Displays all users events
     */
    public void filterAll() {
        clearTableView();
        visibility.toggleOff(hBoxProjectSprint);
        displayAllEvents();
        activeFilter = "all";
    }

    /**
     * Filter project specific user work events
     */
    public void filterProjects() {
        clearTableView();
        eventsSet.clear();
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOff(vBoxSprintSelect);
        if (!activeFilter.equals("projects")) {
            getProjectsByAccount();
            textFieldProjectDisplay.setText(projectsList.get(projectIndex).getName());
            displayProjectEvents();
            activeFilter = "projects";
        }
    }

    /**
     * Filter sprint specific user work events
     */
    public void filterSprints() {
        clearTableView();
        eventsSet.clear();
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOn(vBoxSprintSelect);
        if (!activeFilter.equals("sprints")) {
            getProjectsByAccount();
            textFieldProjectDisplay.setText(projectsList.get(projectIndex).getName());
            displaySprintEvents();
            activeFilter = "sprints";
        }
    }

    /**
     * When project based event filtering is selected
     * Used to browse through users project list
     */
    @FXML
    void nextProject() {
        clearTableView();
        sprintsList.clear();
        projectIndex++;
        sprintIndex = 0;


        if (projectIndex > projectsList.size()-1) {
            projectIndex = 0;
        }
        textFieldProjectDisplay.setText(projectsList.get(projectIndex).getName());


        if (activeFilter.equals("sprints")){
            sprintsList.clear();
            sprintsList.addAll(projectsList.get(projectIndex).getSprints());
        }
        if (sprintsList.size() > 0)
            textFieldSprintDisplay.setText(sprintsList.get(sprintIndex).getName());

        if (!activeFilter.equals("sprints"))
            displayProjectEvents();
        else
            displaySprintEvents();
    }

    /**
     * When project based event filtering is selected
     * used to browse through users project list
     */
    @FXML
    void previousProject() {
        clearTableView();
        sprintsList.clear();
        projectIndex--;
        sprintIndex = 0;


        if (projectIndex < 0) {
            projectIndex = (projectsList.size()-1);
        }

        textFieldProjectDisplay.setText(projectsList.get(projectIndex).getName());

        if (activeFilter.equals("sprints")){
            sprintsList.clear();
            sprintsList.addAll(projectsList.get(projectIndex).getSprints());
        }
        if (sprintsList.size() > 0)
            textFieldSprintDisplay.setText(sprintsList.get(sprintIndex).getName());

        if (!activeFilter.equals("sprints"))
            displayProjectEvents();
        else displaySprintEvents();
    }

    /**
     * When sprint based event filtering is selected
     * used to browse through users project specific sprint lists
     */
    @FXML
    void nextSprint() {
        clearTableView();
        if(sprintsList.size() > 0){
            sprintIndex++;
            System.out.println("sprintSet size = " + sprintsList.size());
            System.out.println("Sprint index" +sprintIndex);

            if (sprintIndex > sprintsList.size()-1)
                sprintIndex = 0;

            if(sprintsList.size() > 0)
                textFieldSprintDisplay.setText(sprintsList.get(sprintIndex).getName());

            displaySprintEvents();
        }
    }

    /**
     * When sprint based event filtering is selected
     * used to browse through users project specific sprint lists
     */
    @FXML
    void previousSprint() {
        clearTableView();
        if (sprintsList.size() > 0) {
            sprintIndex--;

            System.out.println("sprintSet size = " + sprintsList.size());
            System.out.println("Sprint index" +sprintIndex);
            if (sprintIndex < 0)
                sprintIndex = (sprintsList.size()-1);

            if(sprintsList.size() > 0)
                textFieldSprintDisplay.setText(sprintsList.get(sprintIndex).getName());

            displaySprintEvents();
        }
    }

    /**
     * TableView listener clears work event data editing fields if new item is selected from tableView
     */
    private void tableViewListener(){
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                clearEditFields();
            }
        });
    }

    /**
     * Populates "Add new event" task combobox with task linked to selected project
     */
    private void filterProjectListener(){
        comboBoxEventProject.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

            if(newValue != null){
                Project project = comboBoxEventProject.getSelectionModel().getSelectedItem();
                comboBoxEventTask.getItems().clear();
                comboBoxEventTask.getItems().addAll(project.getTasks());
            }
        });
    }

    /**
     * Initializes Date Pickers used in new work event creation
     * and when editing existing work event
     */
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

    /**
     * Initializes Table View displaying user work events
     */
    private void initTableView() {
        tableColDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedDate(String.valueOf(rb.getLocale()))));
        tableColTask.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        tableColEventType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getTaskType().getName()));
        tableColHoursWorked.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoursString()));
        tableColProject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getName()));
        tableColDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    /**
     * Visibility toggle for New Event Creation UI panel
     */
    @FXML
    public void toggleNewEvent() {
        visibility.toggleVisibility(vBoxToggle);
    }

    @FXML
    public void exportExcel() {
        ExportUtil exp = new ExportUtil();
        exp.exportExcel(tableView);
    }
}
