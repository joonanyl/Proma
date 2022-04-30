package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

/**
 * @author Aarni Pesonen
 */
public class NavigationHandler {


    @FXML
    public Pane handleNavigation(ActionEvent event) {
            final Node eventSource = (Node) event.getSource();
            String userData = (String) eventSource.getUserData();
            System.out.println("Clicked " + userData + " (printed from NavigationHandler)");
            GetView viewLoader = new GetView();
            return viewLoader.getView(userData);
    }

    public Pane handleMenuItemNavigation(ActionEvent event) {
        MenuItem eventsource = (MenuItem) event.getSource();
        String userData = (String) eventsource.getUserData();
        GetView viewLoader = new GetView();
        return viewLoader.getView(userData);
    }
}
