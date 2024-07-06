package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Solution2Caller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        String path1 = "https://programmerhumor.io/wp-content/uploads/2022/09/programmerhumor-io-programming-memes-53381476c613c68-758x771.jpg";
        String path2 = "https://programmerhumor.io/wp-content/uploads/2022/10/programmerhumor-io-programming-memes-bb741d1fac025a7.png";
        String path3 = "https://preview.redd.it/never-been-so-offended-like-this-v0-vunc47sytb1b1.jpg?auto=webp&v=enabled&s=2651978006d90d7487e2b7390af904fdb8823ba9";
        String path4 = "https://programmerhumor.io/wp-content/uploads/2021/06/programmerhumor-io-programming-memes-9f68688e73add27-758x389.jpg";
        String path5 = "https://pbs.twimg.com/media/CJGHigXWwAASU2Y?format=png&name=small";
        Image image1 = new Image(path1);
        Image[] imageTab = new Image[]{image1, new Image(path2), new Image(path3),new Image(path4), new Image(path5)};


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("solution2final.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //NOWE - przekazujemy dane do kontrolera
        Solution2Controller controller = fxmlLoader.getController();
        controller.initData(imageTab);

        stage.setTitle("Przeglądarka memów 2");
        stage.setScene(scene);
        stage.show();
    }
}
