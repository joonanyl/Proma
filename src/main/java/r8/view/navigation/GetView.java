package r8.view.navigation;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import r8.App;
import r8.view.IController;

import java.net.URL;

public class GetView {

    private Pane view;

    public Pane getView(String viewName) {
        try {
            URL viewUrl = getClass().getResource("/fxml/" + viewName + ".fxml");
            if (viewUrl == null) {
                throw new java.io.FileNotFoundException(viewName + ".fxml not found");
            }
            view = FXMLLoader.load(viewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public Pane getView(String viewName, App app) {
        try {
            URL viewUrl = getClass().getResource("/fxml/" + viewName + ".fxml");
            FXMLLoader loader = new FXMLLoader(viewUrl);
            IController controller = loader.getController();
            controller.setApp(app);
            if (viewUrl == null) {
                throw new java.io.FileNotFoundException(viewName + ".fxml not found");
            }
            //FXMLLoader loader = new FXMLLoader(NavigationHandler.class.getResource((String)viewUrl));
            view = FXMLLoader.load(viewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}

