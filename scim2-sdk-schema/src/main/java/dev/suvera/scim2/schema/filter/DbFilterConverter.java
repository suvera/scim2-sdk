package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.filter.data.DbFilterClause;

import java.util.Map;

public interface DbFilterConverter {

    void convert(
            String filter,
            Map<String, String> columnMappings
    );

    DbFilterClause getClause();
}
