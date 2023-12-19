package com.example.end_to_end;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Task");
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.print("Which interface will be used? (console/graphic/web): ");
        Scanner cin = new Scanner(System.in);
        String inputString = cin.nextLine();
        if (inputString.equals("console") || inputString.equals("Console")){
            ConsolUI.start();
        }
        else if (inputString.equals("graphic") || inputString.equals("Graphic")) {
            launch();
        }
        else if(inputString.equals("web") || inputString.equals("Web")){
        }
        else {
            System.out.println("Invalid input. Exiting program.");
            System.exit(1);
        }
        System.exit(0);
    }
}