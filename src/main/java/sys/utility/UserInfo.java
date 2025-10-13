package sys.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sys.collection.SceneCollection;

public class UserInfo extends HBox {
    private String _username;
    private String _status;

    public UserInfo(String username, String status) {
        this._username = username;
        this._status = status;

        this.setId("user-info");
        this.getStylesheets().add(SceneCollection.style);
        this.setPadding(new Insets(15));

        TextFlow textArea = new TextFlow();
        textArea.setLineSpacing(5);

        Text text = new Text(String.format("USERNAME: %s - STATUS: %s", _username, _status));
        textArea.setMinWidth(250);
        textArea.getChildren().addLast(text);

        Button blockButton = new Button("Block");
        blockButton.setMinSize(100, 40);
        Button unblockButton = new Button("Unblock");
        unblockButton.setMinSize(100, 40);


        this.getChildren().addLast(textArea);
        this.getChildren().addLast(blockButton);
        this.getChildren().addLast(unblockButton);
        this.setMinHeight(80);
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        HBox.setMargin(this, new Insets(10));
    }
}
