package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.misc.BulkRequest;
import dev.suvera.scim2.schema.data.misc.BulkResponse;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2BulkService;
import org.springframework.stereotype.Service;

/**
 * author: suvera
 * date: 10/19/2020 3:53 PM
 */
@Service
public class Scim2BulkServiceImpl implements Scim2BulkService {
    @Override
    public BulkResponse bulk(BulkRequest request) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }
}
