package r8.view.navigation.forgotPasswordView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateLogin;

import java.io.IOException;

public class ForgotPasswordViewController {

    final IAppStateLogin appStateLogin = AppState.getInstance();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateLogin.getLoginViewController().handleNavigation(event);
    }
}
