package sys.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sys.collection.EnumCollection;
import sys.collection.SceneCollection;
import sys.utility.DialogUtility;
import sys.utility.SceneHandler;
import sys.utility.UserDataHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainApplicationController implements Initializable {
    private String currentUser;
    private double currentUserBalance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(avatar.getFitWidth() / 2, avatar.getFitHeight() / 2, avatar.getFitWidth() / 2);
        avatar.setClip(clip);

        toMainPage();

        setTextFormatters(textField_deposit);
        setTextFormatters(textField_withdraw);
        setTextFormatters(textField_transfer);
    }

    @FXML private ImageView avatar;

    @FXML private VBox mainApp_mainPage;
    @FXML private VBox mainApp_depositPage;
    @FXML private VBox mainApp_withdrawPage;
    @FXML private VBox mainApp_transferPage;
    @FXML private VBox mainApp_historyPage;

    @FXML private Label mainPage_label_userName;
    @FXML private Label mainPage_label_currentBalance;
    @FXML private Label label_deposit_currentBalance;
    @FXML private Label label_withdraw_currentBalance;
    @FXML private Label label_transfer_currentBalance;

    @FXML private TextField textField_deposit;
    @FXML private TextField textField_withdraw;
    @FXML private TextField textField_transfer;
    @FXML private TextField textField_receiverName;

    @FXML private VBox transactionHistory_contentArea;

    @FXML private Button btn_logOut;

    public void setUser(String username) { // INVOKED AUTOMATICALLY BY THE SCENE HANDLER
        currentUser = username;
        currentUserBalance = UserDataHandler.getUserBalance(username);

        mainPage_label_userName.setText(currentUser);
        setCurrentBalanceLabels();
    }

    @FXML
    protected void depositAction() {
        double depositedBalance;
        try {
            depositedBalance = Double.parseDouble(textField_deposit.getText());
        } catch (NumberFormatException e) {
            DialogUtility.showError("Empty", "Please enter a value in the given fields!");
            refreshFields();
            return;
        }
        if (depositedBalance <= 0) {
            DialogUtility.showError("Input Error", "The deposited value cannot be less than or equal to zero!");
            refreshFields();
            return;
        }

        currentUserBalance += depositedBalance;
        UserDataHandler.updateBalance(currentUser, currentUserBalance);
        setCurrentBalanceLabels();
        refreshFields();
        DialogUtility.showInfo("Deposit", String.format("Successfully deposited ₱%.2f to your account.", depositedBalance));
    }

    @FXML
    protected void withdrawAction() {
        double withdrawnBalance;
        try {
            withdrawnBalance = Double.parseDouble(textField_withdraw.getText());
        } catch (NumberFormatException e) {
            DialogUtility.showError("Empty", "Please enter a value in the given fields!");
            refreshFields();
            return;
        }
        if (withdrawnBalance <= 0) {
            DialogUtility.showError("Input Error", "The deposited value cannot be less than or equal to zero!");
            refreshFields();
            return;
        }
        if (withdrawnBalance > currentUserBalance) {
            DialogUtility.showError("Input Error", "The deposited value cannot be more than the current balance!");
            refreshFields();
            return;
        }

        currentUserBalance -= withdrawnBalance;
        UserDataHandler.updateBalance(currentUser, currentUserBalance);
        setCurrentBalanceLabels();
        refreshFields();
        DialogUtility.showInfo("Withdraw", String.format("Successfully withdrawn ₱%.2f from your account.", withdrawnBalance));
    }

    @FXML
    protected void transferAction() {
        double transferredBalance;
        String receiverName = textField_receiverName.getText().trim();
        double receiverCurrentBalance = UserDataHandler.getUserBalance(receiverName);
        try {
            transferredBalance = Double.parseDouble(textField_transfer.getText());
        } catch (NumberFormatException e) {
            DialogUtility.showError("Empty", "Please enter a value in the given fields!");
            refreshFields();
            return;
        }
        if (transferredBalance <= 0) {
            DialogUtility.showError("Input Error", "The deposited value cannot be less than or equal to zero!");
            refreshFields();
            return;
        }
        if (transferredBalance > currentUserBalance) {
            DialogUtility.showError("Input Error", "The deposited value cannot be more than the current balance!");
            refreshFields();
            return;
        }
        if (receiverName.isEmpty()) {
            DialogUtility.showError("Input Error", "Please enter the receiver's name!");
            refreshFields();
            return;
        }
        if (!UserDataHandler.usernameExists(receiverName)) {
            DialogUtility.showError("Input Error", "The receiver entered does not exist!");
            refreshFields();
            return;
        }
        if (receiverName.equalsIgnoreCase("admin")) {
            DialogUtility.showError("Input Error", "You cannot transfer balance to the admin!");
            refreshFields();
            return;
        }

        currentUserBalance -= transferredBalance;
        UserDataHandler.updateBalance(currentUser, currentUserBalance);
        UserDataHandler.updateBalance(receiverName, receiverCurrentBalance + transferredBalance);
        setCurrentBalanceLabels();
        refreshFields();
        DialogUtility.showInfo("Transfer", String.format("Successfully transferred ₱%.2f to '%s'.", transferredBalance, receiverName));
    }

    @FXML
    protected void toMainPage() {
        switchPage(EnumCollection.MainApp.MAIN_PAGE);
    }

    @FXML
    protected void toDepositPage() {
        switchPage(EnumCollection.MainApp.DEPOSIT_PAGE);
    }

    @FXML
    protected void toWithdrawPage() {
        switchPage(EnumCollection.MainApp.WITHDRAW_PAGE);
    }

    @FXML
    protected void toTransferPage() {
        switchPage(EnumCollection.MainApp.TRANSFER_PAGE);
    }

    @FXML
    protected void toHistoryPage() {
        switchPage(EnumCollection.MainApp.HISTORY_PAGE);
    }

    @FXML
    protected void logOut() throws IOException {
        SceneHandler.switchScene((Stage) btn_logOut.getScene().getWindow(), SceneCollection.loginScene);
    }

    /*
    *   PRIVATE METHODS
    * */
    private void setCurrentBalanceLabels() {
        DecimalFormat d = new DecimalFormat("#,###.##");
        String formattedBalance = d.format(currentUserBalance);
        mainPage_label_currentBalance.setText(formattedBalance);
        label_deposit_currentBalance.setText(formattedBalance);
        label_withdraw_currentBalance.setText(formattedBalance);
        label_transfer_currentBalance.setText(formattedBalance);
    }

    private void refreshFields() {
        textField_deposit.setText("");
        textField_withdraw.setText("");
        textField_transfer.setText("");
        textField_receiverName.setText("");
    }

    private void switchPage(EnumCollection.MainApp page) {
        mainApp_mainPage.setVisible(false);
        mainApp_depositPage.setVisible(false);
        mainApp_withdrawPage.setVisible(false);
        mainApp_transferPage.setVisible(false);
        mainApp_historyPage.setVisible(false);

        switch(page) {
            case MAIN_PAGE -> mainApp_mainPage.setVisible(true);
            case DEPOSIT_PAGE -> mainApp_depositPage.setVisible(true);
            case WITHDRAW_PAGE -> mainApp_withdrawPage.setVisible(true);
            case TRANSFER_PAGE -> mainApp_transferPage.setVisible(true);
            case HISTORY_PAGE -> mainApp_historyPage.setVisible(true);
        }
    }

    private void setTextFormatters(TextField textField) {
        // Pattern: Digits, optional decimal point, up to 2 digits after
        Pattern validEditingState = Pattern.compile("\\d*(\\.\\d{0,2})?");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (validEditingState.matcher(newText).matches()) {
                return change;
            } else {
                System.out.println("ERROR");
                return null;
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }
}
