// Generated from SCIMFilter.g4 by ANTLR 4.13.2
package dev.suvera.scim2.schema.filter.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SCIMFilterParser}.
 */
public interface SCIMFilterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SCIMFilterParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SCIMFilterParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(SCIMFilterParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(SCIMFilterParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#moreFilters}.
	 * @param ctx the parse tree
	 */
	void enterMoreFilters(SCIMFilterParser.MoreFiltersContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#moreFilters}.
	 * @param ctx the parse tree
	 */
	void exitMoreFilters(SCIMFilterParser.MoreFiltersContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#filterExpression}.
	 * @param ctx the parse tree
	 */
	void enterFilterExpression(SCIMFilterParser.FilterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#filterExpression}.
	 * @param ctx the parse tree
	 */
	void exitFilterExpression(SCIMFilterParser.FilterExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#closedFilter}.
	 * @param ctx the parse tree
	 */
	void enterClosedFilter(SCIMFilterParser.ClosedFilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#closedFilter}.
	 * @param ctx the parse tree
	 */
	void exitClosedFilter(SCIMFilterParser.ClosedFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#notFilter}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(SCIMFilterParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#notFilter}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(SCIMFilterParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#valuePath}.
	 * @param ctx the parse tree
	 */
	void enterValuePath(SCIMFilterParser.ValuePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#valuePath}.
	 * @param ctx the parse tree
	 */
	void exitValuePath(SCIMFilterParser.ValuePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#attrExp}.
	 * @param ctx the parse tree
	 */
	void enterAttrExp(SCIMFilterParser.AttrExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#attrExp}.
	 * @param ctx the parse tree
	 */
	void exitAttrExp(SCIMFilterParser.AttrExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#filterOperator}.
	 * @param ctx the parse tree
	 */
	void enterFilterOperator(SCIMFilterParser.FilterOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#filterOperator}.
	 * @param ctx the parse tree
	 */
	void exitFilterOperator(SCIMFilterParser.FilterOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#valuePresent}.
	 * @param ctx the parse tree
	 */
	void enterValuePresent(SCIMFilterParser.ValuePresentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#valuePresent}.
	 * @param ctx the parse tree
	 */
	void exitValuePresent(SCIMFilterParser.ValuePresentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#compValue}.
	 * @param ctx the parse tree
	 */
	void enterCompValue(SCIMFilterParser.CompValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#compValue}.
	 * @param ctx the parse tree
	 */
	void exitCompValue(SCIMFilterParser.CompValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#stringValue}.
	 * @param ctx the parse tree
	 */
	void enterStringValue(SCIMFilterParser.StringValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#stringValue}.
	 * @param ctx the parse tree
	 */
	void exitStringValue(SCIMFilterParser.StringValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(SCIMFilterParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(SCIMFilterParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void enterCompareOp(SCIMFilterParser.CompareOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void exitCompareOp(SCIMFilterParser.CompareOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#attrPath}.
	 * @param ctx the parse tree
	 */
	void enterAttrPath(SCIMFilterParser.AttrPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#attrPath}.
	 * @param ctx the parse tree
	 */
	void exitAttrPath(SCIMFilterParser.AttrPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SCIMFilterParser#subAttr}.
	 * @param ctx the parse tree
	 */
	void enterSubAttr(SCIMFilterParser.SubAttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link SCIMFilterParser#subAttr}.
	 * @param ctx the parse tree
	 */
	void exitSubAttr(SCIMFilterParser.SubAttrContext ctx);
}