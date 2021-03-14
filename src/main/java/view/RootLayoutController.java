package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RootLayoutController {
    private JavaFXApplication parent;

    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea messageTextArea;

    public void setParent(JavaFXApplication parent) {
        this.parent = parent;
    }

    @FXML
    public void sendButtonPressed() {
        String message = messageTextArea.getText();
        messageTextArea.setText("");


    }
}
