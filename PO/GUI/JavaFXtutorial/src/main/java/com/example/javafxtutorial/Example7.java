package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Example7 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double width = 1296;
        double heigth = 959;
        // Przygotujemy obrazek
        String path1 = "https://programmerhumor.io/wp-content/uploads/2022/09/programmerhumor-io-programming-memes-53381476c613c68-758x771.jpg";
        Image image1 = new Image(path1, width, heigth,false,true);
        // NOWE - widok obrazka
        ImageView imageView = new ImageView();
        imageView.setImage(image1);

        // Przygotowujemy jakiś węzeł/komponent obrazka
        VBox content = new VBox();
        // NOWE - jak dodać element do węzła głównego
        content.getChildren().add(imageView);

        // Przygotowanie sceny
        // Tworzymy obiekt Scene - w konstruktorze rootem jest nasz węzeł
        Scene scene = new Scene(content, width, heigth);

        // Nasz występ
        // Dodajemy scenę Scene do sceny Stage :)
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
