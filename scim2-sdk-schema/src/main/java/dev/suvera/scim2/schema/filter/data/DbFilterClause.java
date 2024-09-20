package dev.suvera.scim2.schema.filter.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DbFilterClause {
    private StringBuilder whereClause = new StringBuilder();
    private Map<String, Object> binds = new HashMap<>();
}
