package second.compiler.lexis;

enum About {
    INT("Переменная типа int"),
    FLOAT("Переменная типа float"),
    DOUBLE("Переменная типа double"),
    CONST_INT("Константа типа int"),
    CONST_FLOAT("Константа типа FLOAT"),
    CONST_DOUBLE("Константа типа DOUBLE"),
    OPERATOR("Оператор"),
    BRACKET("Скобка ()"),
    BRACKET_REGION("{}"),
    END_OPERATOR(";"),
    CONST_16("Шестнадцатиричная константа"),
    VAL_16("Шестнадцатиричная переменная"),
    DO("do"),
    WHILE("while"),
    BOOLEAN_OPERATORS("Условные операторы");

    private final String val;

    About(String s) {
        val = s;
    }

    public String getVal() {
        return val;
    }
}
