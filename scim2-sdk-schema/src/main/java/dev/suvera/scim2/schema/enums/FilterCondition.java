package dev.suvera.scim2.schema.enums;

public enum FilterCondition {
    EQUAL, NOT_EQUAL, CONTAINS, STARTS_WITH, ENDS_WITH, GREATER_THAN, LESSER_THAN,
    GREATER_THAN_EQUALS, LESSER_THAN_EQUALS, PRESENT, NONE;

    public static FilterCondition fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        switch (value.toLowerCase()) {
            case "eq":
                return EQUAL;
            case "ne":
                return NOT_EQUAL;
            case "co":
                return CONTAINS;
            case "sw":
                return STARTS_WITH;
            case "ew":
                return ENDS_WITH;
            case "gt":
                return GREATER_THAN;
            case "lt":
                return LESSER_THAN;
            case "ge":
                return GREATER_THAN_EQUALS;
            case "le":
                return LESSER_THAN_EQUALS;
            case "pr":
                return PRESENT;
        }

        throw new IllegalArgumentException("Unknown FilterOperation value: " + value);
    }
}
