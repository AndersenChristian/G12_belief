package antlr4;

public interface AST {}

class Start implements AST {
    Expr expr;

    public Start(Expr expr) {
        this.expr = expr;
    }

    public Expr convertToCNF() {
        return expr.convertToCNF();
    }
}

abstract class Expr implements AST {
    abstract public Expr convertToCNF();

    abstract public String toInputFormat();

    abstract public String toSATFormat();

    abstract public Expr deMorgan();

    abstract Expr lawOfDistribution(Expr left);
}

class Not extends Expr {
    Expr expr;

    Not(Expr c1) {
        this.expr = c1;
    }

    @Override
    public Expr convertToCNF() {
        Expr cnf = this.expr.convertToCNF();

        if (cnf instanceof Atomic)
            return new Not(cnf);

        return cnf.deMorgan();
    }

    @Override
    public Expr deMorgan(){
        return expr.deMorgan();
    }

    @Override
    public Expr lawOfDistribution(Expr left) {
        if (expr instanceof Atomic) {
            if (left instanceof Atomic)
                return new Or(this,left);
            return left.lawOfDistribution(this);
        }
        System.out.println("shouldn't happpend");
        return expr.lawOfDistribution(left);
    }

    @Override
    public String toInputFormat() {
        return "~" + expr.toInputFormat();
    }

    @Override
    public String toSATFormat() {
        return "¬" + expr.toInputFormat();
    }
}

class And extends Expr {
    Expr left, right;

    And(Expr c1, Expr c2) {
        this.left = c1;
        this.right = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.left.convertToCNF();
        Expr c2 = this.right.convertToCNF();

        return new And(c1, c2);
    }

    @Override
    public Expr deMorgan() {
        return new Or(new Not(left.deMorgan()), new Not(right.deMorgan()));
    }

    @Override
    public Expr lawOfDistribution(Expr left){
        return new And(new Parenthesis(this.left.lawOfDistribution(left)),new Parenthesis(right.lawOfDistribution(left)));
    }

    @Override
    public String toInputFormat() {
        return left.toInputFormat() + " & " + right.toInputFormat();
    }

    @Override
    public String toSATFormat() {
        return left.toInputFormat() + " ∧ " + right.toInputFormat();
    }
}

class Or extends Expr {
    Expr left, right;

    Or(Expr c1, Expr c2) {
        this.left = c1;
        this.right = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.left.convertToCNF();
        Expr c2 = this.right.convertToCNF();

        if (c1 instanceof Parenthesis || c2 instanceof Parenthesis) {
            return c1.lawOfDistribution(c2);
        }

        return new Or(c1, c2);//.convertToCNF(env);
    }

    @Override
    public Expr lawOfDistribution(Expr left) {
        return new Or(left, right.lawOfDistribution(left));
    }

    @Override
    public Expr deMorgan(){
        return new And(new Not(left.deMorgan()), new Not(right.deMorgan()));
    }

    @Override
    public String toInputFormat() {
        return left.toInputFormat() + " | " + right.toInputFormat();
    }

    @Override
    public String toSATFormat() {
        return left.toInputFormat() + " ∨ " + right.toInputFormat();
    }
}

class Bimp extends Expr {
    Expr left, right;

    Bimp(Expr c1, Expr c2) {
        this.left = c1;
        this.right = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.left.convertToCNF();
        Expr c2 = this.right.convertToCNF();
        return new And(new Parenthesis(new Imp(c1, c2)), new Parenthesis(new Imp(c2, c1))).convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return left.toInputFormat() + " <=> " + right.toInputFormat();
    }

    @Override
    public String toSATFormat() {
        return left.toInputFormat() + " ↔ " + right.toInputFormat();
    }

    @Override
    public Expr deMorgan() {
        // Should not happen
        System.out.println("Biimplication node visited. Should not happen when calling deMorgan()");
        return null;
    }

    @Override
    Expr lawOfDistribution(Expr left) {
        // Should not happen
        System.out.println("Bi implication node visited. Should not happen when calling lawOfDistribution()");
        return null;
    }
}

class Imp extends Expr {
    Expr left, right;

    Imp(Expr c1, Expr c2) {
        this.left = c1;
        this.right = c2;
    }

    @Override
    public Expr convertToCNF() {
        return new Parenthesis(new Or(new Not(this.left), this.right)).convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return left.toInputFormat() + " => " + right.toInputFormat();
    }

    @Override
    public String toSATFormat() {
        return left.toInputFormat() + " → " + right.toInputFormat();
    }

    @Override
    public Expr deMorgan() {
        // Should not happen
        System.out.println("Implication node visited. Should not happen when calling deMorgan()");
        return null;
    }

    @Override
    Expr lawOfDistribution(Expr left) {
        // Should not happen
        System.out.println("Implication node visited. Should not happen when calling lawOfDistribution()");
        return null;
    }
}

class Parenthesis extends Expr {
    Expr expr;

    public Parenthesis(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Expr convertToCNF() {
        Expr cnf = expr.convertToCNF();
        //if (cnf instanceof Or) return cnf.lawOfDistribution(env, ((Or) cnf).c2);
        if (cnf instanceof Parenthesis) return cnf;
        return new Parenthesis(cnf);
    }

    @Override
    public Expr deMorgan(){
        return expr.deMorgan();
    }

    @Override
    public Expr lawOfDistribution(Expr left){
        return expr.lawOfDistribution(left);
    }

    @Override
    public String toInputFormat() {
        return '(' + expr.toInputFormat() + ')';
    }

    @Override
    public String toSATFormat() {
        return '(' + expr.toInputFormat() + ')';
    }
}


class Atomic extends Expr {
    String name;

    public Atomic(String name) {
        this.name = name;
    }

    @Override
    public Expr convertToCNF() {
        return this;
    }

    @Override
    public Expr deMorgan(){
        return this;
    }

    @Override
    public Expr lawOfDistribution(Expr left){
        if (left instanceof Atomic)
            return new Or(left,this);
        return left.lawOfDistribution(this);
    }

    @Override
    public String toInputFormat() {
        return name;
    }

    @Override
    public String toSATFormat() {
        return name;
    }
}
