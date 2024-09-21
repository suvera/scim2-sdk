package dev.suvera.scim2.example.server.jpa.repo;

import dev.suvera.scim2.example.server.jpa.entity.ScimGroup;
import dev.suvera.scim2.schema.data.group.GroupRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.filter.mysql.MysqlFilterConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ScimGroupDao {
    private final static Logger log = LogManager.getLogger(ScimGroupDao.class);

    @Autowired
    private EntityManager em;

    public SearchResults searchGroups(SearchRequest record) {
        Integer start = record.getStartIndex();
        if (start == null || start < 1) {
            start = 0;
        } else {
            start--;
        }
        record.setStartIndex(start);
        Integer pagesize = record.getCount();
        if (pagesize == null || pagesize < 1) {
            pagesize = 10;
        }
        ListResponse<GroupRecord> response = new ListResponse<>();
        response.setResources(new ArrayList<>());

        String whereClause = "";
        Map<String, Object> binds = Map.of();
        if (record.getFilter() != null && !record.getFilter().isEmpty()) {
            MysqlFilterConverter converter = new MysqlFilterConverter();
            converter.setCaseInsensitive(true);

            converter.convert(record.getFilter(), Map.of(
                    "displayName", "display_name"
            ));

            whereClause = converter.getClause().getWhereClause().toString();
            binds = converter.getClause().getBinds();
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM scim_group ");
        if (!whereClause.isEmpty()) {
            sqlBuilder.append(" WHERE ").append(whereClause);
        }
        sqlBuilder.append(" LIMIT :start, :pagesize");

        String finalQuery = sqlBuilder.toString();
        log.info("Search Query: {}; Binds: {}", finalQuery, binds);

        Query query = em.createNativeQuery(finalQuery, ScimGroup.class);
        binds.forEach(query::setParameter);
        query.setParameter("start", start);
        query.setParameter("pagesize", pagesize);

        @SuppressWarnings("unchecked")
        List<ScimGroup> results = query.getResultList();

        if (results == null) {
            results = new ArrayList<>();
        }

        // Get the total count
        sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) FROM scim_group ");
        if (!whereClause.isEmpty()) {
            sqlBuilder.append(" WHERE ").append(whereClause);
        }
        query = em.createNativeQuery(sqlBuilder.toString(), Long.class);
        binds.forEach(query::setParameter);
        int count = ((Number) query.getSingleResult()).intValue();

        return new SearchResults(results, count, start, pagesize);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchResults {
        private List<ScimGroup> results;
        private int totalResults;
        private int startIndex;
        private int itemsPerPage;
    }
}
