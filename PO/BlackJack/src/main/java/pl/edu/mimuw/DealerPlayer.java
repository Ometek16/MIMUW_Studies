package pl.edu.mimuw;

public class DealerPlayer extends LimitedPlayer {
    private Card secretCard;

    public DealerPlayer(String name) {
        super(name, 17);
    }

    public void drawSecretCard(Card secretCard) {
        this.secretCard = secretCard;
    }

    public void revealSecretCard() {
        this.drawCard(secretCard);
    }
}
