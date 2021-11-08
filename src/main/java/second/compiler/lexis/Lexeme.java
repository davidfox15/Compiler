package second.compiler.lexis;

public class Lexeme {

    private final boolean isVal;

    private final boolean isSign;

    private final boolean isOperator;

    private final boolean isNumber;

    private final String lexeme;

    private Lexeme(String lexeme, boolean isSign, boolean isNumber, boolean isVal, boolean isOperator) {
        this.isVal = isVal;
        this.isSign = isSign;
        this.isNumber = isNumber;
        this.lexeme = lexeme;
        this.isOperator = isOperator;
    }

    public static Lexeme operator(String lexeme) {
        return new Lexeme(lexeme, false, false, false, true);
    }

    public static Lexeme sign(String lexeme) {
        return new Lexeme(lexeme, true,false, false, false);
    }

    public static Lexeme number(String lexeme) {
        return new Lexeme(lexeme, false, true, false, false);
    }

    public static Lexeme val(String lexeme) {
        return new Lexeme(lexeme, false, false, true, false);
    }

    public String getLexeme() {
        return lexeme;
    }

    public boolean isSign() {
        return isSign;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public boolean isVal() {
        return isVal;
    }

    public boolean isOperator() {
        return isOperator;
    }

    @Override
    public String toString() {
        return "< " + lexeme + " >";
    }


}
