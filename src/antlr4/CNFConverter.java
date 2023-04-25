package antlr4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;


public class CNFConverter {

    public List<Expr> convertToCNF(String input) {
        // open the input file
        //CharStream input = CharStreams.fromFileName(filename);

        CharStream inputstream = CharStreams.fromString(input);

        // create a lexer/scanner
        LogicLexer lex = new LogicLexer(inputstream);

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
        return result.convertToCNF(env);
    }
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