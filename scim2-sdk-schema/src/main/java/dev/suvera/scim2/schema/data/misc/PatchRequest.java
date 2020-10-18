package dev.suvera.scim2.schema.data.misc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.BaseRecord;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * author: suvera
 * date: 10/17/2020 12:22 PM
 */
@Data
public class PatchRequest<T extends BaseRecord> {
    private Set<String> schemas = Collections.singleton(ScimConstant.URN_PATCH_OP);
    @JsonIgnore
    private final Class<T> recordType;

    public PatchRequest(Class<T> recordType) {
        this.recordType = recordType;
    }

    @JsonProperty("Operations")
    private List<PatchOperation> operations;
}
