package dev.suvera.scim2.server.service;

import dev.suvera.scim2.schema.data.group.GroupRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.ex.ScimException;

/**
 * author: suvera
 * date: 10/19/2020 12:22 PM
 */
public interface Scim2GroupService {
    GroupRecord createGroup(GroupRecord record) throws ScimException;

    GroupRecord patchGroup(String userId, PatchRequest<GroupRecord> request) throws ScimException;

    GroupRecord replaceGroup(String userId, GroupRecord record) throws ScimException;

    void deleteGroup(String userId) throws ScimException;

    ListResponse<GroupRecord> searchGroup(SearchRequest record) throws ScimException;

    GroupRecord readGroup(String id) throws ScimException;
}
