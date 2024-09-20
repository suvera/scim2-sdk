package filter;

import dev.suvera.scim2.schema.enums.FilterOperation;
import dev.suvera.scim2.schema.filter.ScimFilterListener;
import dev.suvera.scim2.schema.filter.data.AttributeExpression;

import java.util.ArrayList;
import java.util.List;

public class FakeScimFilterListener implements ScimFilterListener {
    public final List<Object> results = new ArrayList<>();

    @Override
    public void onAttributeExpression(AttributeExpression expression) {
        System.out.println("AttributeExpression: " + expression);
        results.add(expression);
    }

    @Override
    public void onOpenParenthesis(boolean negated) {
        System.out.println("OpenParenthesis: " + negated);
        results.add("(" + (negated ? "NOT " : ""));
    }

    @Override
    public void onCloseParenthesis(boolean negated) {
        System.out.println("CloseParenthesis: " + negated);
        results.add((negated ? "NOT " : "") + ")");
    }

    @Override
    public void onLogicalOperator(FilterOperation operation) {
        System.out.println("LogicalOperator: " + operation);
        results.add(operation);
    }
}
