package sys.utility;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sys.collection.SceneCollection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog extends TextFlow {

    public TransactionLog(int referenceNumber, double amount, String paymentMethod) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Text text = new Text();

        this.setId("transaction_log");
        this.getStylesheets().add(SceneCollection.style);
        this.setLineSpacing(5);

        text.setFont(Font.font("Monospaced", 12));
        text.setText(String.format("""
                        %-30s %-30d%n\
                        %-30s %-30s%n\
                        %-30s ₱%-30.2f%n\
                        %-30s %-30S""",
                "Reference Number:", referenceNumber, "Date & Time:", formattedDateTime, "Amount:", amount, "Payment Method:", paymentMethod));
        this.getChildren().add(text);

        VBox.setMargin(this, new Insets(15));
        this.setPadding(new Insets(15));
    }
}