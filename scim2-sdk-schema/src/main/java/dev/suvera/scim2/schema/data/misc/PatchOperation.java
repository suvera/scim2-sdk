package dev.suvera.scim2.schema.data.misc;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.suvera.scim2.schema.enums.PatchOp;
import lombok.Data;

/**
 * author: suvera
 * date: 10/17/2020 12:06 PM
 */
@SuppressWarnings("unused")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PatchOperation {
    private PatchOp op;
    private String path;
    private Object value;
}
