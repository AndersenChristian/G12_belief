package antlr4;

public abstract class Expr implements AST {
    private Expr expr;

    public Expr() {}

    public Expr(Expr expr) {
        this.expr = expr;
    }

    abstract public Expr convertToCNF();

    abstract public String toInputFormat();

    public Expr deMorgan() {
        return expr.deMorgan();
    }

    public Expr lawOfDistribution(Expr left) { return expr.lawOfDistribution(left); }

    @Override
    public String toString(){
        return this.toInputFormat();
    }
}
