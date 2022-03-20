package r8.view.loginView.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.App;

public class ForgotPasswordViewController {

    @FXML
    private void navigate(ActionEvent event) {
        App.handleNavigation(event);
    }
}
