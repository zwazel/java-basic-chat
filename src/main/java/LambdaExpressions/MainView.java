package LambdaExpressions;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {

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
        primaryStage.setTitle("Test"); // Sets the title for the window, aka stage

        button = new Button("Click me"); // Instantiate new button and set the text
        // Anonymous Inner Class, but with lambda
        button.setOnAction(event -> {
            System.out.println("I am an anonymous inner class, but with lambda!");
        });

        StackPane layout = new StackPane(); // Making a very simple layout
        layout.getChildren().add(button); // Add the button to the layout

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
