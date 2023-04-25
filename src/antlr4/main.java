package antlr4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class LogicExprListener extends LogicBaseListener {
    public static void main(String[] args) throws IOException {

        // we expect exactly one argument: the name of the input file
        if (args.length != 1) {
            System.err.println("\n");
            System.err.println("Logic Interpreter\n");
            System.err.println("=================\n\n");
            System.err.println("Please give as input argument a filename\n");
            System.exit(1);
        }
        String filename = args[0];

        // open the input file
        CharStream input = CharStreams.fromFileName(filename);

        // create a lexer/scanner
        LogicLexer lex = new LogicLexer(input);

        // get the stream of tokens from the scanner
        CommonTokenStream tokens = new CommonTokenStream(lex);

        //tokens.fill();

        // create a parser
        LogicParser parser = new LogicParser(tokens);

        // and parse anything from the grammar for "start"
        ParseTree parseTree = parser.start();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.println("Error: Syntax does not match with hwsim grammar");
            System.exit(1);
        }

        // Construct an interpreter and run it on the parse tree
        Interpreter interpreter = new Interpreter();
        Start result = (Start) interpreter.visit(parseTree);
        Environment env = new Environment();
        result.eval(env);
        /*result.runSimulator(env);
        System.out.println(env);*/
        //ParseTreeWalker walker = new ParseTreeWalker();
        //LogicExprListener listener = new LogicExprListener();
        //walker.walk(listener, parser.start());
    }

    /*@Override
    public void enterStart(LogicParser.StartContext ctx) {
        System.out.println("Entering start: " + ctx.getText());
    }

    @Override
    public void enterAND(LogicParser.ANDContext ctx) {
        System.out.println("Entering and: " + ctx.getText());
        new And((Expr) ctx.c1, (Expr) ctx.c2);
    }

    @Override public void exitAND(LogicParser.ANDContext ctx) {
        System.out.println("exit and: " + ctx.getText());
    }

    @Override
    public void enterAtomic(LogicParser.AtomicContext ctx) {
        System.out.println("enter atom: " + ctx.getText());
    }

    @Override
    public void exitAtomic(LogicParser.AtomicContext ctx) {
        System.out.println("exit atom: " + ctx.getText());
    }*/
}

class Interpreter extends AbstractParseTreeVisitor<AST> implements LogicVisitor<AST> {

    @Override
    public AST visitStart(LogicParser.StartContext ctx) {
        List<Expr> expr = new ArrayList<>();
        for (LogicParser.ExprContext e : ctx.e) {
            expr.add((Expr) visit(e));
        }
        return new Start(expr);
    }

    @Override
    public AST visitNOT(LogicParser.NOTContext ctx) {
        System.out.println(ctx.c1.getText());
        return new Not((Expr) visit(ctx.c1));
    }

    @Override
    public AST visitOR(LogicParser.ORContext ctx) {
        System.out.println(ctx.getText());
        return new Or((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitAND(LogicParser.ANDContext ctx) {
        System.out.println(ctx.getText());
        return new And((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitIFF(LogicParser.IFFContext ctx) {
        return new Iff((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitAtomic(LogicParser.AtomicContext ctx) {
        System.out.println(ctx.getText());
        return new Atomic(ctx.getText(), false);
    }

    @Override
    public AST visitIMP(LogicParser.IMPContext ctx) {
        return new Imp((Expr) visit(ctx.c1), (Expr) visit(ctx.c2));
    }

    @Override
    public AST visitParentheses(LogicParser.ParenthesesContext ctx) {
        System.out.println(ctx.getText());
        List<Expr> expr = new ArrayList<>();
        for (LogicParser.ExprContext e : ctx.c1) {
            expr.add((Expr) visit(e));
        }
        return new Parenthesis(expr);
    }
}