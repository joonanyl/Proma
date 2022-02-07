package r8.view.dashboardView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import r8.App;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardViewController {

        @FXML
        Label labelUserName;
        @FXML
        Label labelWelcome;
        @FXML
        Label labelSystemTimeDisplay;
        @FXML
        private Button btnCustomizeView;

        //private MainViewController mvc = new MainViewController();

        private App app;

        private final StringProperty value = new SimpleStringProperty();

        @FXML
        private void initialize() {
            mockData();
        }

        @FXML
        public StringProperty valueProperty() {
            return value;
        }

        @FXML
        private void setValue (ActionEvent event) {
            final Node eventSource = (Node) event.getSource();
            this.value.set((String) eventSource.getUserData());
            System.out.println("userDate from button" + value);
        }

        public void setMainApp(App app) {
            this.app = app;
        }

        private void mockData() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            labelUserName.setText("testuser6000");
            labelSystemTimeDisplay.setText("Today is " + dtf.format(now));
        }
}

