package dev.suvera.scim2.server.service;

import dev.suvera.scim2.schema.data.misc.BulkRequest;
import dev.suvera.scim2.schema.data.misc.BulkResponse;
import dev.suvera.scim2.schema.ex.ScimException;

/**
 * author: suvera
 * date: 10/19/2020 12:23 PM
 */
public interface Scim2BulkService {
    BulkResponse bulk(BulkRequest request) throws ScimException;
}
