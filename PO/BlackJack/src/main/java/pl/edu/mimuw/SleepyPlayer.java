package pl.edu.mimuw;

public class SleepyPlayer extends Player {
    
    public SleepyPlayer(String name) {
        this.name = name;
    }

    public boolean wantsCard() {
        return false;
    }
}
