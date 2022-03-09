package r8.view.timeManagementView.trackerView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class TrackerViewWeekController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddWeek;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSubWeek;

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
    private TableColumn<?, ?> tableColProject;

    @FXML
    private TableColumn<?, ?> tableColRemaining;

    @FXML
    private TableView<?> tableViewWeek;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldHoursWorked;

    private int displayedWeek = 1;

    @FXML
    private TextField textFieldWeekDisplay;

    public void initialize() {
        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
    }
    @FXML
    public void addWeek() {
        displayedWeek += 1;
        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
    }

    public void subWeek() {
        displayedWeek -= 1;
        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
    }
}
