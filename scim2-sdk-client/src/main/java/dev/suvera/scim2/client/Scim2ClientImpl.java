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

import java.io.IOException;
import java.util.Collection;

import static dev.suvera.scim2.schema.ScimConstant.*;

/**
 * author: suvera
 * date: 10/17/2020 1:48 PM
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Data
public class Scim2ClientImpl implements Scim2Client {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String endPoint;
    private final OkHttpClient client;
    private Scim2Protocol protocol;

    protected Scim2ClientImpl(String endPoint, OkHttpClient client) throws ScimException {
        endPoint = StringUtils.stripEnd(endPoint, " /");

        this.endPoint = endPoint;
        this.client = client;
        init();
    }

    private void init() throws ScimException {
        this.protocol = new Scim2Protocol(
                ScimResponse.of(doRequest(HttpMethod.GET, PATH_SP, null)),
                ScimResponse.of(doRequest(HttpMethod.GET, PATH_RESOURCETYPES, null)),
                ScimResponse.of(doRequest(HttpMethod.GET, PATH_SCHEMAS, null))
        );
    }

    private Response doRequest(
            HttpMethod method,
            String path,
            Object payload
    ) throws ScimException {
        if (path == null) {
            throw new ScimException("Client Exception, empty Path");
        }
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

        if (!HttpMethod.GET.equals(method)) {
            if (payload != null) {
                String body;
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
                builder.post(RequestBody.create(body, MediaType.parse("application/scim+json")));
            }
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
        patchResponse.setResource(mapToObject(response.getBody(), request.getRecordType()));

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
        return null;
    }

    @Override
    public Schema getSchema(String schemaId) {
        return null;
    }

    @Override
    public SpConfig getSpConfig() {
        return null;
    }

    @Override
    public Collection<ResourceType> getResourceTypes() {
        return null;
    }

    @Override
    public Collection<Schema> getSchemas() {
        return null;
    }
}
