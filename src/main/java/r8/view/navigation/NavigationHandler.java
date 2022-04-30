package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class NavigationHandler {

    private String currentView;

    @FXML
    public Pane handleNavigation(ActionEvent event) throws IOException {
        final Node eventSource;
        try{
            eventSource = (Node) event.getSource();
        }catch (ClassCastException e){
            return handleMenuItemNavigation(event);
        }
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData + " (printed from NavigationHandler)");
        GetView viewLoader = new GetView();
        currentView = userData;
        return viewLoader.getView(userData);
    }

    public Pane handleMenuItemNavigation(ActionEvent event) throws IOException{
        final MenuItem eventsource = (MenuItem) event.getSource();
        String userData = (String) eventsource.getUserData();
        GetView viewLoader = new GetView();
        currentView = userData;
        return viewLoader.getView(userData);
    }

    public String getCurrentView() {
        return this.currentView;
    }
}
