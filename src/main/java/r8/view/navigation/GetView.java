package r8.view.navigation;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import r8.util.lang.ResourceHandler;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * Resource loader for .fxml files and {@link ResourceBundle}.
 * Used whenever parent view or subview view is loaded.
 */
public class GetView {

    private Pane view;

    /**
     * Loads requested view based on view name received as a parameter
     * @param viewName is the name of fxml file to load without the .fxml extension
     * @return loaded view
     */
    public Pane getView(String viewName) {
        FXMLLoader loader = new FXMLLoader();
        try {
            ResourceBundle resourceBundle = ResourceHandler.getInstance().getBundle();
            URL viewUrl = getClass().getResource("/fxml/" + viewName + ".fxml");
            if (viewUrl == null) {
                throw new java.io.FileNotFoundException(viewName + ".fxml not found");
            }
            loader.setLocation(viewUrl);
            loader.setResources(resourceBundle);
            loader.setCharset(StandardCharsets.UTF_8);
            view = loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}

