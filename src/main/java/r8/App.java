package r8;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class App extends Application
{
	private static Stage stage;

    //private String viewToLoad;
    //private boolean displayLogin;

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        //displayLogin = true;
        switchToScene("login-view", "Proma - login", false, false);
    }

    public static void handleNavigation(ActionEvent event) {
        Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData + " (printed from App.java)");
        switchToScene(userData);
    }

    public static void switchToScene(String sceneName, String title, boolean resizable, boolean maximized) {
        load(sceneName);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setMaximized(maximized);
        stage.show();
    }

    public static void switchToScene(String sceneName) {
        switchToScene(sceneName, "", false, false);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private static void load(String sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource("/fxml/" + sceneName + ".fxml")));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Pane getView(String viewName) {
        Pane view = null;
        try {
            URL viewUrl = App.class.getResource("/fxml/" + viewName + ".fxml");
            if (viewUrl == null) {
                throw new java.io.FileNotFoundException(viewName + ".fxml not found");
            }
            FXMLLoader loader = new FXMLLoader(viewUrl);
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /* public void switchScene() {
        try {
            if (displayLogin) { viewToLoad = "login-view"; }
            if (!displayLogin) { viewToLoad = "main-view"; }
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/" + viewToLoad + ".fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            if (displayLogin) {
                stage.setTitle("Proma - Login");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setMaximized(false);
                stage.sizeToScene();
                stage.centerOnScreen();
            }

            if (!displayLogin) {
                stage.setTitle("Proma - Project Manager v0.1");
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setMaximized(true);
            }
            IViewController viewController = loader.getController();
            viewController.setApp(this);

            AppState.getInstance().setViewController(viewController);
            displayLogin = !displayLogin;

            stage.show();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    } */

    public static void main(String[] args) {
        launch(args);
    }
}