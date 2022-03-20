package r8.view.mainView.timeManagementView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import r8.App;

import java.io.IOException;

public class TimeManagementViewController {

    @FXML
    private BorderPane trackerViewPane;

    /*@FXML
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
    private TextField textNewEventName;*/

    private String currentSubview;

    @FXML
    private void initialize() throws IOException {
        handleNavigation("tracker-view-all");
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        //GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            trackerViewPane.setCenter(App.getView(userData));
            currentSubview = userData;
        }
    }

    @FXML
    private void handleNavigation(String viewName) throws IOException {
        //GetView viewLoader = new GetView();
        trackerViewPane.setCenter(App.getView(viewName));
        currentSubview = viewName;
    }

    //include data for task dropdown
    /*private void mockData() {
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
    }*/
}
