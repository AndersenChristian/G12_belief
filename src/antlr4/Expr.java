package antlr4;

public abstract class Expr implements AST {
    private Expr expr;

    public Expr() {}

    public Expr(Expr expr) {
        this.expr = expr;
    }

    abstract public Expr convertToCNF(Environment env);

    abstract public String toInputFormat();

    public Expr deMorgan(Environment env) {
        return expr.deMorgan(env);
    }

    public Expr lawOfDistribution(Environment env, Expr left) { return expr.lawOfDistribution(env, left); }

    @Override
    public String toString(){
        return this.toInputFormat();
    }
}
