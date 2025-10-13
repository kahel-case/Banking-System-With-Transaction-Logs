package sys.utility;


import sys.collection.DatabaseCollection;

import java.sql.*;

public class UserDataHandler {
    private static final String USER_DATA = DatabaseCollection.USER_DATA;

    public static void CreateTable() {
        try (Connection conn = DriverManager.getConnection(USER_DATA)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database!");
            }
        } catch (SQLException e) {
            System.out.println("UserDataHandler error. Cannot create database.");
        }

        try (Connection conn = DriverManager.getConnection(USER_DATA);
            Statement stmt = conn.createStatement()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS users (
                            username TEXT NOT NULL UNIQUE,
                            password TEXT NOT NULL,
                            balance REAL DEFAULT 0.0,
                            status TEXT NOT NULL
                        );
                    """;
            stmt.execute(sql);
            stmt.close();
            System.out.println("Table 'users' created or already exists.");
        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
    }

    public static boolean createUser(String username, String password, String status) {
        String sql = "INSERT INTO users (username, password, status) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(USER_DATA);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, status);
            stmt.executeUpdate();
            stmt.close();

            System.out.println("User inserted successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
            return false;
        }
    }

    public static boolean usernameExists(String username) {
        boolean usernameExists = false;
        try (Connection conn = DriverManager.getConnection(USER_DATA);
            PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users");
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String temp = rs.getString("username");
                if (temp.equalsIgnoreCase(username)) {
                    usernameExists = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
        return usernameExists;
    }

    public static void updateBalance(String username, double balance) {
        String sql = "UPDATE users SET balance = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, balance);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
    }

    public static void updateUserStatus(String username, String status) {
        String sql = "UPDATE users SET status = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
    }

    public static double getUserBalance(String username) {
        double balance = 0.0;
        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
        return balance;
    }

    public static String getUserStatus(String username) {
        String status = "Unknown";
        try (Connection conn = DriverManager.getConnection(USER_DATA);
            PreparedStatement stmt = conn.prepareStatement("SELECT status FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                status = rs.getString("status");
            }

        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
        return status;
    }

    public static String getPassword(String username) {
        String password = "";
        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString("password");
            }

        } catch (Exception e) {
            System.out.println("UserDataHandler error: " + e.getMessage());
        }
        return password;
    }

    public static void initializeAdmin() {
        String sql = "INSERT INTO users (username, password, status) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(USER_DATA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "admin");
            stmt.setString(2, "123");
            stmt.setString(3, "Admin");
            stmt.executeUpdate();
            stmt.close();

            System.out.println("Admin account successfully initialized!");
        } catch (Exception e) {
            System.out.println("Admin account already initialized");
        }
    }
}
