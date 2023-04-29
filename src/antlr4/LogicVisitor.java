// Generated from java-escape by ANTLR 4.11.1

    package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LogicParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LogicVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LogicParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(LogicParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT(LogicParser.NOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OR}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOR(LogicParser.ORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AND}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAND(LogicParser.ANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IFF}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIFF(LogicParser.IFFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Atomic}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomic(LogicParser.AtomicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMP}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMP(LogicParser.IMPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link LogicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentheses(LogicParser.ParenthesesContext ctx);
}