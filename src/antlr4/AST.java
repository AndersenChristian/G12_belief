package antlr4;

import java.util.ArrayList;
import java.util.List;

public abstract class AST {}

class Start extends AST {
    Expr expr;

    public Start(Expr expr) {
        this.expr = expr;
    }

    public Expr convertToCNF() {
        return expr.CNFConverter();
    }
}

class ExprInstance extends Expr {
    Expr expr;

    ExprInstance(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Expr CNFConverter() {
        return expr.CNFConverter();
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
    public Expr CNFConverter() {
        return new Not(this.c1.CNFConverter());
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
    public Expr CNFConverter() {
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
    public Expr CNFConverter() {
        Expr c1 = this.c1.CNFConverter();
        Expr c2 = this.c2.CNFConverter();
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
    public Expr CNFConverter() {
        Expr c1 = this.c1.CNFConverter();
        Expr c2 = this.c2.CNFConverter();
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
    public Expr CNFConverter() {
        Expr c1 = this.c1.CNFConverter();
        Expr c2 = this.c2.CNFConverter();
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
    public Expr CNFConverter() {
        return expr.CNFConverter();
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
    public Expr CNFConverter() {
        return this;
    }

    @Override
    public String toInputFormat() {
        return name;
    }
}
