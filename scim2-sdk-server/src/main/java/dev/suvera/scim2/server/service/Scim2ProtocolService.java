package dev.suvera.scim2.server.service;

import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.builder.DefaultResourceTypes;
import dev.suvera.scim2.schema.builder.DefaultSchemas;
import dev.suvera.scim2.schema.builder.DefaultSpConfig;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.resource.ResourceType;
import dev.suvera.scim2.schema.data.schema.Schema;
import dev.suvera.scim2.schema.data.sp.SpConfig;

/**
 * author: suvera
 * date: 10/19/2020 12:21 PM
 */
public interface Scim2ProtocolService {

    default SpConfig buildServiceProviderConfig() {
        DefaultSpConfig defaultImpl = new DefaultSpConfig();
        return defaultImpl.serviceProviderConfig();
    }

    default ListResponse<ResourceType> buildResourceTypes() {
        DefaultResourceTypes defaultImpl = new DefaultResourceTypes();
        return defaultImpl.resourceTypes();
    }

    default ResourceType buildResourceType(String id) {
        DefaultResourceTypes defaultImpl = new DefaultResourceTypes();
        id = (id != null) ? id.toLowerCase() : "";

        switch (id) {
            case "user":
                return defaultImpl.user();

            case "group":
                return defaultImpl.group();

            case "schema":
                return defaultImpl.schema();

            case "resourcetype":
                return defaultImpl.resourceType();

            case "serviceproviderconfig":
                return defaultImpl.serviceProviderConfig();

            default:
                throw new RuntimeException("Could not find location of ResourceType with id " + id);
        }
    }


    default ListResponse<Schema> buildSchemas() {
        DefaultSchemas defaultImpl = new DefaultSchemas();
        return defaultImpl.schemas();
    }

    default Schema buildSchema(String id) {
        DefaultSchemas defaultImpl = new DefaultSchemas();
        id = (id != null) ? id : "";

        switch (id) {
            case ScimConstant.URN_USER:
                return defaultImpl.user();

            case ScimConstant.URN_ENTERPRISE_USER:
                return defaultImpl.enterpriseUser();

            case ScimConstant.URN_GROUP:
                return defaultImpl.group();

            case ScimConstant.URN_SCHEMA:
                return defaultImpl.schema();

            case ScimConstant.URN_RESOURCE_TYPE:
                return defaultImpl.resourceType();

            case ScimConstant.URN_SP_CONFIG:
                return defaultImpl.serviceProviderConfig();

            default:
                throw new RuntimeException("Could not find location of Schema for id " + id);
        }
    }
}
