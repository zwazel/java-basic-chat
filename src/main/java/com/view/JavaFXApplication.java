package com.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public boolean initComponents(Stage primaryStage) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
            root = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setParent(this);

            Scene scene = new Scene(root, 300, 250);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Java Chat Lets Go");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Can't load FXML file!");
            return false;
        }

        return true;
    }

    @Override
    public void start(Stage primaryStage) {
        /*
        Scene scene = new Scene(new GridPane(), 300,250);

        primaryStage.setScene(scene);
        primaryStage.setTitle("test");
        primaryStage.show();

        System.out.println("/com.view/RootLayout.fxml");
         */

        initComponents(primaryStage);
    }
}
