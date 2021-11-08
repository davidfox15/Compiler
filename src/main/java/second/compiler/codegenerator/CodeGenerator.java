package second.compiler.codegenerator;

import second.compiler.syntax.CodeBlock;
import second.compiler.syntax.SyntaxOutput;
import second.compiler.tree.Tree;

import java.util.LinkedList;
import java.util.List;

public class CodeGenerator {
    private final SyntaxOutput syntaxOutput;
    private int l;

    public CodeGenerator(SyntaxOutput syntaxOutput) {
        this.syntaxOutput = syntaxOutput;
        l = 0;
    }

    public CodeGeneratorOutput generateCodeDoWhile() {
        CodeGeneratorOutput codeGeneratorOutput = new CodeGeneratorOutput();
        CodeCommand loop = new CodeCommand();
        loop.setArg("loop");
        loop.setMark(true);
        codeGeneratorOutput.getOperatorsAssembler().add(loop);
        for (CodeBlock codeBlock : syntaxOutput.getCodeBlocks()) {
            if (codeBlock.isTree()) {
                codeGeneratorOutput.getOperatorsAssembler().addAll(generateCodeExpression(codeBlock.getTree()));
            }
        }
        return codeGeneratorOutput;
    }


    private List<CodeCommand> generateCodeExpression(Tree tree) {
        String code = tree.getStringForCodeGenerator(l + 1);
        l = tree.getL();
        List<CodeCommand> assCode = new LinkedList<>();
        for (String s : code.split(";")) {
            String[] cmdAndArg = s.trim().split(" ");
            assCode.add(new CodeCommand(cmdAndArg[0], cmdAndArg[1]));
        }
        return assCode;
    }
}
