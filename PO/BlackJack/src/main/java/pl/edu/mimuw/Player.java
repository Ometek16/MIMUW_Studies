package pl.edu.mimuw;

abstract public class Player {

    private int pipCount = 0;
    protected String name;
    protected String status = "";

    protected int getPipCount() {
        return this.pipCount;
    }

    protected void drawCard(Card card) {
        this.pipCount += card.value;
        if (this.pipCount > 21)
            this.setStatus("BUSTED");
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    abstract public boolean wantsCard();
}
