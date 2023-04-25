package antlr4;

public abstract class AST {}

class Start extends AST {
    Expr expr;

    public Start(Expr expr) {
        this.expr = expr;
    }

    public Expr convertToCNF() {
        return expr.convertToCNF();
    }
}

class ExprInstance extends Expr {
    Expr expr;

    ExprInstance(Expr expr) {
        this.expr = expr;
    }

    @Override
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
        return new Not(this.c1.convertToCNF());
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
        return new And(c1, c2);
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
        return new Or(c1, c2);
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
        return new Or(new And(new Not(c2), c1), new And(new Not(c1), c2));
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
        Expr c1 = this.c1.convertToCNF();
        Expr c2 = this.c2.convertToCNF();
        return new Or(new Not(c1), new ExprInstance(c2));
    }

    @Override
    public String toInputFormat() {
        return c1.toInputFormat() + "=>" + c2.toInputFormat();
    }
}

class Parenthesis extends Expr {
    Expr expr;

    public Parenthesis(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Expr convertToCNF() {
        return expr.convertToCNF();
    }

    @Override
    public String toInputFormat() {
        return expr.toInputFormat();
    }
}


class Atomic extends Expr {
    String name;
    boolean value;

    public Atomic(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Expr convertToCNF() {
        return this;
    }

    @Override
    public String toInputFormat() {
        return name;
    }
}
