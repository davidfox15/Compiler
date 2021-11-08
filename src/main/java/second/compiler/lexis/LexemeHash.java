package second.compiler.lexis;

import second.compiler.hash.Hashable;

public class LexemeHash implements Hashable {

    private final Lexeme lexeme;

    private String about;

    public LexemeHash(Lexeme lexeme, String about) {
        this.lexeme = lexeme;
        this.about = about;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStringElement() {
        return lexeme.getLexeme();
    }
}
