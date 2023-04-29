package antlr4;

public interface AST {}

class Start extends Expr implements AST {
    Expr expr;

    public Start(Expr expr) {
        this.expr = expr;
    }

    public Expr convertToCNF() {
        return expr.convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return expr.toInputFormat();
    }
}

class Not extends Expr {
    Expr c1;

    Not(Expr c1) {
        this.c1 = c1;
    }

    @Override
    public Expr convertToCNF() {
        Expr cnf = this.c1.convertToCNF();

        if (cnf instanceof Atomic)
            return new Not(cnf);
        //if (cnf instanceof Parenthesis)
        //    return cnf.deMorgan(env);
        return cnf.deMorgan();
    }

    @Override
    public Expr deMorgan(){
        return c1.deMorgan();
    }

    @Override
    public Expr lawOfDistribution(Expr left) {
        if (c1 instanceof Atomic) {
            if (left instanceof Atomic)
                return new Or(this,left);
            return left.lawOfDistribution(this);
        }
        System.out.println("shouldn't happpend");
        return c1.lawOfDistribution(left);
    }

    @Override
    public String toInputFormat() {
        return "~" + c1.toInputFormat();
    }
}

class And extends Expr {
    Expr c1, c2;

    And(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.c1.convertToCNF();
        Expr c2 = this.c2.convertToCNF();

        return new And(c1, c2);
    }

    @Override
    public Expr deMorgan() {
        return new Or(new Not(c1.deMorgan()), new Not(c2.deMorgan()));
    }

    @Override
    public Expr lawOfDistribution(Expr left){
        return new And(new Parenthesis(c1.lawOfDistribution(left)),new Parenthesis(c2.lawOfDistribution(left)));
    }

    @Override
    public String toInputFormat() {
        return c1.toInputFormat() + " & " + c2.toInputFormat();
    }
}

class Or extends Expr {
    Expr c1, c2;

    Or(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.c1.convertToCNF();
        Expr c2 = this.c2.convertToCNF();

        if (c1 instanceof Parenthesis || c2 instanceof Parenthesis) {
            return c1.lawOfDistribution(c2);
        }

        return new Or(c1, c2);//.convertToCNF(env);
    }

    @Override
    public Expr lawOfDistribution(Expr left) {
        return new Or(left, c2.lawOfDistribution(left));
    }

    @Override
    public Expr deMorgan(){
        return new And(new Not(c1.deMorgan()), new Not(c2.deMorgan()));
    }

    @Override
    public String toInputFormat() {
        return c1.toInputFormat() + " | " + c2.toInputFormat();
    }
}

class Iff extends Expr {
    Expr c1, c2;

    Iff(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Expr convertToCNF() {
        Expr c1 = this.c1.convertToCNF();
        Expr c2 = this.c2.convertToCNF();
        return new And(new Parenthesis(new Imp(c1, c2)), new Parenthesis(new Imp(c2, c1))).convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return c1.toInputFormat() + " <=> " + c2.toInputFormat();
    }
}

class Imp extends Expr {
    Expr c1, c2;

    Imp(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Expr convertToCNF() {
        return new Parenthesis(new Or(new Not(this.c1), this.c2)).convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return c1.toInputFormat() + " => " + c2.toInputFormat();
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
}
