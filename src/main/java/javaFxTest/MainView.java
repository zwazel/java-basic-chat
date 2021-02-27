package javaFxTest;

import javafx.application.Application;
import javafx.scene.control.Button;
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

        /**
         * Sets the title for the window, aka stage
         */
        primaryStage.setTitle("Test");

        button = new Button("Click me"); // Instanciate new button and set the text

        /**
         *
         */
    }
}
