package dev.suvera.scim2.schema.enums;

public enum FilterOperation {
    AND, OR, NONE;

    public static FilterOperation fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        switch (value.toLowerCase()) {
            case "and":
                return AND;
            case "or":
                return OR;
        }

        throw new IllegalArgumentException("Unknown FilterOperation value: " + value);
    }
}
