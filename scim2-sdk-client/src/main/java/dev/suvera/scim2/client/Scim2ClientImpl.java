package dev.suvera.scim2.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.data.BaseRecord;
import dev.suvera.scim2.schema.data.ScimResponse;
import dev.suvera.scim2.schema.data.misc.*;
import dev.suvera.scim2.schema.data.resource.ResourceType;
import dev.suvera.scim2.schema.data.schema.Schema;
import dev.suvera.scim2.schema.data.sp.SpConfig;
import dev.suvera.scim2.schema.enums.HttpMethod;
import dev.suvera.scim2.schema.enums.ScimOperation;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.schema.util.Scim2Protocol;
import dev.suvera.scim2.schema.util.UrlUtil;
import lombok.Data;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static dev.suvera.scim2.schema.ScimConstant.*;

/**
 * author: suvera
 * date: 10/17/2020 1:48 PM
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Data
public class Scim2ClientImpl implements Scim2Client {
    private final static Logger log = LogManager.getLogger(Scim2ClientImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final boolean DEBUG = false;

    private String endPoint;
    private OkHttpClient client;
    private Scim2Protocol protocol;
    private String spConfigJson;
    private String resourceTypesJson;
    private String schemasJson;

    protected Scim2ClientImpl(String endPoint, OkHttpClient client) throws ScimException {
        init(endPoint, client);
    }

    protected Scim2ClientImpl(
        String endPoint,
        OkHttpClient client,
        String spConfigJson,
        String resourceTypesJson,
        String schemasJson
    ) throws ScimException {
        this.spConfigJson = spConfigJson;
        this.resourceTypesJson = resourceTypesJson;
        this.schemasJson = schemasJson;
        init(endPoint, client);
    }

    private void init(String endPoint, OkHttpClient client) throws ScimException {
        endPoint = StringUtils.stripEnd(endPoint, " /");

        this.endPoint = endPoint;
        this.client = client;

        ScimResponse spResponse;
        try {
            spResponse = ScimResponse.of(doRequest(HttpMethod.GET, PATH_SP, null));
        } catch (ScimException e) {
            if (spConfigJson != null && !spConfigJson.isEmpty()) {
                spResponse = new ScimResponse(200, spConfigJson, Collections.emptyMap());
            } else {
                throw e;
            }
        }

        ScimResponse rtResponse;
        try {
            rtResponse = ScimResponse.of(doRequest(HttpMethod.GET, PATH_RESOURCETYPES, null));
        } catch (ScimException e) {
            if (resourceTypesJson != null && !resourceTypesJson.isEmpty()) {
                rtResponse = new ScimResponse(200, resourceTypesJson, Collections.emptyMap());
            } else {
                throw e;
            }
        }

        ScimResponse schemasResponse;
        try {
            schemasResponse = ScimResponse.of(doRequest(HttpMethod.GET, PATH_SCHEMAS, null));
        } catch (ScimException e) {
            if (schemasJson != null && !schemasJson.isEmpty()) {
                schemasResponse = new ScimResponse(200, schemasJson, Collections.emptyMap());
            } else {
                throw e;
            }
        }

        this.protocol = new Scim2Protocol(spResponse, rtResponse, schemasResponse);
    }

    private Response doRequest(
            HttpMethod method,
            String path,
            Object payload
    ) throws ScimException {
        if (path == null) {
            throw new ScimException("Client Exception, empty Path");
        }

        log.info("Http {} request {}", method, path);
        path = StringUtils.stripStart(path, " /");
        path = "/" + path;

        if (HttpMethod.GET.equals(method)) {
            String query = UrlUtil.queryString(payload);
            if (query != null) {
                path += "?" + query;
            }
        }

        Request.Builder builder = new Request.Builder()
                .url(endPoint + path)
                .header("X-Requested-With", CLIENT_NAME);

        String body = null;
        if (payload != null) {
            if (payload instanceof String) {
                body = payload.toString();
            } else {
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                try {
                    body = objectMapper.writeValueAsString(payload);
                } catch (JsonProcessingException e) {
                    throw new ScimException("Failed whole encoding object to JSON", e);
                }
            }
        }

        if (HttpMethod.DELETE.equals(method)) {
            if (body != null && !body.isEmpty()) {
                builder.delete(RequestBody.create(body, MediaType.parse("application/scim+json")));
            } else {
                builder.header(CONTENT_TYPE, "application/scim+json").delete();
            }
        } else if (HttpMethod.PUT.equals(method)) {
            if (body == null) {
                body = "";
            }
            builder.put(RequestBody.create(body, MediaType.parse("application/scim+json")));
        } else if (HttpMethod.PATCH.equals(method)) {
            if (body == null) {
                body = "";
            }
            builder.patch(RequestBody.create(body, MediaType.parse("application/scim+json")));
        } else if (!HttpMethod.GET.equals(method)) {
            if (body == null) {
                body = "";
            }
            builder.post(RequestBody.create(body, MediaType.parse("application/scim+json")));
        }

        Request request = builder.build();
        Call call = client.newCall(request);
        Response response;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new ScimException("Could not send HTTP request to scim2 service", e);
        }

        return response;
    }

    @Override
    public <T extends BaseRecord> T create(T record, ResourceType resourceType) throws ScimException {
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.POST,
                resourceType.getEndPoint(),
                record
        ));

        protocol.validateResponse(
                ScimOperation.CREATE,
                response,
                resourceType,
                null
        );

        //noinspection unchecked
        return (T) mapToObject(response.getBody(), record.getClass());
    }

    private <T extends BaseRecord> T mapToObject(String value, Class<T> cls) throws ScimException {
        try {
            return objectMapper.readValue(value, cls);
        } catch (JsonProcessingException e) {
            throw new ScimException("Could not map json to object " + cls.getName(), e);
        }
    }

    @Override
    public <T extends BaseRecord> T read(
            String id,
            Class<T> cls,
            ResourceType resourceType
    ) throws ScimException {

        String path = resourceType.getEndPoint();
        path = StringUtils.stripEnd(path, " /");
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.GET,
                path + "/" + id,
                null
        ));

        protocol.validateResponse(
                ScimOperation.READ,
                response,
                resourceType,
                null
        );

        return mapToObject(response.getBody(), cls);
    }

    @Override
    public <T extends BaseRecord> T replace(
            String id,
            T record,
            ResourceType resourceType
    ) throws ScimException {
        String path = resourceType.getEndPoint();
        path = StringUtils.stripEnd(path, " /");
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.PUT,
                path + "/" + id,
                record
        ));

        protocol.validateResponse(
                ScimOperation.REPLACE,
                response,
                resourceType,
                null
        );

        //noinspection unchecked
        return (T) mapToObject(response.getBody(), record.getClass());
    }

    @Override
    public void delete(String id, ResourceType resourceType) throws ScimException {
        String path = resourceType.getEndPoint();
        path = StringUtils.stripEnd(path, " /");
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.DELETE,
                path + "/" + id,
                null
        ));

        protocol.validateResponse(
                ScimOperation.DELETE,
                response,
                resourceType,
                null
        );
    }

    @Override
    public <T extends BaseRecord> PatchResponse<T> patch(
            String id,
            PatchRequest<T> request,
            ResourceType resourceType
    ) throws ScimException {

        if (!protocol.getSp().getPatch().getSupported()) {
            throw new ScimException("Patch Operation is not supported by Service Provider");
        }

        String path = resourceType.getEndPoint();
        path = StringUtils.stripEnd(path, " /");
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.PATCH,
                path + "/" + id,
                request
        ));

        protocol.validateResponse(
                ScimOperation.PATCH,
                response,
                resourceType,
                null
        );

        PatchResponse<T> patchResponse = new PatchResponse<>(request.getRecordType());

        patchResponse.setStatus(response.getCode());
        try {
            patchResponse.setResource(mapToObject(response.getBody(), request.getRecordType()));
        } catch (ScimException e) {
            log.error("Patch request has no Resource received. {}", e.getMessage());
        }

        return patchResponse;
    }

    @Override
    public <T extends BaseRecord> ListResponse<T> search(
            SearchRequest request,
            Class<T> cls,
            ResourceType resourceType
    ) throws ScimException {
        String path = resourceType.getEndPoint();
        path = StringUtils.stripEnd(path, " /");

        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.POST,
                path + PATH_SEARCH,
                request
        ));

        protocol.validateResponse(
                ScimOperation.SEARCH,
                response,
                resourceType,
                cls
        );

        ListResponse<T> listResponse;
        try {
            JavaType type = objectMapper.getTypeFactory().
                    constructParametricType(ListResponse.class, cls);

            listResponse = objectMapper.readValue(response.getBody(), type);
        } catch (JsonProcessingException e) {
            throw new ScimException("Could not parse search response for " + resourceType.getName(), e);
        }

        return listResponse;
    }

    @Override
    public MixedListResponse search(SearchRequest request) throws ScimException {
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.POST,
                PATH_SEARCH,
                request
        ));

        protocol.validateResponse(
                ScimOperation.SEARCH,
                response,
                null,
                null
        );

        try {
            return objectMapper.readValue(response.getBody(), MixedListResponse.class);
        } catch (JsonProcessingException e) {
            throw new ScimException("Could not map json to object for BulkResponse", e);
        }
    }

    @Override
    public BulkResponse bulk(BulkRequest request) throws ScimException {
        if (!protocol.getSp().getBulk().getSupported()) {
            throw new ScimException("Bulk Operation is not supported by Service Provider");
        }
        ScimResponse response = ScimResponse.of(doRequest(
                HttpMethod.POST,
                PATH_BULK,
                request
        ));

        protocol.validateResponse(
                ScimOperation.BULK,
                response,
                null,
                null
        );

        try {
            return objectMapper.readValue(response.getBody(), BulkResponse.class);
        } catch (JsonProcessingException e) {
            throw new ScimException("Could not map json to object for BulkResponse", e);
        }
    }

    @Override
    public ResourceType getResourceType(String schemaId) {
        return protocol.getResourceType(schemaId);
    }

    @Override
    public Schema getSchema(String schemaId) {
        return protocol.getSchema(schemaId);
    }

    @Override
    public SpConfig getSpConfig() {
        return protocol.getSp();
    }

    @Override
    public Collection<ResourceType> getResourceTypes() {
        return protocol.getResourceTypes().values();
    }

    @Override
    public Collection<Schema> getSchemas() {
        return protocol.getSchemas().values();
    }
}
