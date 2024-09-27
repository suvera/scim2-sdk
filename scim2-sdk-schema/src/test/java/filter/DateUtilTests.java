package filter;

import dev.suvera.scim2.schema.filter.ScimFilterParser;
import dev.suvera.scim2.schema.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateUtilTests {

    @Test
    public void testDateUtilParse() {

        String dateStr = "2020-10-19T08:17:00Z";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(dateStr, sdf.format(DateUtil.parseDate(dateStr)));

        dateStr = "2020-10-19T08:17:00.123Z";
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(dateStr, sdf.format(DateUtil.parseDate(dateStr)));

        dateStr = "2020-10-19T08:17:00.123+05:30Z";
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX'Z'", Locale.ENGLISH);
        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(dateStr, sdf.format(DateUtil.parseDate(dateStr)));

        dateStr = "2020-10-19 08:17:00.123";
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(dateStr, sdf.format(DateUtil.parseDate(dateStr)));

        dateStr = "2020-10-19 08:17:00";
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(dateStr, sdf.format(DateUtil.parseDate(dateStr)));

    }
}
