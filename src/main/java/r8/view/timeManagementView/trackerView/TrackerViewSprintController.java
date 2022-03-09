package r8.view.timeManagementView.trackerView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class TrackerViewSprintController {

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
    private ComboBox<?> comboBoxEventName;

    @FXML
    private ComboBox<?> comboBoxEventType;

    @FXML
    private DatePicker datePickerEvent;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableColumn<?, ?> tableColDate;

    @FXML
    private TableColumn<?, ?> tableColDescription;

    @FXML
    private TableColumn<?, ?> tableColEventName;

    @FXML
    private TableColumn<?, ?> tableColEventType;

    @FXML
    private TableColumn<?, ?> tableColHoursWorked;

    @FXML
    private TableColumn<?, ?> tableColRemaining;

    @FXML
    private TableView<?> tableViewWeek;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldHoursWorked;

    @FXML
    private TextField textFieldProjectDisplay;

    @FXML
    private TextField textFieldSprintDisplay;

    private String[] mockProjects;
    private String[] mockSprints;
    private int projectIndex = 1;
    private int sprintIndex = 1;

    public void initialize() {
        mockData();
        initDatePicker();
        textFieldProjectDisplay.setText(mockProjects[projectIndex-1]);
        textFieldSprintDisplay.setText(mockSprints[sprintIndex-1]);
    }

    @FXML
    void nextSprint(ActionEvent event) {
        sprintIndex++;

        if (sprintIndex > mockSprints.length)
            sprintIndex = 1;

        textFieldSprintDisplay.setText(mockSprints[sprintIndex-1]);
    }

    @FXML
    void previousSprint(ActionEvent event) {
        sprintIndex--;
        if (sprintIndex < 1)
            sprintIndex = mockSprints.length;

        textFieldSprintDisplay.setText(mockSprints[sprintIndex-1]);
    }

    @FXML
    void nextProject(ActionEvent event) {
        projectIndex++;

        if (projectIndex > mockProjects.length)
            projectIndex = 1;

        textFieldProjectDisplay.setText(mockProjects[projectIndex-1]);
    }

    @FXML
    void previousProject(ActionEvent event) {
        projectIndex--;

        if (projectIndex < 1)
            projectIndex = mockProjects.length;


        textFieldProjectDisplay.setText(mockProjects[projectIndex-1]);
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

    private void mockData() {
        mockProjects = new String[]{"Project 1", "Project 2", "Project 3"};
        mockSprints = new String[]{"Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4", "Sprint 5"};
    }
}
