package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import javafx.scene.layout.Pane;

/**
 * View utility for handling ActionEvent based navigation.
 * Navigation information is received as ActionEvent userData and contains name
 * of view to be loaded.
 * Calls {@link GetView} to load the requested .fxml resources.
 *
 * @author Aarni Pesonen
 */
public class NavigationHandler {

    /**
     * Returns requested view as which request UI elements inherit from
     * @param event triggering the navigation
     * @return requested view as Pane, as loaded by {@link GetView}
     */
    @FXML
    public Pane handleNavigation(ActionEvent event) {
            final Node eventSource = (Node) event.getSource();
            String userData = (String) eventSource.getUserData();
            GetView viewLoader = new GetView();
            return viewLoader.getView(userData);
    }

    /**
     * Works just as handleNavigation, but processes MenuItem
     * ActionEvents instead.
     * @param event triggering the navigation
     * @return requested view as Pane, as loaded by {@link GetView}
     */
    public Pane handleMenuItemNavigation(ActionEvent event) {
        MenuItem eventsource = (MenuItem) event.getSource();
        String userData = (String) eventsource.getUserData();
        GetView viewLoader = new GetView();
        return viewLoader.getView(userData);
    }
}
