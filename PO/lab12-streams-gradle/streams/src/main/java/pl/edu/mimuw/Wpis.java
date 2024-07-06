package pl.edu.mimuw;

record Wpis(String nazwa, int wartość){
    @Override
    public String toString() {
        return nazwa + ": " + wartość;
    }
}
