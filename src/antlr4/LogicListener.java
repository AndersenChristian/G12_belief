// Generated from java-escape by ANTLR 4.11.1

    package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LogicParser}.
 */
public interface LogicListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LogicParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(LogicParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogicParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(LogicParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNOT(LogicParser.NOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNOT(LogicParser.NOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OR}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOR(LogicParser.ORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OR}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOR(LogicParser.ORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AND}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAND(LogicParser.ANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AND}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAND(LogicParser.ANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IFF}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIFF(LogicParser.IFFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IFF}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIFF(LogicParser.IFFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Atomic}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomic(LogicParser.AtomicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Atomic}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomic(LogicParser.AtomicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IMP}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIMP(LogicParser.IMPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMP}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIMP(LogicParser.IMPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(LogicParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(LogicParser.ParenthesesContext ctx);
}