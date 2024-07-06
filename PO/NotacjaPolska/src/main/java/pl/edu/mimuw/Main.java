package pl.edu.mimuw;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        try {
            System.out.println(RPNEvaluator.evaluate(string));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
