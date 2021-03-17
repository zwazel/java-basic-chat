package view;

import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXApplication extends Application {
    private static final int PREF_WIDTH = 750;
    private static final int PREF_HEIGHT = 500;

    static FXMLLoader loader = new FXMLLoader();

    public static void main(String[] args) {
        launch(args);
    }

    public boolean initComponents(Stage primaryStage) {
        Parent root;
        try {
            loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setMinWidth(PREF_WIDTH);
            primaryStage.setMinHeight(PREF_HEIGHT);

            primaryStage.setTitle("Java Chat Lets Go");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Can't load FXML file!");
            return false;
        }

        return true;
    }

    public static void setControllerParent(Client parent) {
        RootLayoutController controller = loader.getController();
        controller.setParent(parent);
    }

    @Override
    public void start(Stage primaryStage) {
        initComponents(primaryStage);
    }
}
