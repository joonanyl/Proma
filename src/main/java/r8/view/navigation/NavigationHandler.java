package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class NavigationHandler {

    @FXML
    public Pane handleNavigation(ActionEvent event) throws IOException {
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData + " (printed from NavigationHandler)");
        GetView viewLoader = new GetView();
        return viewLoader.getView(userData);
    }
}
