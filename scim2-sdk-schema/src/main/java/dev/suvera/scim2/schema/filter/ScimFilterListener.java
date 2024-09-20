package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.enums.FilterOperation;
import dev.suvera.scim2.schema.filter.data.AttributeExpression;

public interface ScimFilterListener {

    void onAttributeExpression(AttributeExpression expression);

    void onOpenParenthesis(boolean negated);

    void onCloseParenthesis(boolean negated);

    void onLogicalOperator(FilterOperation operation);

}
