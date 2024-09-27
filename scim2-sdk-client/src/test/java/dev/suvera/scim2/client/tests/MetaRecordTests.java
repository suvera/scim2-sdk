package dev.suvera.scim2.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.meta.MetaRecord;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * author: suvera
 * date: 10/19/2020 8:17 AM
 */
@SuppressWarnings("unused")
public class MetaRecordTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testDateStrings() throws Exception {
        String jsonStr = "{\n" +
                         "  \"resourceType\": \"User\",\n" +
                         "  \"created\": \"2020-10-19T08:17:00Z\",\n" +
                         "  \"lastModified\": \"2020-10-19T08:18:00Z\",\n" +
                         "  \"location\": \"http://localhost:8080/scim/v2/Users/123\",\n" +
                         "  \"version\": \"" + UUID.randomUUID().toString() + "\"\n" +
                         "}";

        MetaRecord meta = objectMapper.readValue(jsonStr, MetaRecord.class);

        assertEquals("User", meta.getResourceType());
        assertEquals("http://localhost:8080/scim/v2/Users/123", meta.getLocation());
        assertEquals("2020-10-19T08:17:00Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getCreated()));
        assertEquals("2020-10-19T08:18:00Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getLastModified()));
        assertNotNull(meta.getVersion());

        // Dates with milliseconds
        jsonStr = "{\n" +
                  "  \"resourceType\": \"User\",\n" +
                  "  \"created\": \"2020-10-19T08:17:01.123Z\",\n" +
                  "  \"lastModified\": \"2020-10-19T08:18:02.456Z\",\n" +
                  "  \"location\": \"http://localhost:8080/scim/v2/Users/123\",\n" +
                  "  \"version\": \"" + UUID.randomUUID().toString() + "\"\n" +
                  "}";

        meta = objectMapper.readValue(jsonStr, MetaRecord.class);

        assertEquals("2020-10-19T08:17:01Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getCreated()));
        assertEquals("2020-10-19T08:18:02Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getLastModified()));

        // Dates with milliseconds and timezone
        jsonStr = "{\n" +
                  "  \"resourceType\": \"User\",\n" +
                  "  \"created\": \"2020-10-19T08:17:01.123+05:30Z\",\n" +
                  "  \"lastModified\": \"2020-10-19T08:18:02.456-07:00Z\",\n" +
                  "  \"location\": \"http://localhost:8080/scim/v2/Users/123\",\n" +
                  "  \"version\": \"" + UUID.randomUUID().toString() + "\"\n" +
                  "}";

        meta = objectMapper.readValue(jsonStr, MetaRecord.class);

        assertEquals("2020-10-19T02:47:01Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getCreated()));
        assertEquals("2020-10-19T15:18:02Z", ScimConstant.SCIM_DATE_FORMAT.format(meta.getLastModified()));

        jsonStr = objectMapper.writeValueAsString(meta);
        System.out.println(jsonStr);

    }
}
