package sys.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sys.collection.SceneCollection;

import java.io.IOException;
import java.util.Objects;

public class SceneHandler {

    public static void switchScene(Stage stage, String nextScene) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SceneHandler.class.getResource(nextScene)));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneCollection.style);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchSceneWithUser(Stage stage, String nextScene, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SceneHandler.class.getResource(nextScene)));
        Parent root = loader.load();

        Object controller = loader.getController();
        try {
            controller.getClass().getMethod("setUser", String.class).invoke(controller, username);
        } catch (Exception e) {
            System.out.println("Method does not exist");
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneCollection.style);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.centerOnScreen();
        stage.show();
    }
}
