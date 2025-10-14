package sys.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sys.collection.SceneCollection;

public class UserScore extends HBox {
    private String _username;
    private double _anomalyScore;
    private String _activity;

    public UserScore(String username, double anomalySCore, String activity) {
        this._username = username;
        this._anomalyScore = anomalySCore;
        this._activity = activity;

        this.setId("user-info");
        this.getStylesheets().add(SceneCollection.style);
        this.setPadding(new Insets(15));

        TextFlow textArea = new TextFlow();
        textArea.setLineSpacing(5);

        Text text = new Text();
        updateText(text);
        textArea.setMinWidth(250);
        textArea.getChildren().addLast(text);


        this.getChildren().addLast(textArea);
        this.setMinHeight(60);
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        HBox.setMargin(this, new Insets(10));
    }

    public void updateText(Text newText) {
        newText.setText(String.format("User %s's activity is %s (Score: %.2f)", _username, _activity, _anomalyScore));
    }
}
