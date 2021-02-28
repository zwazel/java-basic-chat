package CommunicatingBetweenWindows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The Stage is the whole window
     * A scene is just a part/the content inside of the window
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        button = new Button("Click me");
        button.setOnAction(e -> {
            boolean result = ConfirmBox.display("Send Nudes?", "Do you really want to send Nudes?");
            System.out.println(result);
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);

        window.setScene(scene);
        window.setTitle("Alert Boxes!");
        window.show();
    }
}
