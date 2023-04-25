package antlr4;

public abstract class Expr extends AST {
    abstract public Expr CNFConverter();
    abstract public String toInputFormat();
}
