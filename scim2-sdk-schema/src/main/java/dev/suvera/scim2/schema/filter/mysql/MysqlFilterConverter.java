package dev.suvera.scim2.schema.filter.mysql;

import dev.suvera.scim2.schema.enums.FilterCondition;
import dev.suvera.scim2.schema.enums.FilterOperation;
import dev.suvera.scim2.schema.enums.ValueType;
import dev.suvera.scim2.schema.filter.BaseDbFilterConverter;
import dev.suvera.scim2.schema.filter.ScimFilterParser;
import dev.suvera.scim2.schema.filter.data.AttributeExpression;
import dev.suvera.scim2.schema.filter.data.DbFilterClause;
import lombok.Getter;

import java.util.Map;

public class MysqlFilterConverter extends BaseDbFilterConverter {

    @Override
    public void convert(
            String filter,
            Map<String, String> columnMappings
    ) {
        this.clause = new DbFilterClause();
        if (columnMappings != null) {
            this.columnMappings = columnMappings;
        }

        ScimFilterParser.parse(filter, this);
    }

    @SuppressWarnings("DuplicateExpressions")
    @Override
    public void onAttributeExpression(AttributeExpression expression) {
        String column = this.columnMappings.get(expression.getAttribute());
        if (column == null) {
            column = this.columnMappings.get(expression.getFqdn());
        }

        if (column == null) {
            //column = expression.getAttribute();
            throw new IllegalArgumentException("No mapped db column found for filter column: " + expression.getAttribute());
        }

        StringBuilder where = clause.getWhereClause();
        String bindVar = BIND_PREFIX + bindCounter++;
        Object value = expression.getValue();
        ValueType valueType = expression.getValueType();
        FilterCondition op = expression.getOperator();
        String strVal;

        switch (op) {
            case EQUAL:
            case NOT_EQUAL:
                String compOp = " = :";
                if (op == FilterCondition.NOT_EQUAL) {
                    compOp = " != :";
                }
                switch (valueType) {
                    case STRING:
                        if (this.caseInsensitive) {
                            column = "LOWER(" + column + ")";
                        }
                        where.append(column);
                        where.append(compOp).append(bindVar);
                        clause.getBinds().put(bindVar, value == null ? "" : value.toString().toLowerCase());
                        break;

                    case BOOLEAN:
                    case INTEGER:
                    case DECIMAL:
                        where.append(column);
                        where.append(compOp).append(bindVar);
                        clause.getBinds().put(bindVar, value);
                        break;

                    case NULL:
                        where.append(column);
                        if (op == FilterCondition.NOT_EQUAL) {
                            where.append(" IS NOT NULL");
                        } else {
                            where.append(" IS NULL");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported value type: " + valueType);
                }
                break;

            case CONTAINS:
            case STARTS_WITH:
            case ENDS_WITH:
                if (valueType != ValueType.STRING) {
                    throw new IllegalArgumentException("Unsupported value type for operator: " + op);
                }

                if (this.caseInsensitive) {
                    column = "LOWER(" + column + ")";
                }

                where.append(column);
                where.append(" LIKE :").append(bindVar);
                strVal = (value == null ? "" : value.toString().toLowerCase());
                //noinspection RedundantEscapeInRegexReplacement
                strVal = strVal.replaceAll("%", "\\%");

                if (op == FilterCondition.STARTS_WITH) {
                    strVal = "%" + strVal;
                } else if (op == FilterCondition.ENDS_WITH) {
                    strVal = strVal + "%";
                } else {
                    strVal = "%" + strVal + "%";
                }

                clause.getBinds().put(bindVar, strVal);
                break;

            case LESSER_THAN:
            case GREATER_THAN:
            case LESSER_THAN_EQUALS:
            case GREATER_THAN_EQUALS:
                if (valueType != ValueType.INTEGER && valueType != ValueType.DECIMAL) {
                    throw new IllegalArgumentException("Unsupported value type for operator: " + op);
                }

                where.append(column);
                switch (op) {
                    case LESSER_THAN:
                        where.append(" < :");
                        break;

                    case GREATER_THAN:
                        where.append(" > :");
                        break;

                    case LESSER_THAN_EQUALS:
                        where.append(" <= :");
                        break;

                    case GREATER_THAN_EQUALS:
                        where.append(" >= :");
                        break;
                }
                clause.getBinds().put(bindVar, value);
                break;

            case PRESENT:
                where.append(column);
                switch (valueType) {
                    case STRING:
                        where.append(" IS NOT NULL AND ");
                        where.append(column);
                        where.append(" != ''");
                        break;

                    case INTEGER:
                        where.append(" IS NOT NULL AND ");
                        where.append(column);
                        where.append(" != 0");
                        break;

                    case DECIMAL:
                        where.append(" IS NOT NULL AND ");
                        where.append(column);
                        where.append(" != 0.0");
                        break;

                    default:
                        where.append(" IS NOT NULL");
                }
                where.append(" IS NOT NULL");
                break;

            default:
                throw new IllegalArgumentException("Unsupported filter operator: " + expression.getOperator());
        }
    }

    @Override
    public void onOpenParenthesis(boolean negated) {
        clause.getWhereClause().append(negated ? " NOT ( " : "( ");
    }

    @Override
    public void onCloseParenthesis(boolean negated) {
        clause.getWhereClause().append(" ) ");
    }

    @Override
    public void onLogicalOperator(FilterOperation operation) {
        clause.getWhereClause().append(" ").append(operation.name()).append(" ");
    }
}
