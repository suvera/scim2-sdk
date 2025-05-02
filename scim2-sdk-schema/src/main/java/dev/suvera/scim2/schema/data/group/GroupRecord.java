package dev.suvera.scim2.schema.data.group;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.BaseRecord;
import dev.suvera.scim2.schema.data.ExtensionRecord;
import dev.suvera.scim2.schema.data.meta.MetaRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: suvera
 * date: 10/17/2020 9:56 AM
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupRecord extends BaseRecord {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private String displayName;
    private List<GroupMember> members;

    public GroupRecord() {
        this.meta = new MetaRecord();
        meta.setResourceType(ScimConstant.NAME_GROUP);

        this.schemas = Set.of(ScimConstant.URN_GROUP);
    }

    @JsonIgnore
    private Map<String, ExtensionRecord> extensions = new HashMap<>();

    @JsonAnySetter
    protected void setExtensions(String key, Object value) {
        ExtensionRecord extension;

        if (value instanceof Map) {
            // Convert raw Map to ExtensionRecord
            extension = MAPPER.convertValue(value, ExtensionRecord.class);
        } else if (value instanceof ExtensionRecord) {
            extension = (ExtensionRecord) value;
        } else {
            throw new IllegalArgumentException("Invalid field provided: " + key);
        }

        extensions.put(key, extension);
        if (this.schemas != null) {
            this.schemas.add(key);
        }
    }

    @JsonAnyGetter
    public Map<String, ExtensionRecord> getExtensions() {
        return extensions;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GroupMember {
        private String type;
        private String display;
        private String value;
        @JsonProperty("$ref")
        private String ref;
    }
}
