package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class NavigationHandler {

    @FXML
    public Pane handleNavigation(ActionEvent event) throws IOException {
        String userData = "";
        System.out.println(event.getSource());
        if(event.getSource().getClass().equals(MenuItem.class)){
            MenuItem menuItem = (MenuItem) event.getSource();
            userData = (String) menuItem.getUserData();
        }else{
            final Node eventSource = (Node) event.getSource();
            userData = (String) eventSource.getUserData();
        }
        System.out.println("Clicked " + userData + " (printed from NavigationHandler)");
        GetView viewLoader = new GetView();
        return viewLoader.getView(userData);
    }
}
