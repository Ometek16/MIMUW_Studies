package pl.edu.mimuw;

import java.util.Random;

public class Deck {

    private Card[] deck;
    private int currentIndex;

    public Deck() {
        currentIndex = 0;
        deck = new Card[52];
        Card[] cards = Card.values();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < cards.length; j++) {
                deck[i * 13 + j] = cards[j];
            }
        }

        Random random = new Random();
        this.shuffle(random);
    }

    public Deck(Random random) {
        currentIndex = 0;
        deck = new Card[52];
        Card[] cards = Card.values();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < cards.length; j++) {
                deck[i * 13 + j] = cards[j];
            }
        }

        this.shuffle(random);
    }

    public Card giveCard() {
        return deck[currentIndex++];
    }

    public void printDeck() {
        for (int i = 0; i < 52; i++)
            System.out.print(deck[i].value + " ");
        System.out.println();
    }

    public void shuffle(Random random) {
        int currSize = 52;

        while (currSize != 0) {
            int randomPos = random.nextInt(currSize);
            
            Card tmp = deck[currSize - 1];
            deck[currSize - 1] = deck[randomPos];
            deck[randomPos] = tmp;

            currSize -= 1;
        }
    }
}
