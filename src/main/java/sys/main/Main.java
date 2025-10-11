package sys.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sys.collection.ImageCollection;
import sys.collection.SceneCollection;
import sys.utility.UserDataHandler;
import sys.utility.UserTransactionHandler;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(SceneCollection.loginScene));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(SceneCollection.style);

        stage.setTitle("Banking System");
        stage.getIcons().add(ImageCollection.frameIcon);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        UserDataHandler.CreateTable();
        UserDataHandler.initializeAdmin();
        launch();
    }
}