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

        c.convert("emails[type eq \"work\" and value co \"@example.com\"] or ims[type eq \"xmpp\" and value co \"@foo.com\"]",
                Map.of("emails.value", "email_address", "emails.type", "email_type", "ims.type", "im_name", "ims.value", "im_value"));

        DbFilterClause clause = c.getClause();
        System.out.println(clause.getWhereClause().toString());
        System.out.println(clause.getBinds());

        assertNotNull(clause.getWhereClause().toString());
        //assertEquals("(  AND email_address LIKE :svr_1 )  OR ( im_name = :svr_2 AND  ) ", clause.getWhereClause().toString());
        assertEquals(4, clause.getBinds().size());
    }

}
