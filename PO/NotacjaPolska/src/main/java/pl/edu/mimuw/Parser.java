package pl.edu.mimuw;

public class Parser {

    private static final String[] legalOperators = new String[] {"+", "-", "*", "/"};
    private Parser(){}

    private static boolean contains(String element) {
        for (var operator : legalOperators)
            if (element.equals(operator))
                return true;

        return false;
    }

    public static Token[] parse(String string) throws IllegalArgumentException {
        String[] elements = string.split(" ");
        Token[] tokens = new Token[elements.length];

        for (int i = 0; i < elements.length; i++) {
            try {
                int value = Integer.parseInt(elements[i]);
                tokens[i] = new NumberToken(value);
            } 
            catch (IllegalArgumentException e) {
                if (contains(elements[i]))
                    tokens[i] = new OperatorToken(elements[i]);
                else
                    throw new IllegalArgumentException("Niewłaściwy znak :/");
            }
        }

        return tokens;
    }
}
