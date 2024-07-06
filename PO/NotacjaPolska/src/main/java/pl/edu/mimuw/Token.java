package pl.edu.mimuw;

import java.util.Deque;

public interface Token {

    public void evaluate(Deque<Integer> stack);
}
