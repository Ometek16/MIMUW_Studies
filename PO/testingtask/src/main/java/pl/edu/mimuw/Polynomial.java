package pl.edu.mimuw;

public class Polynomial {
    double[] coefficients;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        double[] result = new double[Math.max(coefficients.length, other.coefficients.length)];

        for (int i = 0; i < coefficients.length; i++) {
            result[i] += coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            result[i] += other.coefficients[i];
        }

        return new Polynomial(result);
    }

    public Polynomial subtract(Polynomial other) {
        double[] result = new double[Math.max(coefficients.length, other.coefficients.length)];

        for (int i = 0; i < coefficients.length; i++) {
            result[i] += coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            result[i] -= other.coefficients[i];
        }

        return new Polynomial(result);
    }

    public Polynomial multiply(Polynomial other) {
        double[] result = new double[coefficients.length + other.coefficients.length - 1];

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                result[i + j] += coefficients[i] * other.coefficients[j];
            }
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }

        return result;
    }

    public Polynomial derivative() {
        if (coefficients.length <= 1) {
            return new Polynomial(new double[]{0});
        }

        double[] derivativeCoefficients = new double[coefficients.length - 1];

        for (int i = 1; i < coefficients.length; i++) {
            derivativeCoefficients[i - 1] = coefficients[i] * i;
        }

        return new Polynomial(derivativeCoefficients);
    }

    public Polynomial integral() {
        double[] integralCoefficients = new double[coefficients.length + 1];

        for (int i = 0; i < coefficients.length; i++) {
            integralCoefficients[i + 1] = coefficients[i] / (i + 1);
        }

        return new Polynomial(integralCoefficients);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] == 0) continue;
            if (sb.length() > 0 && coefficients[i] > 0) {
                sb.append(" + ");
            }
            if (i == 0 || coefficients[i] != 1) {
                sb.append(coefficients[i]);
            }
            if (i > 0) {
                sb.append("x");
            }
            if (i > 1) {
                sb.append("^").append(i);
            }
        }

        return sb.toString();
    }
}
