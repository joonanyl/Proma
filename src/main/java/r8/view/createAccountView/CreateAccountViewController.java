package r8.view.createAccountView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;

public class CreateAccountViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getLoginViewController().handleNavigation(event);
    }
}
