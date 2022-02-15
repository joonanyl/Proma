package r8.view.navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class NavigationHandler {


/*
    public void initialize() {
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView("dashboard-view");
        System.out.println(view);
        mainViewPane.setCenter(view);
    }*/

    @FXML
    public Pane handleNavigation(ActionEvent event) throws IOException {
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData);
        GetView viewLoader = new GetView();
        return viewLoader.getView(userData);
    }
}
