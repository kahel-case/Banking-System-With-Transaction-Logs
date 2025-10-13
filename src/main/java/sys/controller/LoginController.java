package sys.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sys.collection.EnumCollection;
import sys.collection.SceneCollection;
import sys.utility.DialogUtility;
import sys.utility.SceneHandler;
import sys.utility.UserDataHandler;
import sys.utility.UserTransactionHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchPage(EnumCollection.Launcher.MAIN_PAGE);
        refreshFields();
    }

    @FXML private TextField loginPage_userName;
    @FXML private TextField loginPage_password;
    @FXML private TextField createAccountPage_userName;
    @FXML private TextField createAccountPage_password;
    @FXML private TextField createAccountPage_confirmPassword;

    @FXML private Button btn_logIn;

    @FXML private VBox launcher_signInPage;
    @FXML private VBox launcher_createAccountPage;
    @FXML private VBox launcher_mainPage;

    @FXML
    protected void toSignInPage() {
        switchPage(EnumCollection.Launcher.SIGN_IN);
    }

    @FXML
    protected void toCreateAccountPage() {
        switchPage(EnumCollection.Launcher.CREATE_ACCOUNT);
    }

    @FXML
    protected void toMainPage() { switchPage(EnumCollection.Launcher.MAIN_PAGE); refreshFields(); }

    @FXML // Called when the 'Sign-In' button is pressed
    protected void launchMainApplication() {
        String username = loginPage_userName.getText().trim().toLowerCase();
        String password = loginPage_password.getText().trim().toLowerCase();

        // RETURNS IF FIELDS ARE EMPTY
        if (username.isEmpty() || password.isEmpty()) {
            DialogUtility.showError("Empty", "Please enter the username and password!");
            return;
        }

        // RETURNS IF USERNAME DOES NOT EXIST
        if (!UserDataHandler.usernameExists(username)) {
            DialogUtility.showError("Credential Error", "The username entered does not exist!");
            return;
        }

        // RETURNS IF THE PASSWORD DOES NOT MATCH
        if (!UserDataHandler.getPassword(username).equalsIgnoreCase(password)) {
            DialogUtility.showError("Credential Error", "The password entered does not match the user!");
            loginPage_password.setText("");
            loginPage_password.requestFocus();
            return;
        }

        // SPECIFICALLY LAUNCHES ADMIN PANEL IF CRITERIA MET
        if (UserDataHandler.getUserStatus(username).equalsIgnoreCase("Admin")) {
            try {
                SceneHandler.switchScene((Stage) btn_logIn.getScene().getWindow(), SceneCollection.adminPanelScene);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            refreshFields();
            return;
        }

        // RETURNS IF USER IS BLOCKED
        if (!UserDataHandler.getUserStatus(username).equalsIgnoreCase("Active")) {
            DialogUtility.showWarning("Admin", "Your account has been blocked by the admin.");
            refreshFields();
            return;
        }

        // LAUNCHES MAIN APP IF ALL CRITERIA MET
        try {
            SceneHandler.switchSceneWithUser((Stage) btn_logIn.getScene().getWindow(), SceneCollection.mainApplicationScene, username);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        refreshFields();
    }

    @FXML // Called when the 'Create Account' button is pressed
    protected void onCreateAccount() {
        String username = createAccountPage_userName.getText().trim().toLowerCase();
        String password = createAccountPage_password.getText().trim().toLowerCase();
        String confirmPassword = createAccountPage_confirmPassword.getText().trim().toLowerCase();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            DialogUtility.showError("Empty", "Please enter values in the given fields!");
            return;
        }
        if (!password.equalsIgnoreCase(confirmPassword)) {
            DialogUtility.showError("Password", "The password and confirm password fields do not match!");
            return;
        }

        if (UserDataHandler.createUser(username, password, EnumCollection.Active)) {
            UserTransactionHandler.createUserTable(username);
            DialogUtility.showInfo("Success", "User successfully created!");
        } else {
            DialogUtility.showWarning("User Exists", String.format("The username '%s' is already taken!\nPlease choose another one.", username));
            refreshFields();
            return;
        }
        toMainPage();
    }

    /*
    *   PRIVATE METHODS
    * */

    private void switchPage(EnumCollection.Launcher page) {
        launcher_signInPage.setVisible(false);
        launcher_createAccountPage.setVisible(false);
        launcher_mainPage.setVisible(false);

        switch(page) {
            case SIGN_IN -> launcher_signInPage.setVisible(true);
            case CREATE_ACCOUNT -> launcher_createAccountPage.setVisible(true);
            case MAIN_PAGE -> launcher_mainPage.setVisible(true);
        }
    }

    private void refreshFields() {
        loginPage_userName.setText("");
        loginPage_password.setText("");
        createAccountPage_userName.setText("");
        createAccountPage_password.setText("");
        createAccountPage_confirmPassword.setText("");
    }
}