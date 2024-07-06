package pl.edu.mimuw;

public class Main {

    public static void main(String[] args) {
        Player[] players = new Player[6];

        players[0] = new LimitedPlayer("Alice", 16);
        players[1] = new LimitedPlayer("Bob", 17);
        players[2] = new LimitedPlayer("Chris", 20);
        players[3] = new CautiousPlayer("Dan");
        players[4] = new RandomPlayer("Emily");
        players[5] = new SleepyPlayer("Luna");

        Game game = new Game(players);

        game.startGame();
    }
}

/* 
    Gra Black Jack, ale bez:
        - split
        - double down
        - insurance
        - BlackJack (3:2)
        - jakiekolwiek wypłacania nagród za rundę (przeciez to nie hazard lol)
        - Dealera podglądającego kartę gdy dostanie asa
 */
