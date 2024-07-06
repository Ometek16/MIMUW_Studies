package pl.edu.mimuw;

public class CustomAutoCloseable implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("Zamykam lol");
    }
}
