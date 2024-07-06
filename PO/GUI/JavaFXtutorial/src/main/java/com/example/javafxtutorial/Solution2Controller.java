package com.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Solution2Controller {

    // NOWE - dodajemy atrybuty
    private Image[] images;
    private int counter;
    private final int startowy = 1;
    @FXML
    private ImageView obrazek;

    @FXML
    private Button przod;

    @FXML
    private Button tyl;

    // NOWE - uzupełniamy impementację
    @FXML
    void idzDoPrzodu(ActionEvent event) {
        counter++;
        obrazek.setImage(images[counter % images.length]);
    }

    @FXML
    void idzDoTylu(ActionEvent event) {
        counter--;
        if (counter < 0) counter = counter + images.length;  // in Java modulo can be < 0
        obrazek.setImage(images[counter % images.length]);
    }

    // NOWE odbieramy dane od Wywoływacza
    public void initData(Image[] imageTab) {

        images = imageTab;
        counter = startowy % images.length;
        obrazek.setImage(images[startowy]); // jeśli chcemy mieć obrazek startowy
    }
}
