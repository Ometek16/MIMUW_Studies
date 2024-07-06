package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Example5 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // tworzymy przycisk
        Button b = new Button("Hello!");

        // dodajemy główny węzeł
        VBox r = new VBox();

        // NOWE - dodajemy pole do wyświetlania wiadomości, na razie puste
        Label l = new Label(">><<");

        // NOWE - tworzymy obsługę zdarzenia - zmieniamy komunikat w polu l
        EventHandler<ActionEvent> event = e -> l.setText("Welcome to JavaFX Application!");

        // NOWE - łączymy zdarzenie z naszym przyciskiem
        b.setOnAction(event);

        // łączymy przycisk z głównym węzłem (i pole tekstowe też)
        r.getChildren().add(b);
        r.getChildren().add(l);

        // Przygotowanie sceny
        // Tworzymy obiekt Scene - w konstruktorze rootem jest nasz węzeł
        Scene sc = new Scene(r, 320, 240);

        // Nasz występ
        // Dodajemy scenę Scene do sceny Stage :)
        primaryStage.setScene(sc);
        primaryStage.show();
    }
}
