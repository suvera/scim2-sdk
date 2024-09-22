package dev.suvera.scim2.schema.data.sp;

import dev.suvera.scim2.schema.data.BaseRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * author: suvera
 * date: 10/17/2020 12:42 AM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpConfig extends BaseRecord {
    private String documentationUri = null;
    @NotNull(message = "patch cannot be null")
    private Supported patch;
    @NotNull(message = "bulk cannot be null")
    private Bulk bulk;
    @NotNull(message = "filter cannot be null")
    private Filter filter;
    @NotNull(message = "etag cannot be null")
    private Supported etag;
    @NotNull(message = "changePassword cannot be null")
    private Supported changePassword;
    @NotNull(message = "sort cannot be null")
    private Supported sort;
    private List<AuthenticationScheme> authenticationSchemes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Supported {
        @NotNull(message = "supported cannot be null")
        private Boolean supported;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bulk {
        @NotNull(message = "supported cannot be null")
        private Boolean supported;
        @NotNull(message = "maxOperations cannot be null")
        private Integer maxOperations;
        @NotNull(message = "maxPayloadSize cannot be null")
        private Integer maxPayloadSize;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filter {
        @NotNull(message = "supported cannot be null")
        private Boolean supported;
        @NotNull(message = "maxResults cannot be null")
        private Integer maxResults;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthenticationScheme {
        @NotBlank(message = "type cannot be null")
        private String type;
        @NotBlank(message = "name cannot be null")
        @Nullable
        private String name = null;
        @Nullable
        private String description = null;
        @Nullable
        private String specUri = null;
        @Nullable
        private String documentationUri = null;
        @Nullable
        private Boolean primary = null;
    }
}
