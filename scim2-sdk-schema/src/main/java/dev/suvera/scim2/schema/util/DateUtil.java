package dev.suvera.scim2.schema.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    private static final String[] DATE_FORMATS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX'Z'", // ISO 8601 with optional milliseconds and timezone
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", // ISO 8601 with optional milliseconds and timezone
            "yyyy-MM-dd'T'HH:mm:ss'Z'",        // ISO 8601 with Zulu timezone

            "yyyy-MM-dd HH:mm:ss.SSSXXX", // ISO 8601 with optional milliseconds and timezone
            "yyyy-MM-dd HH:mm:ss.SSS", // ISO 8601 with optional milliseconds and timezone
            "yyyy-MM-dd HH:mm:ss"              // Common format with space separator
    };

    public static Date parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sdf.parse(dateString);
            } catch (ParseException e) {
                // Ignore and try the next format
            }
        }

        throw new IllegalArgumentException("Invalid date format: " + dateString);
    }
}
