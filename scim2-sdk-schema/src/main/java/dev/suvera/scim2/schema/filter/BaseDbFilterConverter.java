package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.filter.data.DbFilterClause;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDbFilterConverter implements DbFilterConverter, ScimFilterListener {
    @Getter
    protected DbFilterClause clause;

    protected Map<String, String> columnMappings = new HashMap<>();

    protected int bindCounter = 1;
    protected final static String BIND_PREFIX = "svr_";

    @Setter
    @Getter
    protected boolean caseInsensitive = false;

}
