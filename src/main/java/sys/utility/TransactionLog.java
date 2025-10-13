package sys.utility;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sys.collection.SceneCollection;

public class TransactionLog extends TextFlow {
    public TransactionLog(String referenceNumber, String date, double amount, String paymentMethod) {
        Text text = new Text();

        this.setId("transaction-log");
        this.getStylesheets().add(SceneCollection.style);
        this.setLineSpacing(5);

        text.setFont(Font.font("Monospaced", 12));
        text.setText(String.format("""
                        %-30s %-30s%n\
                        %-30s %-30s%n\
                        %-30s ₱%-30.2f%n\
                        %-30s %-30s""",
                "Reference Number:", referenceNumber, "Date & Time:", date, "Amount:", amount, "Payment Method:", paymentMethod));
        this.getChildren().add(text);

        VBox.setMargin(this, new Insets(15));
        this.setPadding(new Insets(15));
    }
}