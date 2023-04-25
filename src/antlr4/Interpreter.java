package antlr4;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class Interpreter extends AbstractParseTreeVisitor<AST> implements LogicVisitor<AST> {

    @Override
    public AST visitStart(LogicParser.StartContext ctx) {
        return new Start((Expr) visit(ctx.e));
    }

    @Override
    public AST visitNOT(LogicParser.NOTContext ctx) {
        return new Not((Expr) visit(ctx.c1));
    }

    @Override
    public AST visitOR(LogicParser.ORContext ctx) {
        return new Or((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitAND(LogicParser.ANDContext ctx) {
        return new And((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitIFF(LogicParser.IFFContext ctx) {
        return new Iff((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitAtomic(LogicParser.AtomicContext ctx) {
        return new Atomic(ctx.getText(), false);
    }

    @Override
    public AST visitIMP(LogicParser.IMPContext ctx) {
        return new Imp((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitParentheses(LogicParser.ParenthesesContext ctx) {
        return new Parenthesis((Expr) visit(ctx.c1));
    }
}
