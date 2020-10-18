package dev.suvera.scim2.schema.data.meta;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * author: suvera
 * date: 10/16/2020 11:11 PM
 */
@Data
public class MetaRecord {
    private String resourceType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
    private Date lastModified;

    private String location;
    private String version;
}
