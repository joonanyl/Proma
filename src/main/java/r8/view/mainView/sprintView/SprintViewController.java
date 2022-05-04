package r8.view.mainView.sprintView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.model.appState.AppState;
import r8.view.IViewController;

import java.io.IOException;

/**
 * Controller for sprint subview
 * *not yet fully implemented*
 * @author Aarni Pesonen
 */
public class SprintViewController {

    private final IViewController viewController = AppState.getInstance().getViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        viewController.handleNavigation(event);
    }
}
