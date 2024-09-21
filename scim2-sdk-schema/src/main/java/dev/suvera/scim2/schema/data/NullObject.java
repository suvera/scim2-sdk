package dev.suvera.scim2.schema.data;

public class NullObject {
    public static final NullObject INSTANCE = new NullObject();

    private NullObject() {
    }

    public String toString() {
        return "";
    }
}
