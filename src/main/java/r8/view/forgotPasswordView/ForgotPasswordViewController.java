package r8.view.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;

public class ForgotPasswordViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getLoginViewController().handleNavigation(event);
    }
}
