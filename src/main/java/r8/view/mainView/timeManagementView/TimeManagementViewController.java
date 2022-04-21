package r8.view.mainView.timeManagementView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import r8.App;
import r8.model.util.UIElementVisibility;
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

    @FXML
    private Button btnAddNewEvent;

    @FXML
    private VBox vBoxEntry = new VBox();

    @FXML
    private VBox vBoxEvent = new VBox();

    @FXML
    private VBox vBoxToggle = new VBox();

    private String currentSubview;
    private UIElementVisibility visibility = new UIElementVisibility();

    @FXML
    private void initialize() throws IOException {
        initSubview("tracker-view-all");
        visibility.toggleOff(vBoxToggle);
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        trackerViewPane.setCenter(nav.handleNavigation(event));
        currentSubview = nav.getCurrentView();
    }

    @FXML
    public void initSubview(String viewName) throws IOException {
        GetView viewLoader = new GetView();
        trackerViewPane.setCenter(viewLoader.getView(viewName));
        currentSubview = viewName;
    }

    @FXML
    public void toggleNewEvent() {
        visibility.toggleVisibility(vBoxToggle);
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
