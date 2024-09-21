package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.NullObject;
import dev.suvera.scim2.schema.enums.FilterCondition;
import dev.suvera.scim2.schema.enums.FilterOperation;
import dev.suvera.scim2.schema.enums.ValueType;
import dev.suvera.scim2.schema.filter.data.AttributeExpression;
import dev.suvera.scim2.schema.filter.parser.SCIMFilterBaseListener;
import dev.suvera.scim2.schema.filter.parser.SCIMFilterParser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Iterator;

class ScimFilterListenerInternal extends SCIMFilterBaseListener {
    private final static boolean DEBUG = false;

    private final ScimFilterListener listener;

    private final ArrayDeque<String> attributes = new ArrayDeque<>();
    private final ArrayDeque<Object> values = new ArrayDeque<>();
    private final ArrayDeque<FilterCondition> operators = new ArrayDeque<>();
    private final ArrayDeque<Boolean> notFilters = new ArrayDeque<>();

    private final ArrayDeque<String> parentAttrNames = new ArrayDeque<>();
    private final ArrayDeque<Boolean> hasParentAttrName = new ArrayDeque<>();

    public ScimFilterListenerInternal(ScimFilterListener listener) {
        this.listener = listener;
    }

    @Override
    public void enterExpression(SCIMFilterParser.ExpressionContext ctx) {
        super.enterExpression(ctx);
        if (DEBUG) System.out.println("enterExpression: " + ctx.getText());
    }

    @Override
    public void exitExpression(SCIMFilterParser.ExpressionContext ctx) {
        super.exitExpression(ctx);
        if (DEBUG) System.out.println("exitExpression: " + ctx.getText());
    }

    @Override
    public void enterFilter(SCIMFilterParser.FilterContext ctx) {
        super.enterFilter(ctx);
        if (DEBUG) System.out.println("enterFilter: " + ctx.getText());
    }

    @Override
    public void exitFilter(SCIMFilterParser.FilterContext ctx) {
        super.exitFilter(ctx);
        if (DEBUG) System.out.println("exitFilter: " + ctx.getText());
    }

    @Override
    public void enterFilterExpression(SCIMFilterParser.FilterExpressionContext ctx) {
        super.enterFilterExpression(ctx);
        if (DEBUG) System.out.println("enterFilterExpression: " + ctx.getText());
    }

    @Override
    public void exitFilterExpression(SCIMFilterParser.FilterExpressionContext ctx) {
        super.exitFilterExpression(ctx);
        if (DEBUG) System.out.println("exitFilterExpression: " + ctx.getText());
    }

    @Override
    public void enterFilterOperator(SCIMFilterParser.FilterOperatorContext ctx) {
        super.enterFilterOperator(ctx);
        if (DEBUG) System.out.println("enterFilterOperator: " + ctx.getText());
    }

    @Override
    public void exitFilterOperator(SCIMFilterParser.FilterOperatorContext ctx) {
        super.exitFilterOperator(ctx);
        if (DEBUG) System.out.println("exitFilterOperator: " + ctx.getText());
        listener.onLogicalOperator(FilterOperation.fromString(ctx.getText()));
    }

    @Override
    public void enterValuePresent(SCIMFilterParser.ValuePresentContext ctx) {
        super.enterValuePresent(ctx);
        if (DEBUG) System.out.println("enterValuePresent: " + ctx.getText());
    }

    @Override
    public void exitValuePresent(SCIMFilterParser.ValuePresentContext ctx) {
        super.exitValuePresent(ctx);
        if (DEBUG) System.out.println("exitValuePresent: " + ctx.getText());
        operators.push(FilterCondition.PRESENT);
        values.push(NullObject.INSTANCE);
    }

    @Override
    public void enterNotFilter(SCIMFilterParser.NotFilterContext ctx) {
        super.enterNotFilter(ctx);
        if (DEBUG) System.out.println("enterNotFilter: " + ctx.getText());
        notFilters.push(true);
        listener.onOpenParenthesis(true);
    }

    @Override
    public void exitNotFilter(SCIMFilterParser.NotFilterContext ctx) {
        super.exitNotFilter(ctx);
        if (DEBUG) System.out.println("exitNotFilter: " + ctx.getText());
        boolean negated = false;
        if (!notFilters.isEmpty()) {
            negated = notFilters.pop();
        }
        listener.onCloseParenthesis(negated);
    }

    // ------------ AttrExp ------------
    @Override
    public void enterAttrExp(SCIMFilterParser.AttrExpContext ctx) {
        super.enterAttrExp(ctx);
        if (DEBUG) System.out.println("enterAttrExp: " + ctx.getText());
    }

    @Override
    public void exitAttrExp(SCIMFilterParser.AttrExpContext ctx) {
        super.exitAttrExp(ctx);
        if (DEBUG) System.out.println("exitAttrExp: " + ctx.getText());

        AttributeExpression a = new AttributeExpression();
        if (operators.isEmpty()) {
            System.err.println("No operator found when ending the attribute expression");
            return;
        }
        String attrName = attributes.pop();
        a.setAttribute(attrName);

        if (!operators.isEmpty()) {
            a.setOperator(operators.pop());
        }

        // If there are no values, skip it
        if (values.isEmpty()) {
            listener.onAttributeExpression(a);
            return;
        }
        Object val = values.pop();
        a.setValueType(ValueType.STRING);
        if (val instanceof NullObject) {
            a.setValue(null);
            a.setValueType(ValueType.NULL);
        } else {
            a.setValue(val);
            if (val instanceof Boolean) {
                a.setValueType(ValueType.BOOLEAN);
            } else if (val instanceof Integer) {
                a.setValueType(ValueType.INTEGER);
            } else if (val instanceof Double || val instanceof Float) {
                a.setValueType(ValueType.DECIMAL);
            } else if (val instanceof Date) {
                a.setValueType(ValueType.DATE_TIME);
            }
        }
        listener.onAttributeExpression(a);
    }

    // ------------ AttrPath ------------
    @Override
    public void enterAttrPath(SCIMFilterParser.AttrPathContext ctx) {
        super.enterAttrPath(ctx);
        if (DEBUG) System.out.println("enterAttrPath: " + ctx.getText());
    }

    @Override
    public void exitAttrPath(SCIMFilterParser.AttrPathContext ctx) {
        super.exitAttrPath(ctx);
        if (DEBUG) System.out.println("exitAttrPath: " + ctx.getText());
        if (!hasParentAttrName.isEmpty()) {
            hasParentAttrName.clear();
            parentAttrNames.push(ctx.getText());
            return;
        }

        String name = ctx.getText();
        if (!parentAttrNames.isEmpty()) {
            Iterator<String> it = parentAttrNames.descendingIterator();
            while (it.hasNext()) {
                //noinspection StringConcatenationInLoop
                name = it.next() + "." + name;
            }
        }
        attributes.push(name);
    }

    // ------------ AttrVal ------------
    @Override
    public void enterCompareOp(SCIMFilterParser.CompareOpContext ctx) {
        super.enterCompareOp(ctx);
        if (DEBUG) System.out.println("enterCompareOp: " + ctx.getText());
    }

    @Override
    public void exitCompareOp(SCIMFilterParser.CompareOpContext ctx) {
        super.exitCompareOp(ctx);
        if (DEBUG) System.out.println("exitCompareOp: " + ctx.getText());
        String op = ctx.getText();
        operators.push(FilterCondition.fromString(op));
    }

    // ------------ CompValue ------------
    @Override
    public void enterCompValue(SCIMFilterParser.CompValueContext ctx) {
        super.enterCompValue(ctx);
        if (DEBUG) System.out.println("enterCompValue: " + ctx.getText());
    }

    @Override
    public void exitCompValue(SCIMFilterParser.CompValueContext ctx) {
        super.exitCompValue(ctx);
        if (DEBUG) System.out.println("exitCompValue: " + ctx.getText());
        String val = ctx.getText();
        if (val.startsWith("\"") && val.endsWith("\"")) {
            val = val.substring(1, val.length() - 1);
            if (val.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z")) {
                try {
                    Date date = ScimConstant.SCIM_DATE_FORMAT.parse(val);
                    values.push(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                values.push(val);
            }
        } else if (val.equals("true") || val.equals("false")) {
            values.push(Boolean.parseBoolean(val));
        } else if (val.equals("null")) {
            values.push(NullObject.INSTANCE);
        } else if (val.matches("\\d+")) {
            values.push(Integer.parseInt(val));
        } else if (val.matches("\\d+\\.\\d+")) {
            values.push(Double.parseDouble(val));
        } else {
            values.push(val);
        }
    }

    // ------------ ValuePath ---------------------------------------------
    @Override
    public void enterValuePath(SCIMFilterParser.ValuePathContext ctx) {
        super.enterValuePath(ctx);
        if (DEBUG) System.out.println("enterValuePath: " + ctx.getText());
        hasParentAttrName.push(true);
        listener.onOpenParenthesis(false);
    }

    @Override
    public void exitValuePath(SCIMFilterParser.ValuePathContext ctx) {
        super.exitValuePath(ctx);
        if (DEBUG) System.out.println("exitValuePath: " + ctx.getText());
        parentAttrNames.pop();
        listener.onCloseParenthesis(false);
    }

    // ------------ ClosedFilter ---------------------------------------------
    @Override
    public void enterClosedFilter(SCIMFilterParser.ClosedFilterContext ctx) {
        super.enterClosedFilter(ctx);
        if (DEBUG) System.out.println("enterClosedFilter: " + ctx.getText());
    }

    @Override
    public void exitClosedFilter(SCIMFilterParser.ClosedFilterContext ctx) {
        super.exitClosedFilter(ctx);
        if (DEBUG) System.out.println("exitClosedFilter: " + ctx.getText());
    }


    // ------------ StringValue ---------------------------------------------
    @Override
    public void enterStringValue(SCIMFilterParser.StringValueContext ctx) {
        super.enterStringValue(ctx);
        if (DEBUG) System.out.println("enterStringValue: " + ctx.getText());
    }

    @Override
    public void exitStringValue(SCIMFilterParser.StringValueContext ctx) {
        super.exitStringValue(ctx);
        if (DEBUG) System.out.println("exitStringValue: " + ctx.getText());
    }

    // ------------ SubAttr ---------------------------------------------
    @Override
    public void enterSubAttr(SCIMFilterParser.SubAttrContext ctx) {
        super.enterSubAttr(ctx);
        if (DEBUG) System.out.println("enterSubAttr: " + ctx.getText());
    }

    @Override
    public void exitSubAttr(SCIMFilterParser.SubAttrContext ctx) {
        super.exitSubAttr(ctx);
        if (DEBUG) System.out.println("exitSubAttr: " + ctx.getText());
    }

    // ------------ Number ---------------------------------------------
    @Override
    public void enterNumber(SCIMFilterParser.NumberContext ctx) {
        super.enterNumber(ctx);
        if (DEBUG) System.out.println("enterNumber: " + ctx.getText());
    }

    @Override
    public void exitNumber(SCIMFilterParser.NumberContext ctx) {
        super.exitNumber(ctx);
        if (DEBUG) System.out.println("exitNumber: " + ctx.getText());
    }

    // ------------ ErrorNode ---------------------------------------------
    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
        if (DEBUG) System.out.println("visitErrorNode: " + node.getText());
    }

    // ------------ TerminalNode ---------------------------------------------
    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        if (DEBUG) System.out.println("visitTerminal: " + node.getText());
    }
}
