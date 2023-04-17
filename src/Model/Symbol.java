package Model;

public enum Symbol {
    OR("|"),
    AND("&"),
    NOT("~"),
    IFF("=>"),
    IMP("<=>");

    private final String SYMBOL;

    Symbol(String s) {
        this.SYMBOL = s;
    }

    public String getSYMBOL() {
        return this.SYMBOL;
    }
}