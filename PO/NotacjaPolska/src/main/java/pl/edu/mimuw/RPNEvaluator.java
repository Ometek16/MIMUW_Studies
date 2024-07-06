package pl.edu.mimuw;

import java.util.ArrayDeque;
import java.util.Deque;

public class RPNEvaluator {

    public static int evaluate(String string) throws Exception {
        Token[] tokens;
        
        // get Tokens
        try {
            tokens = Parser.parse(string);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // check if solvable
        int numberCounter = 0;
        int operatorCounter = 0;

        for (var token : tokens){
            if (token instanceof NumberToken)
                numberCounter++;
            else 
                operatorCounter++;
            
            if (numberCounter < operatorCounter + 1)
                throw new IllegalArgumentException("Nie rozwiązywalne :c");
        }

        if (numberCounter != operatorCounter + 1)
            throw new IllegalArgumentException("Nie rozwiązywalne :c");

        // solve :D
        Deque<Integer> stack = new ArrayDeque<>();

        for (var token : tokens) {
            try {
                token.evaluate(stack);
            }
            catch (ArithmeticException e) {
                throw e;
            }
        }

        return stack.removeFirst();
    }
}
