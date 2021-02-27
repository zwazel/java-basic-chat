package View;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainView extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test");

        button = new button("Click me");
        //button.setText("Click me");
    }
}
