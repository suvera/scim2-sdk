package filter;

import dev.suvera.scim2.schema.filter.ScimFilterParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScimFilterParserTest {

    @Test
    public void testSimpleFilter() {
        String input = "userType eq \"Empl\\\"oyee\" and xxxxxx pr and emails[type eq \"woo rk\" and value co true]";
        input = "emails[type eq \"work\" and value co \"@example.com\"] or  ims[type eq \"xmpp\" and value co \"@foo.com\"]";

        FakeScimFilterListener listener = new FakeScimFilterListener();
        listener.results.clear();
        ScimFilterParser.parse(input, listener);

        assertNotNull(listener.results);

    }


}