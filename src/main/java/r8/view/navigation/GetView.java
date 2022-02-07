package r8.view.navigation;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class GetView {

    private Pane view;

    //get generic class reference from calling class?
    public Pane getView(String viewName) {
        try {
            URL viewUrl = NavigationHandler.class.getResource("/fxml/" + viewName + ".fxml");
            if (viewUrl == null) {
                throw new java.io.FileNotFoundException(viewName + ".fxml not found");
            }
            view = FXMLLoader.load(viewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}

