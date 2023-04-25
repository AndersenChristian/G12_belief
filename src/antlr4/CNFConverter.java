package antlr4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

public class CNFConverter {

    public Expr convertToCNF(String input) {
        CharStream inputstream = CharStreams.fromString(input);

        // create a lexer/scanner
        LogicLexer lex = new LogicLexer(inputstream);

        // get the stream of tokens from the scanner
        CommonTokenStream tokens = new CommonTokenStream(lex);

        // create a parser
        LogicParser parser = new LogicParser(tokens);

        // and parse anything from the grammar for "start"
        ParseTree parseTree = parser.start();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.println("Error: Syntax does not match with logic grammar");
            System.exit(1);
        }

        // Construct an interpreter and run it on the parse tree
        Interpreter interpreter = new Interpreter();
        Start result = (Start) interpreter.visit(parseTree);
        return result.convertToCNF();
    }
}

