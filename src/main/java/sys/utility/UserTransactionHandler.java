package sys.utility;

import java.sql.*;

public class UserTransactionHandler {
    private static final String USER_DATA = "jdbc:sqlite:src/main/resources/sys/data/user/user-data.db";

    public static void createUserTable(String username) {
        try (Connection conn = DriverManager.getConnection(USER_DATA)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database!");
            }
        } catch (SQLException e) {
            System.out.println("UserDataHandler error. Cannot create database.");
        }

        try (Connection conn = DriverManager.getConnection(USER_DATA);
             Statement stmt = conn.createStatement()) {
            String sql = String.format("""
                        CREATE TABLE IF NOT EXISTS %s_transaction (
                            referenceNumber TEXT NOT NULL,
                            date TEXT NOT NULL,
                            method TEXT NOT NULL,
                            amount REAL DEFAULT 0.0
                        );
                    """, username);
            stmt.execute(sql);
            stmt.close();
            System.out.printf("Table '%s_transaction' created or already exists.%n", username);
        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
    }

    public static boolean addTransaction(String username, String referenceNumber, String date, String method, double amount) {
        String sql = String.format("INSERT INTO %s_transaction (referenceNumber, date, method, amount) VALUES (?, ?, ?, ?)", username);

        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, referenceNumber);
            stmt.setString(2, date);
            stmt.setString(3, method);
            stmt.setDouble(4, amount);
            stmt.executeUpdate();
            stmt.close();

            System.out.println("Transaction added successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("UserTransactionHandler error: " + e.getMessage());
            return false;
        }
    }

    public static void deleteUserTable(String username) {
        String sql = String.format("DROP TABLE IF EXISTS %s_transaction", username); // Safe: avoids error if table doesn't exist

        try (Connection conn = DriverManager.getConnection(USER_DATA);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.printf("Table '%s_transaction' deleted.%n", username);

        } catch (Exception e) {
            System.out.println("Error deleting table: " + e.getMessage());
        }
    }
}
