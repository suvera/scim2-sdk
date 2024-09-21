// Generated from SCIMFilter.g4 by ANTLR 4.13.2
package dev.suvera.scim2.schema.filter.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SCIMFilterParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NOT=1, OPEN_PARA=2, CLOSE_PARA=3, OPEN_BRACKET=4, CLOSE_BRACKET=5, PR=6, 
		AND=7, OR=8, EQ=9, NE=10, CO=11, SW=12, EW=13, GT=14, LT=15, GE=16, LE=17, 
		QUOTE=18, DOUBLE_QUOTE=19, HYPHEN=20, UNDERSCORE=21, DOT=22, FALSE=23, 
		NULL=24, TRUE=25, NEW_RETURN=26, NEW_LINE=27, ALPHA=28, DIGIT=29, SP=30, 
		URI=31, ATTR_NAME=32, DOUBLE_SLASH=33, QUOTE_SLASH=34, ANY=35;
	public static final int
		RULE_expression = 0, RULE_filter = 1, RULE_moreFilters = 2, RULE_filterExpression = 3, 
		RULE_closedFilter = 4, RULE_notFilter = 5, RULE_valuePath = 6, RULE_attrExp = 7, 
		RULE_filterOperator = 8, RULE_valuePresent = 9, RULE_compValue = 10, RULE_stringValue = 11, 
		RULE_number = 12, RULE_compareOp = 13, RULE_attrPath = 14, RULE_subAttr = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "filter", "moreFilters", "filterExpression", "closedFilter", 
			"notFilter", "valuePath", "attrExp", "filterOperator", "valuePresent", 
			"compValue", "stringValue", "number", "compareOp", "attrPath", "subAttr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'not'", "'('", "')'", "'['", "']'", "'pr'", "'and'", "'or'", "'eq'", 
			"'ne'", "'co'", "'sw'", "'ew'", "'gt'", "'lt'", "'ge'", "'le'", "'\"'", 
			"'\"\"'", "'-'", "'_'", "'.'", "'false'", "'null'", "'true'", "'\\r'", 
			"'\\n'", null, null, null, null, null, "'\\'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NOT", "OPEN_PARA", "CLOSE_PARA", "OPEN_BRACKET", "CLOSE_BRACKET", 
			"PR", "AND", "OR", "EQ", "NE", "CO", "SW", "EW", "GT", "LT", "GE", "LE", 
			"QUOTE", "DOUBLE_QUOTE", "HYPHEN", "UNDERSCORE", "DOT", "FALSE", "NULL", 
			"TRUE", "NEW_RETURN", "NEW_LINE", "ALPHA", "DIGIT", "SP", "URI", "ATTR_NAME", 
			"DOUBLE_SLASH", "QUOTE_SLASH", "ANY"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SCIMFilter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SCIMFilterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			filter();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FilterContext extends ParserRuleContext {
		public FilterExpressionContext filterExpression() {
			return getRuleContext(FilterExpressionContext.class,0);
		}
		public MoreFiltersContext moreFilters() {
			return getRuleContext(MoreFiltersContext.class,0);
		}
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitFilter(this);
		}
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_filter);
		try {
			setState(36);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				filterExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(35);
				moreFilters();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MoreFiltersContext extends ParserRuleContext {
		public FilterExpressionContext filterExpression() {
			return getRuleContext(FilterExpressionContext.class,0);
		}
		public List<TerminalNode> SP() { return getTokens(SCIMFilterParser.SP); }
		public TerminalNode SP(int i) {
			return getToken(SCIMFilterParser.SP, i);
		}
		public FilterOperatorContext filterOperator() {
			return getRuleContext(FilterOperatorContext.class,0);
		}
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public MoreFiltersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moreFilters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterMoreFilters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitMoreFilters(this);
		}
	}

	public final MoreFiltersContext moreFilters() throws RecognitionException {
		MoreFiltersContext _localctx = new MoreFiltersContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_moreFilters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			filterExpression();
			setState(39);
			match(SP);
			setState(40);
			filterOperator();
			setState(41);
			match(SP);
			setState(42);
			filter();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FilterExpressionContext extends ParserRuleContext {
		public AttrExpContext attrExp() {
			return getRuleContext(AttrExpContext.class,0);
		}
		public ClosedFilterContext closedFilter() {
			return getRuleContext(ClosedFilterContext.class,0);
		}
		public NotFilterContext notFilter() {
			return getRuleContext(NotFilterContext.class,0);
		}
		public ValuePathContext valuePath() {
			return getRuleContext(ValuePathContext.class,0);
		}
		public FilterExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterFilterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitFilterExpression(this);
		}
	}

	public final FilterExpressionContext filterExpression() throws RecognitionException {
		FilterExpressionContext _localctx = new FilterExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_filterExpression);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				attrExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				closedFilter();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				notFilter();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(47);
				valuePath();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClosedFilterContext extends ParserRuleContext {
		public TerminalNode OPEN_PARA() { return getToken(SCIMFilterParser.OPEN_PARA, 0); }
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public TerminalNode CLOSE_PARA() { return getToken(SCIMFilterParser.CLOSE_PARA, 0); }
		public ClosedFilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closedFilter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterClosedFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitClosedFilter(this);
		}
	}

	public final ClosedFilterContext closedFilter() throws RecognitionException {
		ClosedFilterContext _localctx = new ClosedFilterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_closedFilter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(OPEN_PARA);
			setState(51);
			filter();
			setState(52);
			match(CLOSE_PARA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NotFilterContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(SCIMFilterParser.NOT, 0); }
		public ClosedFilterContext closedFilter() {
			return getRuleContext(ClosedFilterContext.class,0);
		}
		public NotFilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notFilter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterNotFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitNotFilter(this);
		}
	}

	public final NotFilterContext notFilter() throws RecognitionException {
		NotFilterContext _localctx = new NotFilterContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_notFilter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(NOT);
			setState(55);
			closedFilter();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValuePathContext extends ParserRuleContext {
		public AttrPathContext attrPath() {
			return getRuleContext(AttrPathContext.class,0);
		}
		public TerminalNode OPEN_BRACKET() { return getToken(SCIMFilterParser.OPEN_BRACKET, 0); }
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public TerminalNode CLOSE_BRACKET() { return getToken(SCIMFilterParser.CLOSE_BRACKET, 0); }
		public ValuePathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valuePath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterValuePath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitValuePath(this);
		}
	}

	public final ValuePathContext valuePath() throws RecognitionException {
		ValuePathContext _localctx = new ValuePathContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_valuePath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			attrPath();
			setState(58);
			match(OPEN_BRACKET);
			setState(59);
			filter();
			setState(60);
			match(CLOSE_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttrExpContext extends ParserRuleContext {
		public AttrPathContext attrPath() {
			return getRuleContext(AttrPathContext.class,0);
		}
		public List<TerminalNode> SP() { return getTokens(SCIMFilterParser.SP); }
		public TerminalNode SP(int i) {
			return getToken(SCIMFilterParser.SP, i);
		}
		public ValuePresentContext valuePresent() {
			return getRuleContext(ValuePresentContext.class,0);
		}
		public CompareOpContext compareOp() {
			return getRuleContext(CompareOpContext.class,0);
		}
		public CompValueContext compValue() {
			return getRuleContext(CompValueContext.class,0);
		}
		public AttrExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterAttrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitAttrExp(this);
		}
	}

	public final AttrExpContext attrExp() throws RecognitionException {
		AttrExpContext _localctx = new AttrExpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_attrExp);
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				attrPath();
				setState(63);
				match(SP);
				setState(64);
				valuePresent();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
				attrPath();
				setState(67);
				match(SP);
				setState(68);
				compareOp();
				setState(69);
				match(SP);
				setState(70);
				compValue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FilterOperatorContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(SCIMFilterParser.AND, 0); }
		public TerminalNode OR() { return getToken(SCIMFilterParser.OR, 0); }
		public FilterOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterFilterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitFilterOperator(this);
		}
	}

	public final FilterOperatorContext filterOperator() throws RecognitionException {
		FilterOperatorContext _localctx = new FilterOperatorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_filterOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValuePresentContext extends ParserRuleContext {
		public TerminalNode PR() { return getToken(SCIMFilterParser.PR, 0); }
		public ValuePresentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valuePresent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterValuePresent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitValuePresent(this);
		}
	}

	public final ValuePresentContext valuePresent() throws RecognitionException {
		ValuePresentContext _localctx = new ValuePresentContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_valuePresent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(PR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompValueContext extends ParserRuleContext {
		public TerminalNode FALSE() { return getToken(SCIMFilterParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(SCIMFilterParser.NULL, 0); }
		public TerminalNode TRUE() { return getToken(SCIMFilterParser.TRUE, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public StringValueContext stringValue() {
			return getRuleContext(StringValueContext.class,0);
		}
		public CompValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterCompValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitCompValue(this);
		}
	}

	public final CompValueContext compValue() throws RecognitionException {
		CompValueContext _localctx = new CompValueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_compValue);
		try {
			setState(83);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FALSE:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				match(FALSE);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				match(NULL);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				match(TRUE);
				}
				break;
			case DIGIT:
				enterOuterAlt(_localctx, 4);
				{
				setState(81);
				number();
				}
				break;
			case QUOTE:
				enterOuterAlt(_localctx, 5);
				{
				setState(82);
				stringValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringValueContext extends ParserRuleContext {
		public List<TerminalNode> QUOTE() { return getTokens(SCIMFilterParser.QUOTE); }
		public TerminalNode QUOTE(int i) {
			return getToken(SCIMFilterParser.QUOTE, i);
		}
		public List<TerminalNode> DOUBLE_SLASH() { return getTokens(SCIMFilterParser.DOUBLE_SLASH); }
		public TerminalNode DOUBLE_SLASH(int i) {
			return getToken(SCIMFilterParser.DOUBLE_SLASH, i);
		}
		public StringValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterStringValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitStringValue(this);
		}
	}

	public final StringValueContext stringValue() throws RecognitionException {
		StringValueContext _localctx = new StringValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_stringValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(QUOTE);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 68719214590L) != 0)) {
				{
				setState(89);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(86);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==QUOTE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case 2:
					{
					setState(87);
					match(DOUBLE_SLASH);
					setState(88);
					match(QUOTE);
					}
					break;
				}
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(94);
			match(QUOTE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> DIGIT() { return getTokens(SCIMFilterParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SCIMFilterParser.DIGIT, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(96);
				match(DIGIT);
				}
				}
				setState(99); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompareOpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(SCIMFilterParser.EQ, 0); }
		public TerminalNode NE() { return getToken(SCIMFilterParser.NE, 0); }
		public TerminalNode CO() { return getToken(SCIMFilterParser.CO, 0); }
		public TerminalNode SW() { return getToken(SCIMFilterParser.SW, 0); }
		public TerminalNode EW() { return getToken(SCIMFilterParser.EW, 0); }
		public TerminalNode GT() { return getToken(SCIMFilterParser.GT, 0); }
		public TerminalNode LT() { return getToken(SCIMFilterParser.LT, 0); }
		public TerminalNode GE() { return getToken(SCIMFilterParser.GE, 0); }
		public TerminalNode LE() { return getToken(SCIMFilterParser.LE, 0); }
		public CompareOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterCompareOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitCompareOp(this);
		}
	}

	public final CompareOpContext compareOp() throws RecognitionException {
		CompareOpContext _localctx = new CompareOpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_compareOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 261632L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttrPathContext extends ParserRuleContext {
		public TerminalNode URI() { return getToken(SCIMFilterParser.URI, 0); }
		public TerminalNode HYPHEN() { return getToken(SCIMFilterParser.HYPHEN, 0); }
		public TerminalNode ATTR_NAME() { return getToken(SCIMFilterParser.ATTR_NAME, 0); }
		public List<SubAttrContext> subAttr() {
			return getRuleContexts(SubAttrContext.class);
		}
		public SubAttrContext subAttr(int i) {
			return getRuleContext(SubAttrContext.class,i);
		}
		public AttrPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterAttrPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitAttrPath(this);
		}
	}

	public final AttrPathContext attrPath() throws RecognitionException {
		AttrPathContext _localctx = new AttrPathContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_attrPath);
		int _la;
		try {
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				match(URI);
				setState(104);
				match(HYPHEN);
				setState(105);
				match(ATTR_NAME);
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(106);
					subAttr();
					}
					}
					setState(111);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
				match(ATTR_NAME);
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(113);
					subAttr();
					}
					}
					setState(118);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(119);
				match(ATTR_NAME);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubAttrContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(SCIMFilterParser.DOT, 0); }
		public TerminalNode ATTR_NAME() { return getToken(SCIMFilterParser.ATTR_NAME, 0); }
		public SubAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subAttr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).enterSubAttr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SCIMFilterListener ) ((SCIMFilterListener)listener).exitSubAttr(this);
		}
	}

	public final SubAttrContext subAttr() throws RecognitionException {
		SubAttrContext _localctx = new SubAttrContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_subAttr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(DOT);
			setState(123);
			match(ATTR_NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001#~\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005"+
		"\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007"+
		"\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007"+
		"\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001%\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u00031\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007I\b\u0007\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\nT\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000bZ\b"+
		"\u000b\n\u000b\f\u000b]\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0004"+
		"\fb\b\f\u000b\f\f\fc\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0005\u000el\b\u000e\n\u000e\f\u000eo\t\u000e\u0001\u000e"+
		"\u0001\u000e\u0005\u000es\b\u000e\n\u000e\f\u000ev\t\u000e\u0001\u000e"+
		"\u0003\u000ey\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0000\u0000\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e\u0000\u0003\u0001\u0000\u0007\b\u0001\u0000"+
		"\u0012\u0012\u0001\u0000\t\u0011}\u0000 \u0001\u0000\u0000\u0000\u0002"+
		"$\u0001\u0000\u0000\u0000\u0004&\u0001\u0000\u0000\u0000\u00060\u0001"+
		"\u0000\u0000\u0000\b2\u0001\u0000\u0000\u0000\n6\u0001\u0000\u0000\u0000"+
		"\f9\u0001\u0000\u0000\u0000\u000eH\u0001\u0000\u0000\u0000\u0010J\u0001"+
		"\u0000\u0000\u0000\u0012L\u0001\u0000\u0000\u0000\u0014S\u0001\u0000\u0000"+
		"\u0000\u0016U\u0001\u0000\u0000\u0000\u0018a\u0001\u0000\u0000\u0000\u001a"+
		"e\u0001\u0000\u0000\u0000\u001cx\u0001\u0000\u0000\u0000\u001ez\u0001"+
		"\u0000\u0000\u0000 !\u0003\u0002\u0001\u0000!\u0001\u0001\u0000\u0000"+
		"\u0000\"%\u0003\u0006\u0003\u0000#%\u0003\u0004\u0002\u0000$\"\u0001\u0000"+
		"\u0000\u0000$#\u0001\u0000\u0000\u0000%\u0003\u0001\u0000\u0000\u0000"+
		"&\'\u0003\u0006\u0003\u0000\'(\u0005\u001e\u0000\u0000()\u0003\u0010\b"+
		"\u0000)*\u0005\u001e\u0000\u0000*+\u0003\u0002\u0001\u0000+\u0005\u0001"+
		"\u0000\u0000\u0000,1\u0003\u000e\u0007\u0000-1\u0003\b\u0004\u0000.1\u0003"+
		"\n\u0005\u0000/1\u0003\f\u0006\u00000,\u0001\u0000\u0000\u00000-\u0001"+
		"\u0000\u0000\u00000.\u0001\u0000\u0000\u00000/\u0001\u0000\u0000\u0000"+
		"1\u0007\u0001\u0000\u0000\u000023\u0005\u0002\u0000\u000034\u0003\u0002"+
		"\u0001\u000045\u0005\u0003\u0000\u00005\t\u0001\u0000\u0000\u000067\u0005"+
		"\u0001\u0000\u000078\u0003\b\u0004\u00008\u000b\u0001\u0000\u0000\u0000"+
		"9:\u0003\u001c\u000e\u0000:;\u0005\u0004\u0000\u0000;<\u0003\u0002\u0001"+
		"\u0000<=\u0005\u0005\u0000\u0000=\r\u0001\u0000\u0000\u0000>?\u0003\u001c"+
		"\u000e\u0000?@\u0005\u001e\u0000\u0000@A\u0003\u0012\t\u0000AI\u0001\u0000"+
		"\u0000\u0000BC\u0003\u001c\u000e\u0000CD\u0005\u001e\u0000\u0000DE\u0003"+
		"\u001a\r\u0000EF\u0005\u001e\u0000\u0000FG\u0003\u0014\n\u0000GI\u0001"+
		"\u0000\u0000\u0000H>\u0001\u0000\u0000\u0000HB\u0001\u0000\u0000\u0000"+
		"I\u000f\u0001\u0000\u0000\u0000JK\u0007\u0000\u0000\u0000K\u0011\u0001"+
		"\u0000\u0000\u0000LM\u0005\u0006\u0000\u0000M\u0013\u0001\u0000\u0000"+
		"\u0000NT\u0005\u0017\u0000\u0000OT\u0005\u0018\u0000\u0000PT\u0005\u0019"+
		"\u0000\u0000QT\u0003\u0018\f\u0000RT\u0003\u0016\u000b\u0000SN\u0001\u0000"+
		"\u0000\u0000SO\u0001\u0000\u0000\u0000SP\u0001\u0000\u0000\u0000SQ\u0001"+
		"\u0000\u0000\u0000SR\u0001\u0000\u0000\u0000T\u0015\u0001\u0000\u0000"+
		"\u0000U[\u0005\u0012\u0000\u0000VZ\b\u0001\u0000\u0000WX\u0005!\u0000"+
		"\u0000XZ\u0005\u0012\u0000\u0000YV\u0001\u0000\u0000\u0000YW\u0001\u0000"+
		"\u0000\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001"+
		"\u0000\u0000\u0000\\^\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000"+
		"^_\u0005\u0012\u0000\u0000_\u0017\u0001\u0000\u0000\u0000`b\u0005\u001d"+
		"\u0000\u0000a`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000ca\u0001"+
		"\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000d\u0019\u0001\u0000\u0000"+
		"\u0000ef\u0007\u0002\u0000\u0000f\u001b\u0001\u0000\u0000\u0000gh\u0005"+
		"\u001f\u0000\u0000hi\u0005\u0014\u0000\u0000im\u0005 \u0000\u0000jl\u0003"+
		"\u001e\u000f\u0000kj\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000ny\u0001\u0000\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000pt\u0005 \u0000\u0000qs\u0003\u001e\u000f"+
		"\u0000rq\u0001\u0000\u0000\u0000sv\u0001\u0000\u0000\u0000tr\u0001\u0000"+
		"\u0000\u0000tu\u0001\u0000\u0000\u0000uy\u0001\u0000\u0000\u0000vt\u0001"+
		"\u0000\u0000\u0000wy\u0005 \u0000\u0000xg\u0001\u0000\u0000\u0000xp\u0001"+
		"\u0000\u0000\u0000xw\u0001\u0000\u0000\u0000y\u001d\u0001\u0000\u0000"+
		"\u0000z{\u0005\u0016\u0000\u0000{|\u0005 \u0000\u0000|\u001f\u0001\u0000"+
		"\u0000\u0000\n$0HSY[cmtx";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}