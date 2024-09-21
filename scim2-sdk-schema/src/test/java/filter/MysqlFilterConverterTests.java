package filter;

import dev.suvera.scim2.schema.filter.data.DbFilterClause;
import dev.suvera.scim2.schema.filter.mysql.MysqlFilterConverter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MysqlFilterConverterTests {

    @Test
    public void testSimpleCase() {
        MysqlFilterConverter c = new MysqlFilterConverter();

        c.setBindCounter(1);
        c.convert("userName eq \"bjensen\"", Map.of("userName", "user_name"));
        DbFilterClause clause = c.getClause();

        assertEquals("user_name = :svr_1", clause.getWhereClause().toString());
        assertEquals(1, clause.getBinds().size());

        c.setBindCounter(1);
        c.convert("meta.lastModified gt \"2011-05-13T04:42:34Z\"", Map.of("meta.lastModified", "last_modified"));
        clause = c.getClause();

        assertEquals("last_modified > :svr_1", clause.getWhereClause().toString());
        assertEquals(1, clause.getBinds().size());
        System.out.println(clause.getBinds());

        c.setBindCounter(1);
        c.convert("emails[type eq \"work\" and value co \"@example.com\"] or ims[type eq \"xmpp\" and value co \"@foo.com\"]",
                Map.of("emails.value", "email_address", "emails.type", "email_type", "ims.type", "im_name", "ims.value", "im_value"));
        clause = c.getClause();

        System.out.println(clause.getWhereClause().toString());
        System.out.println(clause.getBinds());

        assertNotNull(clause.getWhereClause().toString());
        assertEquals("( email_type = :svr_1 AND email_address LIKE :svr_2 )  OR ( im_name = :svr_3 AND im_value LIKE :svr_4 ) ", clause.getWhereClause().toString());
        assertEquals(4, clause.getBinds().size());
    }

}
