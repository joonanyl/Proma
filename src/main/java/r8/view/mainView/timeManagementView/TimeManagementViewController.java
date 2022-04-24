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
    private VBox vBoxSprintSelect = new VBox();

    @FXML
    private VBox vBoxToggle;

    @FXML
    private ComboBox<Project> comboBoxEventProject = new ComboBox<>();

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
    private int sprintIndex;
    private String activeFilter;

    @FXML
    private void initialize() {

        initDatePickers();
        initTableView();
        updateComboBoxes();

        tableViewListener();
        filterProjectListener();
        filterAll();

        //projectsList.addAll(controller.getProjectDAO().getByAccount(account));
        /*System.out.println(projectsList);
        Thread thread = new Thread(() -> {
            projectsSet.addAll(controller.getProjectDAO().getByAccount(account));
            eventsSet.addAll(controller.getEventDAO().getByAccount(account));
            System.out.println("events from db" + eventsSet);

            Platform.runLater(() -> {
                projectsList.addAll(projectsSet);
                eventsList.addAll(eventsSet);
                for (Event event : eventsList) {
                    System.out.println("Adding to table view: " +event);
                    tableView.getItems().add(event);
                }
                updateComboBoxes();
                sortTableView();
                System.out.println("TableView contents " +tableView.getItems().toString());
            });
        });
        thread.start();*/
    }

    public void saveEvent() {
        Event event = new Event(textNewEntryDescription.getText(), datePickerCalendarEntry.getValue(),
                Float.parseFloat(textHoursWorked.getText()), account, comboBoxEventTask.getSelectionModel().getSelectedItem(),
                comboBoxEventTask.getSelectionModel().getSelectedItem().getProject());

        /*if  (comboBoxEventTask.getSelectionModel().getSelectedItem().getSprints() != null)
            event.setSprint(comboBoxEventTask.getSelectionModel().getSelectedItem().getActiveSprint());*/

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

    /**
     * Clears work event data edit fields
     */
    public void clearFields() {
        datePickerEvent.setValue(null);
        comboBoxEventName.valueProperty().set(null);
        comboBoxEventType.valueProperty().set(null);
        textFieldHoursWorked.clear();
        textFieldDescription.clear();
    }

    /**
     * Clears "Add work event" input fields
     */
    private void clearEventFields() {
        textNewEventName.clear();
        textNewEventDescription.clear();
    }

    /**
     * Updates work event info to database
     */
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

    // TODO refactor Updates view comboboxes with all users tasks and also retrieves all available task types

    /**
     * Updates UI comboboxes with latest database entries
     */
    private void updateComboBoxes() {

        for (Project project : projectsSet) {
            tasksSet.addAll(project.getTasks());
        }

        comboBoxEventName.getItems().clear();
        comboBoxEventProject.getItems().clear();
        comboBoxEventTask.getItems().clear();
        comboBoxEventType.getItems().clear();

        Thread thread = new Thread(() -> {

            projectsSet.addAll(controller.getProjectDAO().getByAccount(account));
            comboBoxEventTask.getItems().addAll(controller.getTaskDAO().getByAccount(account));
            comboBoxEventType.getItems().addAll(controller.getAllTaskTypes());

            Platform.runLater(() -> {
                comboBoxEventProject.getItems().addAll(projectsSet);
                projectsList.addAll(projectsSet);
                comboBoxEventName.getItems().addAll(tasksSet);
                System.out.println("Sprints " +sprintsSet.toString());
            });
        });
        thread.start();

        for (Project p : projectsList) {
            System.out.println(p.getSprints().toString());
        }

    }

    /**
     * TableView listener clears work event data editing fields if new item is selected from tableView
     */
    private void tableViewListener(){
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                clearFields();
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

    private void sortTableView() {
        tableColDate.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(tableColDate);
        tableView.sort();
    }

    private void updateData() {
        Thread thread = new Thread(() -> {
            projectsSet.clear();
            eventsSet.clear();
            //projectsSet.addAll(controller.loadProjects(account));
            eventsSet.addAll(controller.getEventDAO().getByAccount(account));
            });

        thread.start();
        Platform.runLater(() -> {
            System.out.println("Project + Event data updated.");
        });
    }

    /**
     * Fetches all user account related events from database
     * and adds the retuned event to tableView
     */
    private void displayAllEvents() {
        eventsSet.clear();
        Thread thread = new Thread(() -> {

            eventsSet.addAll(controller.getEventDAO().getByAccount(account));

            Platform.runLater(() -> {
                tableView.getItems().clear();
                tableView.getItems().addAll(eventsSet);
                sortTableView();
            });
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

            Platform.runLater(() -> {
                textFieldProjectDisplay.setText(projectsList.get(projectIndex).getName());
                tableView.getItems().clear();
                tableView.getItems().addAll(eventsSet);
                sortTableView();
            });
        });
        thread.start();
    }

    /**
     *
     */
    public void filterAll() {
        visibility.toggleOff(hBoxProjectSprint);
        displayAllEvents();
        activeFilter = "all";
    }

    /**
     * filter project specific user work events
     */
    public void filterProjects() {
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOff(vBoxSprintSelect);
        if (!activeFilter.equals("projects")) {
            displayProjectEvents();
            activeFilter = "projects";
        }
    }

    /**
     * filter sprint specific user work events
     */
    public void filterSprints() {
        visibility.toggleOn(hBoxProjectSprint);
        visibility.toggleOn(vBoxSprintSelect);
        activeFilter = "sprints";
    }

    /**
     * When project based event filtering is selected
     * used to browse through users project list
     */
    @FXML
    void nextProject() {
        projectIndex++;

        if (projectIndex > projectsList.size()-1)
            projectIndex = 0;

        sprintsList.addAll(projectsList.get(projectIndex).getSprints());
        displayProjectEvents();
    }

    /**
     * When project based event filtering is selected
     * used to browse through users project list
     */
    @FXML
    void previousProject() {
        projectIndex--;

        if (projectIndex < 0)
            projectIndex = (projectsList.size()-1);

        sprintsList.addAll(projectsList.get(projectIndex).getSprints());
        displayProjectEvents();
    }

    /**
     * When sprint based event filtering is selected
     * used to browse through users project specific sprint lists
     */
    @FXML
    void nextSprint() {
        sprintIndex++;

        if (sprintIndex > sprintsList.size())
            sprintIndex = 1;

        if(sprintsList != null)
        textFieldSprintDisplay.setText(sprintsList.get(sprintIndex-1).getName());
    }

    /**
     * When sprint based event filtering is selected
     * used to browse through users project specific sprint lists
     */
    @FXML
    void previousSprint() {
        sprintIndex--;
        if (sprintIndex < 1)
            sprintIndex = sprintsList.size();

        if(sprintsList != null)
        textFieldSprintDisplay.setText(sprintsList.get(sprintIndex-1).getName());
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
     * Initializes Table View for displaying user work events
     */
    private void initTableView() {
        tableColDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        tableColDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        tableColTask.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        tableColEventType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getTaskType().getName()));
        tableColHoursWorked.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoursString()));
        tableColDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    /**
     * Used to handle subview navigation
     * @param event button event triggering handleNavigation method
     * @throws IOException thrown if there is a problem with botton related ActionEvent
     */
    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
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
