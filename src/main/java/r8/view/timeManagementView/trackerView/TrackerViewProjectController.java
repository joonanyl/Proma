package r8.view.timeManagementView.trackerView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class TrackerViewProjectController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddProject;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSubProject;

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

    private String[] mockProjects;
    private int projectIndex = 1;

    public void initialize() {
        mockData();
        initDatePicker();
        textFieldProjectDisplay.setText(mockProjects[projectIndex-1]);
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
    }
}
