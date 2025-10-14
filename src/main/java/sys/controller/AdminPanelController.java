package sys.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sys.collection.DatabaseCollection;
import sys.collection.SceneCollection;
import sys.utility.SceneHandler;
import sys.utility.UserInfo;
import sys.utility.IsolationTree;
import sys.utility.UserScore;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AdminPanelController implements Initializable {
    LinkedList<String> usernames = new LinkedList<>();

    @FXML private Button btn_exitAdminPanel;
    @FXML private VBox users_contentArea;
    @FXML private VBox fraudulentUsers_contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        String sql = "SELECT username, status FROM users";

        try (Connection conn = DriverManager.getConnection(DatabaseCollection.USER_DATA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            usernames.clear();
            while (rs.next()) {
                String username = rs.getString("username");
                String status = rs.getString("status");
                if (username.equalsIgnoreCase("admin")) continue;

                usernames.add(username);
                UserInfo user = new UserInfo(username, status);
                users_contentArea.getChildren().addFirst(user);
            }

            System.out.println("User data successfully loaded.");

        } catch (Exception e) {
            System.out.println("Database load error: " + e.getMessage());
        }
    }

    @FXML
    protected void exitAdminPanel() throws IOException {
        SceneHandler.switchScene((Stage) btn_exitAdminPanel.getScene().getWindow(), SceneCollection.loginScene);
    }

    @FXML
    protected void checkFraudulentActivities() {
        System.out.println("Running fraud checker.");

        fraudulentUsers_contentArea.getChildren().clear();
        Map<String, List<Double>> userTransactions = new HashMap<>();

        // Gather all transactions
        for (String name : usernames) {
            String sql = String.format("SELECT amount FROM %s_transaction", name);

            try (Connection conn = DriverManager.getConnection(DatabaseCollection.USER_DATA);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                List<Double> transactions = new ArrayList<>();
                while (rs.next()) {
                    double amountStr = rs.getDouble("amount");
                    try {
                        transactions.add(amountStr);
                    } catch (NumberFormatException ignored) {}
                }

                if (!transactions.isEmpty()) {
                    userTransactions.put(name, transactions);
                }

            } catch (Exception e) {
                System.out.println("Error reading " + name + " transactions: " + e.getMessage());
            }
        }

        for (Map.Entry<String, List<Double>> entry : userTransactions.entrySet()) {
            String username = entry.getKey();
            List<Double> data = entry.getValue();

            double anomalyScore = IsolationTree.detectAnomaly(data);

            UserScore user;
            if (anomalyScore > 5.0) { // arbitrary threshold
                user = new UserScore(username, anomalyScore, "suspicious");
            } else {
                user = new UserScore(username, anomalyScore, "normal");
            }
            fraudulentUsers_contentArea.getChildren().addFirst(user);
        }

        System.out.println("Fraud detection complete.");
    }
}
