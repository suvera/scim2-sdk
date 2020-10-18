package dev.suvera.scim2.schema.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

/**
 * author: suvera
 * date: 10/17/2020 10:37 AM
 */
@Data
public class ErrorRecord {
    private Set<String> schemas;
    private String scimType;
    private String detail;
    private int status;

    @Override
    @JsonIgnore
    public String toString() {
        return "ScimError{" +
                ", scimType='" + scimType + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                '}';
    }
}
