package pl.edu.mimuw;

public class IntHalfPair<T1> extends Pair<Integer, T1> {
    int first;
    T1 second;

    public IntHalfPair(int first, T1 second) {
        super(first, second);
    }
}
