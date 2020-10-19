package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2SearchService;
import org.springframework.stereotype.Service;

/**
 * author: suvera
 * date: 10/19/2020 4:21 PM
 */
@Service
public class Scim2SearchServiceImpl implements Scim2SearchService {
    @Override
    public ListResponse<?> search(SearchRequest request) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }
}
