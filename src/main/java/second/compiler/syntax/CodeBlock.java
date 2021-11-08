package second.compiler.syntax;
import second.compiler.lexis.Lexeme;
import second.compiler.tree.Tree;

public class CodeBlock {
    private Tree tree;
    private Lexeme lexeme;

    public boolean isLexeme() {
        return lexeme != null && tree == null;
    }

    public boolean isTree() {
        return tree != null && lexeme == null;
    }

    public CodeBlock() {
    }

    public CodeBlock(Tree tree) {
        this.tree = tree;
    }

    public CodeBlock(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        if (lexeme != null) {
            lexeme = null;
        }
        this.tree = tree;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        if (tree != null) {
            tree = null;
        }
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        if (isTree())
            return "CodeBlock{" +
                "tree=" + tree.getRoot().getValue() +
                '}';
        else
            return "CodeBlock{" +
                    "lexeme=" + lexeme.getLexeme() +
                    '}';
    }
}
