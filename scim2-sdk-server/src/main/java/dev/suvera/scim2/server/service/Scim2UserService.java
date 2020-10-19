package dev.suvera.scim2.server.service;

import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.data.user.UserRecord;
import dev.suvera.scim2.schema.ex.ScimException;

/**
 * author: suvera
 * date: 10/19/2020 12:22 PM
 */
public interface Scim2UserService {

    UserRecord createUser(UserRecord record) throws ScimException;

    UserRecord patchUser(String userId, PatchRequest<UserRecord> request) throws ScimException;

    UserRecord replaceUser(String userId, UserRecord record) throws ScimException;

    void deleteUser(String userId) throws ScimException;

    ListResponse<UserRecord> searchUser(SearchRequest record) throws ScimException;

    UserRecord readUser(String id) throws ScimException;
}
