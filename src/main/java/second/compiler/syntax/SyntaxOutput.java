package second.compiler.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Максим Пшибло
 */
public class SyntaxOutput {
    private List<CodeBlock> codeBlocks;


    public SyntaxOutput() {
        codeBlocks = new ArrayList<>();
    }

    public SyntaxOutput(List<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }

    public List<CodeBlock> getCodeBlocks() {
        return codeBlocks;
    }

    public void setCodeBlocks(List<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }
}
