package pl.edu.mimuw;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Polynomial polynomial = new Polynomial(new double[]{2, 0.5, 3, 0.125});

        System.out.println(polynomial.multiply(polynomial).toString());
    }
}
