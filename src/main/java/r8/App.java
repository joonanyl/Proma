package r8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import r8.model.appState.AppState;
import r8.util.lang.ResourceHandler;
import r8.util.lang.LanguageHandler;
import r8.view.IViewController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author Aarni Pesonen
 *
 */

public class App extends Application
{
    private Stage stage;
    private boolean displayLogin;

    /**
     * Starts the UI of Proma application
     * @param stage container for application view to be displayed
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        displayLogin = true;
        switchScene();
    }

    /**
     * Called when switching between login and main work view
     */
    public void switchScene() {
        try {
            String viewToLoad = displayLogin ? "login-view" : "main-view";

            FXMLLoader loader = new FXMLLoader();
            ResourceBundle resourceBundle = ResourceHandler.getInstance().getBundle();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/fxml/" + viewToLoad + ".fxml")));
            loader.setResources(resourceBundle);
            loader.setCharset(StandardCharsets.UTF_8);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(displayLogin ? LanguageHandler.getText("loginTitle") : LanguageHandler.getText("appTitle"));
            setStageSize();

            stage.getIcons().add(new Image("/image/proma-icon.png"));
            // pass App reference to controller to enable sceneSwitching
            IViewController viewController = loader.getController();
            viewController.setApp(this);

            AppState.getInstance().setViewController(viewController);
            displayLogin = !displayLogin;

            stage.show();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Controls starting stage size for login and main view
     */
    private void setStageSize() {
        stage.setResizable(!displayLogin);
        stage.setMaximized(!displayLogin);

        if (displayLogin) {
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    public void reloadMainView() {
        displayLogin = false;
        switchScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
