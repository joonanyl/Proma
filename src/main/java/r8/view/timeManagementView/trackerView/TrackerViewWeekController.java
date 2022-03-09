package r8.view.timeManagementView.trackerView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

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
    final int weeks = 52;

    @FXML
    private TextField textFieldWeekDisplay;

    public void initialize() {
        initDatePicker();
        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
    }

    @FXML
    public void addWeek() {
        displayedWeek++;

        if (displayedWeek > weeks)
            displayedWeek = 1;

        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
    }

    public void subWeek() {
        displayedWeek--;

        if (displayedWeek < 1)
            displayedWeek = weeks;

        textFieldWeekDisplay.setText(String.valueOf(displayedWeek));
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
}
