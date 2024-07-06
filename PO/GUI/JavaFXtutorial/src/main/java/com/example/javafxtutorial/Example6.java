package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Example6 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double width = 1296;
        double heigth = 959;
        // Przygotujemy obrazek
        String path = "https://static.independent.co.uk/2021/03/26/15/MDRUM_Shark_Eating_Croc-6.jpg?quality=75&width=1368&auto=webp";
        Image image = new Image(path, width, heigth,false,true);
        // Przygotujemy tło
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background welcomeAustralia = new Background(myBI);
        // Przygotowujemy jakiś węzeł/komponent obrazka
        VBox content = new VBox();
        content.setBackground(welcomeAustralia);

        // Przygotowanie sceny
        // Tworzymy obiekt Scene - w konstruktorze rootem jest nasz węzeł
        Scene scene = new Scene(content, width, heigth);

        // Nasz występ
        // Dodajemy scenę Scene do sceny Stage :)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to Australia"); // ustawiamy tytuł
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
