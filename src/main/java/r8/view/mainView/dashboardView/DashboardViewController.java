package r8.view.mainView.dashboardView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.lang.ResourceHandler;
import r8.view.IViewController;

import java.io.IOException;
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

        IAppStateMain appStateMain = AppState.getInstance();
        IControllerMain controllerMain = new Controller();
        private App app;

        @FXML
        private void initialize() {
            System.out.println("Logged account is admin: " +appStateMain.getIsAdmin());
            mockData();
        }

        @FXML
        private void navigate(ActionEvent event) throws IOException {
            IViewController viewController = controllerMain.getActiveViewController();
            viewController.handleNavigation(event);
        }

        private void mockData() {
            Account account = appStateMain.getAccount();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            labelUserName.setText(account.getFirstName() +" "+ account.getLastName());
            labelSystemTimeDisplay.setText(ResourceHandler.getInstance().getTextResource("dashboardDay") + " " + dtf.format(now));
        }
}

