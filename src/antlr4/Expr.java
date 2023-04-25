package antlr4;

public abstract class Expr extends AST {
    abstract public Expr CNFConverter(Environment env);
    abstract public String toInputFormat();
}
