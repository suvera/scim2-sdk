package dev.suvera.scim2.schema.filter.data;

import dev.suvera.scim2.schema.enums.FilterCondition;
import dev.suvera.scim2.schema.enums.ValueType;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class AttributeExpression {
    @Nullable
    private String schema = null;
    private String attribute;
    private FilterCondition operator = FilterCondition.EQUAL;
    @Nullable
    private Object value = null;
    private ValueType valueType = ValueType.STRING;

    public String getFqdn() {
        return this.schema == null ? this.attribute : this.schema + ":" + this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
        if (attribute.contains(":")) {
            int pos = attribute.lastIndexOf(":");
            this.schema = attribute.substring(0, pos);
            this.attribute = attribute.substring(pos + 1);
        }
    }

    public String toString() {
        return "AttributeExpression(schema=" + this.getSchema()
               + ", name=" + this.getAttribute()
               + ", operator=" + this.getOperator()
               + ", value=" + this.getValue()
               + ", valueType=" + this.getValueType()
               + ")";
    }
}
