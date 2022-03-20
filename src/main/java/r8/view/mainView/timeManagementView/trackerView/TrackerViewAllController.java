package r8.view.mainView.timeManagementView.trackerView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

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
    private TextField textFieldDayDisplay;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldHoursWorked;

    @FXML
    private TextField textFieldMonthDisplay;

    public void initialize() {
        //siirsin initDatePickerin tänne -sebastian
        datePickerEvent.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
    }


    /* private void initDatePicker() {
        
    } */


}
