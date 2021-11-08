package second.compiler.lexis;

import second.compiler.exceptions.MatcherCompileException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexis {

    public static LexisResult analysisExpression(String input) throws MatcherCompileException {
        return analysisExpression_pr(input, null);
    }

    public static LexisResult analysisExpression(String input, LexisResult ls) throws MatcherCompileException {
        return analysisExpression_pr(input, ls);
    }

    public static LexisResult analysisBoolean(String input) throws MatcherCompileException {
        return analysisBoolean_pr(input, null);
    }

    public static LexisResult analysisBoolean(String input, LexisResult ls) throws MatcherCompileException {
        return analysisBoolean_pr(input, ls);
    }


    private static LexisResult analysisExpression_pr(String input, LexisResult ls) throws MatcherCompileException {
        System.out.println("input = " + input);
        LexisResult lexisResult = Objects.requireNonNullElseGet(ls, LexisResult::new);

        Pattern pattern = Pattern.compile("(\\w+)(;|(=)((.)+))");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            System.out.println(matcher.group(0));
            lexisResult.addNextLexeme(Lexeme.val(matcher.group(1)), About.INT);
            if (matcher.group(2).equals(";")) {
                lexisResult.addNextLexeme(Lexeme.sign(matcher.group(2)), About.END_OPERATOR);
            } else {
                lexisResult.addNextLexeme(Lexeme.operator(matcher.group(3)),  About.OPERATOR);
                Pattern p = Pattern.compile("(\\w+\\.?\\d*)|([+\\-*/])|[()]|;|=");
                Matcher m = p.matcher(matcher.group(4));
                while (m.find())
                {
                    String str = m.group();
                    if (!analysisNumberOrVal(str,lexisResult, true)) {
                        if (str.matches("[+\\-*/]")) {
                            lexisResult.addNextLexeme(Lexeme.operator(str), About.OPERATOR);
                        } else if (str.matches("[()]")) {
                            lexisResult.addNextLexeme(Lexeme.sign(str), About.BRACKET);
                        } else if (str.equals(";")) {
                            lexisResult.addNextLexeme(Lexeme.sign(str), About.END_OPERATOR);
                        } else if (str.equals("=")) {
                            lexisResult.addNextLexeme(Lexeme.operator(str), About.OPERATOR);
                        } else {
                            throw new MatcherCompileException(str);
                        }
                    }
                }
            }
        } else {
            throw new MatcherCompileException(input);
        }
        return lexisResult;
    }


    private static LexisResult analysisBoolean_pr(String input, LexisResult ls) throws MatcherCompileException {
        LexisResult lexisResult = Objects.requireNonNullElseGet(ls, LexisResult::new);

        Pattern booleanPattern = Pattern.compile("(\\w+)(<=|>=|==|>|<|!=)(\\w+)");
        Matcher booleanMatcher = booleanPattern.matcher(input);
        if (booleanMatcher.matches()) {
            String a = booleanMatcher.group(1);
            String sign = booleanMatcher.group(2);
            String b = booleanMatcher.group(3);
            if (!analysisNumberOrVal(a,lexisResult, false)) {
                throw new MatcherCompileException(input + " in " + a);
            }
            lexisResult.addNextLexeme(Lexeme.sign(sign), About.BOOLEAN_OPERATORS);
            if (!analysisNumberOrVal(b, lexisResult, false)) {
                throw new MatcherCompileException(input + " in " + b);
            }


        } else {
            throw new MatcherCompileException(input);
        }

        return lexisResult;
    }

    //TRUE - это число все таки, false - шо это???
    private static boolean analysisNumberOrVal(String input, LexisResult ls, boolean expression) {
        if (input.matches("\\d+[a-f]+"))
        {
            ls.addNextLexeme(Lexeme.number(input), About.CONST_16);
            if (expression)
                ls.changeLastValue(About.VAL_16);
        }
        else if (input.matches("\\d+\\.?\\d*")) {
            if (input.contains(".")) {
                ls.addNextLexeme(Lexeme.number(input), About.CONST_FLOAT);
                if (expression)
                    ls.changeLastValue(About.FLOAT);
            } else {
                ls.addNextLexeme(Lexeme.number(input), About.CONST_INT);
            }
        }
        else if (input.matches("[A-Za-z]+\\w*")) {
            ls.addNextLexeme(Lexeme.val(input), About.INT);
        }
        else {
            return false;
        }
        return true;
    }

    public static LexisResult analysisDoWhile(String input) throws MatcherCompileException {

        LexisResult lexisResult = new LexisResult();
        //input.replace("\n", "");
        input = input.replaceAll("[\n\r\t ]", "");
        Pattern doWhile = Pattern.compile("(do)(\\{)(.*?)(\\})(while)(\\()(.*?)(\\))");
        Matcher doWhileMatcher = doWhile.matcher(input);
        boolean ch = true;
        int start = 0;
        while(doWhileMatcher.find(start)) {
            System.out.println(doWhileMatcher.group(0));
            ch = false;
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(1)), About.DO);
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(2)), About.BRACKET_REGION);

            String[] expressions = doWhileMatcher.group(3).split(";");
            for (int i = 0; i < expressions.length; i++) {
                if (i != expressions.length - 1
                        || String.valueOf(doWhileMatcher.group(3).toCharArray()[doWhileMatcher.group(3).length() - 1]).equals(";")) {
                    lexisResult = analysisExpression(expressions[i] + ";", lexisResult);
                } else {
                    lexisResult = analysisExpression(expressions[i], lexisResult);
                }
            }
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(4)), About.BRACKET_REGION);
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(5)), About.WHILE);
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(6)), About.BRACKET);
            lexisResult = analysisBoolean(doWhileMatcher.group(7), lexisResult);
            lexisResult.addNextLexeme(Lexeme.sign(doWhileMatcher.group(8)), About.BRACKET);
            start = doWhileMatcher.end();
        }
        if (ch) {
            throw new MatcherCompileException(input);
        }
        return lexisResult;
    }
}
