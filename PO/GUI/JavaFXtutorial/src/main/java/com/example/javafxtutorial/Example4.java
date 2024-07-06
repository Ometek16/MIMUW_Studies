package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Example4 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // tworzymy przycisk
        Button b = new Button("Hello!");

        // dodajemy główny węzeł
        VBox content = new VBox();

        // łączymy przycisk z głównym węzłem
        content.getChildren().add(b);

        // Przygotowanie sceny
        // Tworzymy obiekt Scene - w konstruktorze rootem jest nasz główny węzeł
        Scene sc = new Scene(content, 320, 240);

        // Nasz występ
        // Dodajemy scenę Scene do sceny Stage :)
        primaryStage.setScene(sc);
        primaryStage.show();
    }

}
