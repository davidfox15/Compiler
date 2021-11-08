package second.compiler.exceptions;

public class MatcherCompileException extends Exception {
    public MatcherCompileException(String line) {
        super("Error parse in \"" + line + "\"");
    }
}
