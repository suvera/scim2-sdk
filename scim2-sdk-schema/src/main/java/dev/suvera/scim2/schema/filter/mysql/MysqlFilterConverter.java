package dev.suvera.scim2.schema.filter.mysql;

import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.enums.FilterCondition;
import dev.suvera.scim2.schema.enums.FilterOperation;
import dev.suvera.scim2.schema.enums.ValueType;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.schema.filter.BaseDbFilterConverter;
import dev.suvera.scim2.schema.filter.ScimFilterParser;
import dev.suvera.scim2.schema.filter.data.AttributeExpression;
import dev.suvera.scim2.schema.filter.data.DbFilterClause;

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
        String column = this.getMappedColumn(expression);

        if (column == null) {
            ErrorRecord error = new ErrorRecord();
            error.setStatus(400);
            error.setDetail("Filter column '" + expression.getAttribute() + "' is not supported in this context");
            error.setScimType("invalidFilter");
            throw new ScimException(error);
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

                    case DATE_TIME:
                        where.append(column);
                        where.append(compOp).append(bindVar);
                        clause.getBinds().put(bindVar, ScimConstant.READ_DATE_FORMAT.format(value));
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
                        ErrorRecord error = new ErrorRecord();
                        error.setStatus(400);
                        error.setDetail("Filter column '" + expression.getAttribute() + "' has Unsupported value type " + valueType);
                        error.setScimType("invalidFilter");
                        throw new ScimException(error);
                }
                break;

            case CONTAINS:
            case STARTS_WITH:
            case ENDS_WITH:
                if (valueType != ValueType.STRING) {
                    ErrorRecord error = new ErrorRecord();
                    error.setStatus(400);
                    error.setDetail("Filter column '" + expression.getAttribute() + "' has Unsupported value type for operator " + op);
                    error.setScimType("invalidFilter");
                    throw new ScimException(error);
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
                if (valueType != ValueType.INTEGER && valueType != ValueType.DECIMAL && valueType != ValueType.DATE_TIME) {
                    ErrorRecord error = new ErrorRecord();
                    error.setStatus(400);
                    error.setDetail("Filter column '" + expression.getAttribute() + "' has Unsupported value type for operator " + valueType + " -> " + op);
                    error.setScimType("invalidFilter");
                    throw new ScimException(error);
                }

                if (valueType == ValueType.DATE_TIME) {
                    value = ScimConstant.READ_DATE_FORMAT.format(value);
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
                where.append(bindVar);
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
                ErrorRecord error = new ErrorRecord();
                error.setStatus(400);
                error.setDetail("Filter column '" + expression.getAttribute() + "' has Unsupported filter operator " + expression.getOperator());
                error.setScimType("invalidFilter");
                throw new ScimException(error);
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
