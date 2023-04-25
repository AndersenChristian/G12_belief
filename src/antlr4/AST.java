package antlr4;

import java.util.List;

public abstract class AST {}

class Start extends AST {
    List<Expr> expr;

    public Start(List<Expr> expr) {
        this.expr = expr;
    }

    public void eval(Environment env) {
        for (Expr e : expr) {
            e.CNF(env);
        }
    }
}

abstract class Expr extends AST {
    abstract public void CNF(Environment env);
}

class Not extends Expr {
    Expr c1;

    Not(Expr c1) {
        this.c1 = c1;
    }

    @Override
    public void CNF(Environment env) {
        this.c1.CNF(env);
    }
}

class And extends Expr {
    Expr c1, c2;

    And(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void CNF(Environment env) {
        this.c1.CNF(env);
        this.c2.CNF(env);
    }
}

class Or extends Expr {
    Expr c1, c2;

    Or(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void CNF(Environment env) {
        this.c1.CNF(env);
        this.c2.CNF(env);
    }
}

class Iff extends Expr {
    Expr c1, c2;

    Iff(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void CNF(Environment env) {
        this.c1.CNF(env);
        this.c2.CNF(env);
    }
}

class Imp extends Expr {
    Expr c1, c2;

    Imp(Expr c1, Expr c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void CNF(Environment env) {
        this.c1.CNF(env);
        this.c2.CNF(env);
    }
}

class Parenthesis extends Expr {
    List<Expr> expr;

    public Parenthesis(List<Expr> expr) {
        this.expr = expr;
    }

    @Override
    public void CNF(Environment env) {
        for(Expr e: expr) {
            e.CNF(env);
        }
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
    public void CNF(Environment env) {
        System.out.println(name);
    }
}
