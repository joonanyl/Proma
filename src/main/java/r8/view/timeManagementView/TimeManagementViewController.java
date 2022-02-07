package r8.view.timeManagementView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import r8.App;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeManagementViewController {

    @FXML
    private Button btnAddEntry;

    @FXML
    private Button btnCreateNewEvent;

    @FXML
    private ComboBox<?> cBoxCalendarEntryTask;

    @FXML
    private DatePicker datePickerCalendarEntry;

    @FXML
    private Label labelSystemTimeDisplay;

    @FXML
    private Label labelUserName;

    @FXML
    private Label labelWelcome;

    @FXML
    private TextField textHoursWorked;

    @FXML
    private TextField textNewEventName;

    private App app;

    @FXML
    private void initialize() {
        mockData();
    }

    public void setMainApp(App app) {
        this.app = app;
    }

    //include data for task dropdown
    private void mockData() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        labelUserName.setText("testuser6000");
        labelSystemTimeDisplay.setText("Today is " + dtf.format(now));

        datePickerCalendarEntry.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
    }
}
