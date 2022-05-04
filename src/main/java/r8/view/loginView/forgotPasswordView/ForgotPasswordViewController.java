package r8.view.loginView.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.controller.Controller;
import r8.controller.IControllerLogin;

import java.io.IOException;

/**
 * View controller for account password reset function.
 * *Function not yet implemented in loginView, but available in mainView profile*
 *
 * @author
 */
public class ForgotPasswordViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        IControllerLogin controller = new Controller();
        controller.getActiveViewController().handleNavigation(event);
    }
}
