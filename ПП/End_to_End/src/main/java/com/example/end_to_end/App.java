package com.example.end_to_end;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        addScaleTransformation(root, scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Task");
        primaryStage.show();
    }

    private void addScaleTransformation(Parent root, Scene scene) {
        Scale scale = new Scale(1, 1);
        root.getTransforms().add(scale);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactorX = newValue.doubleValue() / scene.getWidth();
            scale.setX(scaleFactorX);
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactorY = newValue.doubleValue() / scene.getHeight();
            scale.setY(scaleFactorY);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}