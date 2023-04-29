package Model;

public enum Operator {
    OR("|"),
    AND("&"),
    NOT("~"),
    IFF("=>"),
    IMP("<=>");

    private final String OPERATOR;

    Operator(String s) {
        this.OPERATOR = s;
    }

    public String getOperator() {
        return this.OPERATOR;
    }
}