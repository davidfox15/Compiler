package second.compiler.exceptions;

public class SyntaxException extends Exception {
    public SyntaxException(String line) {
        super("Error syntax analysis in \"" + line + "\"");
    }

    public SyntaxException(String pos, String line) {
        super("Error syntax analysis \"" + pos + "\" in \"" + line + "\"");
    }
}
