package sys.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sys.collection.DatabaseCollection;
import sys.collection.SceneCollection;
import sys.utility.SceneHandler;
import sys.utility.TransactionLog;
import sys.utility.UserInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql = "SELECT username, status FROM users";

        try (Connection conn = DriverManager.getConnection(DatabaseCollection.USER_DATA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String status = rs.getString("status");
                if (status.equalsIgnoreCase("admin")) continue;

                UserInfo user = new UserInfo(username, status);
                users_contentArea.getChildren().addFirst(user);
            }
            System.out.println("Transaction history successfully instantiated!");
        } catch (Exception e) {
            System.out.println("Instantiation error: " + e.getMessage());
        }
    }

    @FXML private Button btn_exitAdminPanel;
    @FXML private VBox users_contentArea;

    @FXML
    protected void exitAdminPanel() throws IOException {
        SceneHandler.switchScene((Stage) btn_exitAdminPanel.getScene().getWindow(), SceneCollection.loginScene);
    }
}
