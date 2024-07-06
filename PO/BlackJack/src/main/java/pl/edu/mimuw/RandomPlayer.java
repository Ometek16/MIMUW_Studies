package pl.edu.mimuw;

import java.util.Random;

public class RandomPlayer extends Player {

    Random random;

    public RandomPlayer(String name) {
        this.name = name;
        this.random = new Random();
    }

    public RandomPlayer(String name, Random random) {
        this.name = name;
        this.random = random;
    }

    public boolean wantsCard() {
        return (random.nextInt(2) == 0);
    }
}
