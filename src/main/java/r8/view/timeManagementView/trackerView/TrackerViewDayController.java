package r8.view.timeManagementView.trackerView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class TrackerViewDayController {

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

    private String[] months;
    private int daysInMonth;
    private int monthsIndex = 1;

    private int year = 2022;

    public void initialize() {
        initDatePicker();
        mockData();
        daysInMonth = getMonthDays(year, monthsIndex);
        textFieldMonthDisplay.setText(months[monthsIndex-1]);
        textFieldDayDisplay.setText(String.valueOf(Calendar.DAY_OF_MONTH));
    }

    @FXML
    void nextDay(ActionEvent event) {
        int currentDay = parseInt(textFieldDayDisplay.getText());
        currentDay++;
        if (currentDay > daysInMonth) {currentDay = 1;}
            textFieldDayDisplay.setText(String.valueOf(currentDay));
    }

    @FXML
    void previousDay(ActionEvent event) {
        int currentDay = parseInt(textFieldDayDisplay.getText());
        currentDay--;
        if (currentDay < 1) {currentDay = daysInMonth;}
            textFieldDayDisplay.setText(String.valueOf(currentDay));
    }

    @FXML
    void nextMonth(ActionEvent event) {
        monthsIndex++;
        if (monthsIndex > 12) {
            monthsIndex = 1;
        }
        daysInMonth = getMonthDays(year, monthsIndex);
        textFieldMonthDisplay.setText(months[monthsIndex-1]);
    }

    @FXML
    void previousMonth(ActionEvent event) {
        monthsIndex--;
        if (monthsIndex <= 0) {
            monthsIndex = 12;
        }
        daysInMonth = getMonthDays(year, monthsIndex);
        textFieldMonthDisplay.setText(months[monthsIndex-1]);
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

    private int getMonthDays(int year, int monthsIndex) {
        YearMonth yearMonth = YearMonth.of(year, monthsIndex);
        return yearMonth.lengthOfMonth();
    }

    private void mockData() {
        months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }
}
