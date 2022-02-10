package r8.view.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.view.navigation.GlobalControllerRef;

import java.io.IOException;

public class ForgotPasswordViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerRef.getInstance().getLoginViewController().handleNavigation(event);
    }
}
