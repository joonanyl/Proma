package r8.view.dashboardView;

import javafx.fxml.FXML;
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

        private App app;

        @FXML
        private void initialize() {
            mockData();
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

