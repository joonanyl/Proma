package r8.view.loginView.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.controller.Controller;
import r8.controller.IControllerLogin;

import java.io.IOException;

public class ForgotPasswordViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        IControllerLogin controller = new Controller();
        controller.getActiveViewController().handleNavigation(event);
    }
}
