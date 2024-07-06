package pl.edu.mimuw;

public class LimitedPlayer extends Player {

    private int limit;

    public LimitedPlayer(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }

    public boolean wantsCard() {
        return getPipCount() < limit;
    }
}
