package second.compiler.codegenerator;

/**
 * @author Максим Пшибло
 */
public class CodeCommand {
    private String cmd;
    private String arg;
    private boolean isMark;

    public CodeCommand(String cmd, String arg) {
        this.cmd = cmd;
        this.arg = arg;
        isMark = false;
    }

    public CodeCommand() {
        isMark = false;
    }

    boolean containArg(String arg) {
        return this.arg.contains(arg);
    }

    boolean equalsArg(CodeCommand codeCommand) {
        return codeCommand.getArg().equals(arg);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CodeCommand) {
            CodeCommand objCodeCommand = (CodeCommand) obj;
            return cmd.equals(objCodeCommand.cmd) && arg.equals(objCodeCommand.arg);
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return isMark ? arg + ":" : cmd + " " + arg + ";";
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public boolean isMark() {
        return isMark;
    }

    public void swapArg(CodeCommand codeCommand) {
        String tmp = codeCommand.getArg();
        codeCommand.setArg(arg);
        arg = tmp;
    }
}
