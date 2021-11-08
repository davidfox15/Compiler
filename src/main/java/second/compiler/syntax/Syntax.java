package second.compiler.syntax;

import second.compiler.exceptions.SyntaxException;
import second.compiler.lexis.Lexeme;
import second.compiler.lexis.LexisResult;
import second.compiler.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class Syntax {

    private LexisResult lexisResult;

    public Syntax(LexisResult lexisResult) {
        this.lexisResult = lexisResult;
    }

    public SyntaxOutput syntaxAnalysisDoWhile() throws SyntaxException {
        //Только для одного do{...}while
        //{}
        SyntaxOutput syntaxOutput = new SyntaxOutput();

        List<Lexeme> whileBefore = new ArrayList<>();
        List<Lexeme> expressionsBlock = new ArrayList<>();
        List<Lexeme> whileAfter = new ArrayList<>();
        CodeBlock booleanBlock = null;
        boolean go = false;
        //do{.....}while
        List<Lexeme> lexemes = lexisResult.getLexemes();
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme lexeme = lexemes.get(i);
            if (go && lexeme.getLexeme().equals("}")) {
                go = false;
                whileAfter.add(lexeme);
                whileAfter.add(lexemes.get(i+1));
                booleanBlock = analysisBoolean(lexemes.subList(i + 3, lexemes.size() - 1));
                break;
            }
            if (go) {
                expressionsBlock.add(lexeme);
            }
            if (lexeme.getLexeme().equals("{")) {
                go = true;
                for (int k = 0; k <= i; k++) {
                    whileBefore.add(lexemes.get(k));
                }
            }
        }


        whileBefore.forEach(lexeme -> {
            syntaxOutput.getCodeBlocks().add(new CodeBlock(lexeme));
        });


        //n=x+a; in {....}
        List<List<Lexeme>> expressions = new ArrayList<>();
        expressions.add(new ArrayList<>());
        for (Lexeme lexeme : expressionsBlock) {
            if (lexeme.getLexeme().equals(";")) {
                expressions.add(new ArrayList<>());
                continue;
            }
            expressions.get(expressions.size() - 1).add(lexeme);
        }
        for (List<Lexeme> expression : expressions) {
            if (expression.size() != 0)
                syntaxOutput.getCodeBlocks().add(new CodeBlock(syntaxAnalysisExpression(expression)));
        }

        whileAfter.forEach(lexeme -> {
            syntaxOutput.getCodeBlocks().add(new CodeBlock(lexeme));
        });

        if (booleanBlock != null) {
            syntaxOutput.getCodeBlocks().add(booleanBlock);
        }
        return syntaxOutput;
    }

    private Tree syntaxAnalysisExpression(List<Lexeme> expression) throws SyntaxException {
        int scob = 0;
        for (int i = 0; i < expression.size(); i++) {
            Lexeme lexeme = expression.get(i);
            if (lexeme.isOperator()) {
                if (i == 0 || i == expression.size() - 1) {
                    throw new SyntaxException(expression.get(i).getLexeme(), lexemesToString(expression));
                }
            }
            if (i == 1 && expression.get(i).getLexeme().equals(";")) {
                if (expression.size() != 2)
                    throw new SyntaxException(expression.get(i).getLexeme(), lexemesToString(expression));
            } else if (i > 0) {
                if (lexeme.getLexeme().equals("=")){
                    if (leftIsOperator(expression, i))
                        throw new SyntaxException(expression.get(i).getLexeme(), lexemesToString(expression));
                }
                if (lexeme.isOperator()) {
                    if (!expression.get(i-1).isVal() && !expression.get(i-1).isNumber() && !expression.get(i-1).getLexeme().equals(")"))
                        throw new SyntaxException(expression.get(i-1).getLexeme() + lexeme.getLexeme(),
                                lexemesToString(expression));
                    if (i >= expression.size() - 1 || (!expression.get(i+1).isVal()
                            && !expression.get(i+1).isNumber() && !expression.get(i+1).getLexeme().equals("(")))
                        throw new SyntaxException(expression.get(i).getLexeme() + expression.get(i+1).getLexeme(),
                                lexemesToString(expression));
                }
                else if (lexeme.getLexeme().equals("(")) {
                    scob++;
                }
                else if (lexeme.getLexeme().equals(")")) {
                    scob--;
                }
                else if (lexeme.isNumber() || lexeme.isVal()) {
                    if (!expression.get(i-1).isOperator() && !expression.get(i-1).getLexeme().equals("("))
                        throw new SyntaxException(expression.get(i-1).getLexeme() + expression.get(i).getLexeme(),
                                lexemesToString(expression));
                    if (i < expression.size() - 1)
                        if (!expression.get(i+1).isOperator() && !expression.get(i+1).getLexeme().equals(")"))
                            throw new SyntaxException(expression.get(i).getLexeme() + expression.get(i+1).getLexeme(),
                                    lexemesToString(expression));
                }
            }
        }

        if (scob != 0) {
            throw new SyntaxException("()",  lexemesToString(expression));
        }

        Tree tree = new Tree().getTreeForExp(expression);
        tree.setCmp(false);
        return tree;

    }


    private CodeBlock analysisBoolean(List<Lexeme> lexemes) {
        Tree tree = new Tree().getTreeForExp(lexemes);
        tree.setCmp(true);
        return new CodeBlock(tree);
    }

    private String lexemesToString(List<Lexeme> lexemes) {
        StringBuilder sb = new StringBuilder();
        lexemes.forEach(lexeme -> sb.append(lexeme.getLexeme()));
        return sb.toString();
    }

    private boolean leftIsOperator(List<Lexeme> lexemes, int i) {
        for (int j = i; j > 0; j--) {
            if (lexemes.get(j).isOperator() && !lexemes.get(j).getLexeme().equals("=")) {
                return true;
            }
        }
        return false;
    }



}
