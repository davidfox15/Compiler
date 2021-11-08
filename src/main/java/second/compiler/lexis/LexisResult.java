package second.compiler.lexis;
import second.compiler.hash.HashTable;

import java.util.ArrayList;
import java.util.List;

public class LexisResult {

    private final HashTable<LexemeHash> hashTable;
    private static int size = 50;
    private final List<Lexeme> lexemes;
    private LexemeHash lastVal;
    private LexemeHash prev;

    public LexisResult() {
        this.lexemes = new ArrayList<>();
        hashTable = new HashTable<>(size);
    }

    public void addNextLexeme(Lexeme lexeme, About about) {

        if (lexeme.getLexeme().equals("=")) {
            lastVal = prev;
        }
        prev = hashTable.addElement(new LexemeHash(lexeme, about.getVal()));
        lexemes.add(lexeme);

//        Lexeme findLexeme = lexemes.stream()
//                .filter(lexeme1 -> lexeme1.getLexeme().equals(lexeme.getLexeme()))
//                .findFirst()
//                .orElse(null);
//        if (findLexeme != null) {
//            lexeme.setPointer(findLexeme.getPointer());
//        } else {
//            if (lexeme.isSign()) {
//                lexeme.setPointer(0);
//                hashTable.addElement(new LexemeHash(lexeme, about.getVal()));
//            } else {
//                lexeme.setPointer(count);
//                tableNames.add(new LexemeHash(lexeme.getLexeme(), count, about.getVal()));
//                count++;
//            }
//        }

    }

    public List<LexemeHash> getHashTableAsList() {
        return hashTable.toList();
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }


    public void changeLastValue(About about) {
        lastVal.setAbout(about.getVal());
    }

    public String getLexemesString() {
        StringBuilder sb = new StringBuilder();
        for (Lexeme lexeme : lexemes) {
            sb.append(lexeme.getLexeme());
        }


//        for (Lexeme lexeme : lexemes) {
//                if (lexeme.isSign()) {
//                    sb.append(lexeme.getLexeme());
//                } else {
//                    sb.append("<lex ").append(lexeme.getLexeme()).append(">");
//                }
//        }
        return sb.toString();
    }
}
