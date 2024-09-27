package dev.suvera.scim2.schema.data.meta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author: suvera
 * date: 10/16/2020 11:11 PM
 */
@Data
@NoArgsConstructor
public class MetaRecord {
    private String resourceType;

    @JsonIgnore
    private Date created;

    @JsonIgnore
    private Date lastModified;

    private String location;
    private String version;

    public MetaRecord(String resourceType) {
        this.resourceType = resourceType;
    }

    public MetaRecord(String resourceType, String location) {
        this.resourceType = resourceType;
        this.location = location;
    }

    @JsonSetter("created")
    public void setCreatedByStr(String created) {
        this.created = DateUtil.parseDate(created);
    }

    @JsonSetter("lastModified")
    public void setLastModifiedByStr(String lastModified) {
        this.lastModified = DateUtil.parseDate(lastModified);
    }

    @JsonGetter("created")
    public String getCreatedToStr() {
        if (created == null) {
            return null;
        }
        return ScimConstant.SCIM_DATE_FORMAT.format(created);
    }

    @JsonGetter("lastModified")
    public String getLastModifiedToStr() {
        if (lastModified == null) {
            return null;
        }
        return ScimConstant.SCIM_DATE_FORMAT.format(lastModified);
    }

    public MetaRecord(
            String resourceType,
            String location,
            Date created,
            Date lastModified,
            String version
    ) {
        this.resourceType = resourceType;
        this.created = created;
        this.lastModified = lastModified;
        this.location = location;
        this.version = version;
    }
}
