package HandleUserEvents;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application implements EventHandler<ActionEvent> {

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
        primaryStage.setTitle("Handle User Events"); // Sets the title for the window, aka stage

        button = new Button("Click me"); // Instantiate new button and set the text
        button.setOnAction(this); // Whenever this button gets clicked, handle method gets called

        StackPane layout = new StackPane(); // Making a very simple layout
        layout.getChildren().add(button); // Add the button to the layout

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == button) { // Identify and differentiate between different buttons
            System.out.println("OOooOOOooooo I love it when u touch me there....!");
        }
    }
}
