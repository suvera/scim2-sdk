package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.data.user.UserRecord;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2UserService;
import org.springframework.stereotype.Service;

/**
 * author: suvera
 * date: 10/19/2020 4:21 PM
 */
@Service
public class Scim2UserServiceImpl implements Scim2UserService {
    @Override
    public UserRecord createUser(UserRecord record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public UserRecord patchUser(String userId, PatchRequest<UserRecord> request) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public UserRecord replaceUser(String userId, UserRecord record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public void deleteUser(String userId) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public ListResponse<UserRecord> searchUser(SearchRequest record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public UserRecord readUser(String id) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }
}
