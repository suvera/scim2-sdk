package dev.suvera.scim2.server.util;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.ex.ScimException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * author: suvera
 * date: 10/19/2020 2:45 PM
 */
public class ResponseUtil {

    public static ResponseEntity<?> badRequest(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorRecord(400, e.getMessage()));
    }

    public static ResponseEntity<?> badRequest(ScimException e) {
        ErrorRecord er = e.getError();
        if (er == null) {
            er = new ErrorRecord(400, e.getMessage());
        }
        return ResponseEntity.status(er.getStatus()).body(er);
    }

    @NotNull
    public static Set<String> cleanCsvStrings(String attributes) {
        String[] values = attributes.split(",");
        Set<String> attrs = new HashSet<>(Set.of());
        for (String value : values) {
            value = value.trim();
            if (!value.isEmpty()) {
                attrs.add(value);
            }
        }
        return attrs;
    }
}
