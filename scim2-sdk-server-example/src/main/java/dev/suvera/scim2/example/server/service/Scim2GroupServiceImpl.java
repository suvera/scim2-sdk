package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.example.server.jpa.entity.ScimGroup;
import dev.suvera.scim2.example.server.jpa.entity.ScimUser;
import dev.suvera.scim2.example.server.jpa.repo.ScimGroupDao;
import dev.suvera.scim2.example.server.jpa.repo.ScimGroupRepository;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.group.GroupRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2GroupService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * author: suvera
 * date: 10/19/2020 4:19 PM
 */
@Service
public class Scim2GroupServiceImpl implements Scim2GroupService {

    @Autowired
    private EntityManager em;

    @Autowired
    private ScimGroupRepository scimGroupRepo;

    @Value("${spring.application.scimBaseUrl}")
    private String scimBaseUrl;

    @Autowired
    private ScimGroupDao scimGroupDao;

    @Transactional
    @Override
    public GroupRecord createGroup(GroupRecord record) throws ScimException {
        ScimGroup grp = new ScimGroup();
        grp.setDisplayName(record.getDisplayName());
        em.persist(grp);
        em.flush();

        record.setId(grp.getId().toString());
        record.getMeta().setCreated(Date.from(grp.getCreatedAt()));
        record.getMeta().setLastModified(Date.from(grp.getUpdatedAt()));
        record.getMeta().setLocation(scimBaseUrl + ScimConstant.PATH_GROUPS + "/" + grp.getId());
        record.getMeta().setVersion(grp.getUpdatedAt().toString());
        return record;
    }

    @Transactional
    @Override
    public GroupRecord patchGroup(String groupId, PatchRequest<GroupRecord> request) throws ScimException {

        // TODO: Implement patchGroup

        return readGroup(groupId);
    }

    @Transactional
    @Override
    public GroupRecord replaceGroup(String groupId, GroupRecord record) throws ScimException {
        Optional<ScimGroup> optGrp = scimGroupRepo.findById(Long.parseLong(groupId));

        if (optGrp.isPresent()) {
            ScimGroup grp = optGrp.get();
            grp.setDisplayName(record.getDisplayName());
            em.persist(grp);
            em.flush();

            record.setId(grp.getId().toString());
            record.getMeta().setCreated(Date.from(grp.getCreatedAt()));
            record.getMeta().setLastModified(Date.from(grp.getUpdatedAt()));
            record.getMeta().setLocation(scimBaseUrl + ScimConstant.PATH_GROUPS + "/" + grp.getId());
            record.getMeta().setVersion(grp.getUpdatedAt().toString());

            return record;
        } else {
            throw new ScimException(new ErrorRecord(404, "Not Found"));
        }
    }

    @Transactional
    @Override
    public void deleteGroup(String groupId) throws ScimException {
        Optional<ScimGroup> optGrp = scimGroupRepo.findById(Long.parseLong(groupId));

        if (optGrp.isPresent()) {
            scimGroupRepo.delete(optGrp.get());
        } else {
            throw new ScimException(new ErrorRecord(404, "Not Found"));
        }
    }

    @Override
    public ListResponse<GroupRecord> searchGroup(SearchRequest record) throws ScimException {
        ListResponse<GroupRecord> response = new ListResponse<>();
        response.setResources(new ArrayList<>());

        ScimGroupDao.SearchResults optList = scimGroupDao.searchGroups(record);
        for (ScimGroup usr : optList.getResults()) {
            response.getResources().add(buildGroupRecord(usr));
        }

        response.setItemsPerPage(optList.getItemsPerPage());
        response.setStartIndex(optList.getStartIndex());
        response.setTotalResults(optList.getTotalResults());
        response.setSchemas(Set.of(ScimConstant.URN_LIST_RESPONSE));
        return response;
    }

    @Override
    public GroupRecord readGroup(String id) throws ScimException {
        return scimGroupRepo.findById(Long.parseLong(id))
                .map(this::buildGroupRecord)
                .orElseThrow(() -> new ScimException(new ErrorRecord(404, "Not Found")));
    }

    @NotNull
    private GroupRecord buildGroupRecord(ScimGroup grp) {
        GroupRecord record = new GroupRecord();
        record.setId(grp.getId().toString());
        record.setDisplayName(grp.getDisplayName());

        record.getMeta().setCreated(Date.from(grp.getCreatedAt()));
        record.getMeta().setLastModified(Date.from(grp.getUpdatedAt()));
        record.getMeta().setLocation(scimBaseUrl + ScimConstant.PATH_GROUPS + "/" + grp.getId());
        record.getMeta().setVersion(grp.getUpdatedAt().toString());

        if (record.getMembers() != null) {
            record.setMembers(getGroupMembers(grp));
        }

        return record;
    }

    @NotNull
    private List<GroupRecord.GroupMember> getGroupMembers(ScimGroup grp) {
        List<GroupRecord.GroupMember> members = new ArrayList<>();
        for (ScimUser member : grp.getMembers()) {
            GroupRecord.GroupMember m = new GroupRecord.GroupMember();
            m.setType(ScimConstant.NAME_USER);
            m.setDisplay(member.getFormatted());
            m.setValue(member.getId().toString());
            m.setRef(scimBaseUrl + ScimConstant.PATH_USERS + "/" + member.getId());
            members.add(m);
        }
        return members;
    }
}
