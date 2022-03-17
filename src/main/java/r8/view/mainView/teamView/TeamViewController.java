package r8.view.mainView.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;

import java.io.IOException;

public class TeamViewController {

    final IAppStateMain appStateMain = AppState.getInstance();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }
}
