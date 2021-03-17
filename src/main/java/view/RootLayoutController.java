package view;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RootLayoutController {
    private Client parent;

    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea messageTextArea;

    public void setParent(Client parent) {
        this.parent = parent;
    }

    @FXML
    public void sendButtonPressed() {
        String text = messageTextArea.getText();
        if(text.startsWith("/")) {
            text = text.toLowerCase();
            text = text.substring(1);
            String[] commandParts = text.split(" ");
            String command = commandParts[0];

            String[] args = new String[commandParts.length - 1];
            // Copy the elements of the commandParts array from index 1 into args from index 0
            if(args.length > 0) {
                System.arraycopy(commandParts, 1, args, 0, commandParts.length - 1);
            }
            parent.sendCommandToServer(command, args);
        } else {
            parent.sendMessageToServer(text); // Get the text inside of the input field and send it to all the connected clients
        }
        messageTextArea.setText(""); // Reset the input field
    }
}
