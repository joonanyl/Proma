package r8.view.timeManagementView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import r8.App;
import r8.view.navigation.BreadcrumbObject;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private App app;

    @FXML
    private void initialize() throws IOException {
        handleNavigation("tracker-view-all");
    }

    public void setMainApp(App app) {
        this.app = app;
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            trackerViewPane.setCenter(viewLoader.getView(userData));
            currentSubview = userData;
        }
    }

    @FXML
    public void handleNavigation(String viewName) throws IOException {
        GetView viewLoader = new GetView();
        trackerViewPane.setCenter(viewLoader.getView(viewName));
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
