package second.compiler.codegenerator;

import java.util.LinkedList;
import java.util.List;

public class CodeGeneratorOutput {
    private List<CodeCommand> operatorsAssembler;

    public CodeGeneratorOutput() {
        operatorsAssembler = new LinkedList<>();
    }

    public List<CodeCommand> getOperatorsAssembler() {
        return operatorsAssembler;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        operatorsAssembler.forEach(op -> sb.append(op.toString()).append("\n"));
        return sb.toString();
    }
}
