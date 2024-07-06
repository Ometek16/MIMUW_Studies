package pl.edu.mimuw;

public class Game {

    private Player[] players;
    private DealerPlayer dealer;
    public Deck deck;

    public Game(Player[] players) {
        this.players = players;
        deck = new Deck();
        dealer = new DealerPlayer("Dealer");

        System.out.println("===== ===== ===== Players ===== ===== =====\n");
        for (int i = 0; i < players.length; i++) 
            System.out.println("\t" + (i+1) + ".\t" + players[i].getName() + "\t" + players[i].getClass().getSimpleName());
    }

    private void play(Player player) {
        if (player.getStatus() == "BUSTED")
            return;
        System.out.println("\t" + player.getName() + "'s turn. Currently has " + player.getPipCount() + " points.");
        
        while (player.getStatus() != "BUSTED" && player.wantsCard()) {
                System.out.print("\t\tHIT");
                player.drawCard(deck.giveCard());
                System.out.println(" -> now has " + player.getPipCount() + " points.");
        }

        if (player.getStatus() == "BUSTED")
            System.out.println("\t\tBUSTED\n");
        else 
            System.out.println("\t\tSTAY\n");
    }

    public void startGame() {
        System.out.println("\n===== ===== ===== Deal ===== ===== =====\n");
        for (var player : players) {
            player.drawCard(deck.giveCard());
            player.drawCard(deck.giveCard());
            System.out.println("\t" + player.getName() + " has " + player.getPipCount() + " points.\t" + player.getStatus());
        }

        dealer.drawCard(deck.giveCard());
        dealer.drawSecretCard(deck.giveCard());
        System.out.println("\n\t" + dealer.getName() + " has " + dealer.getPipCount() + " points visible.\t" + dealer.getStatus());

        System.out.println("\n===== ===== ===== Player Rounds ===== ===== ====\n");
        for (var player : players) {
            if (player.getStatus() == "BUSTED")
                continue;

            play(player);
        }

        System.out.println("===== ===== ===== Dealer Round ===== ===== =====\n");
        dealer.revealSecretCard();
        System.out.println("\tDealer revealed his other card. Now has " + dealer.getPipCount() + " points.\t" + dealer.getStatus() +"\n");
        play(dealer);

        System.out.println("===== ===== ===== Game Results ===== ===== =====\n");

        System.out.println("\tDealer has " + dealer.getPipCount() + " points.\t" + dealer.getStatus() + "\n");

        for (var player : players) {
            if (player.getStatus() == "BUSTED")
                ;
            else if (dealer.getStatus() == "BUSTED" || dealer.getPipCount() < player.getPipCount())
                player.setStatus("WON");
            else if (dealer.getPipCount() == player.getPipCount())
                player.setStatus("DRAW");
            else 
                player.setStatus("LOST");

            System.out.println("\t" + player.getName() + " has " + player.getPipCount() + " points.\t" + player.getStatus());
        }
    }
}
