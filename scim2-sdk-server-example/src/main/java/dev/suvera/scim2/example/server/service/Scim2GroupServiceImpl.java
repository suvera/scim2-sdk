package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.group.GroupRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2GroupService;
import org.springframework.stereotype.Service;

/**
 * author: suvera
 * date: 10/19/2020 4:19 PM
 */
@Service
public class Scim2GroupServiceImpl implements Scim2GroupService {
    @Override
    public GroupRecord createGroup(GroupRecord record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public GroupRecord patchGroup(String userId, PatchRequest<GroupRecord> request) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public GroupRecord replaceGroup(String userId, GroupRecord record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public void deleteGroup(String userId) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public ListResponse<GroupRecord> searchGroup(SearchRequest record) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }

    @Override
    public GroupRecord readGroup(String id) throws ScimException {
        throw new ScimException(new ErrorRecord(501, "Not Supported"));
    }
}
