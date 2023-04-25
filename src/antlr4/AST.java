package antlr4;

import java.util.ArrayList;
import java.util.List;

public abstract class AST {}

class Start extends AST {
    List<Expr> expr;

    public Start(List<Expr> expr) {
        this.expr = expr;
    }

    public List<Expr> convertToCNF(Environment env) {
        List<Expr> cnf = new ArrayList<>();
        for (Expr e : expr) {
            cnf.add(e.CNFConverter(env));
        }
        return cnf;
    }

    public String toInputFormat() {
        StringBuilder result = new StringBuilder();
        for (Expr e : expr) {
            result.append(e.toInputFormat());
        }
        return result.toString();
    }
}

class ExprInstance extends Expr {
    Expr expr;

    ExprInstance(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Expr CNFConverter(Environment env) {
        return expr.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        return new Not(this.c1.CNFConverter(env));
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
    public Expr CNFConverter(Environment env) {
        Expr c1 = this.c1.CNFConverter(env);
        Expr c2 = this.c2.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        Expr c1 = this.c1.CNFConverter(env);
        Expr c2 = this.c2.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        Expr c1 = this.c1.CNFConverter(env);
        Expr c2 = this.c2.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        Expr c1 = this.c1.CNFConverter(env);
        Expr c2 = this.c2.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        return expr.CNFConverter(env);
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
    public Expr CNFConverter(Environment env) {
        return this;
    }

    @Override
    public String toInputFormat() {
        return name;
    }
}
