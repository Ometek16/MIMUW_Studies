package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Example3 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLUE);      // zmieniam kolor tła
        primaryStage.setTitle("Example 3");             // Dodaję tytuł do okna

        Image logo = new Image("file:mimuw.png");     // Tworzę logo
        primaryStage.getIcons().add(logo);              // Dddaję logo (problemy na ubuntu)

//        primaryStage.setFullScreen(true);             // zabawa z oknem
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setX(100);
        primaryStage.setAlwaysOnTop(true);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
