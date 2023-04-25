package antlr4;

public abstract class Expr extends AST {
    abstract public Expr convertToCNF();
    abstract public String toInputFormat();
}
