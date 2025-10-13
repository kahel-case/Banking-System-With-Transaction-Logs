package sys.collection;

import sys.main.Main;

import java.util.Objects;

public class SceneCollection {
    public static final String loginScene = "/sys/scenes/login-scene.fxml";
    public static final String mainApplicationScene = "/sys/scenes/main-application.fxml";
    public static final String adminPanelScene = "/sys/scenes/admin-panel.fxml";

    public static final String style = Objects.requireNonNull(Main.class.getResource("/sys/style/default-style.css")).toExternalForm();
}
