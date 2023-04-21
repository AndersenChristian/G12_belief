package Model;

public enum Operator {
    OR("|"),
    AND("&"),
    NOT("~"),
    IFF("<=>"),
    IMP("=>");

    private final String SYMBOL;

    Operator(String s) {
        this.SYMBOL = s;
    }

    public String getOperator() {
        return this.SYMBOL;
    }
}