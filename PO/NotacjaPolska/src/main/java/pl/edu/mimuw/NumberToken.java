package pl.edu.mimuw;

import java.util.Deque;

public class NumberToken implements Token {

    private int value;
    
    public NumberToken(int value) {
        this.value = value;
    }

    public void evaluate(Deque<Integer> stack) {
        stack.addFirst(this.value);
    }
}
