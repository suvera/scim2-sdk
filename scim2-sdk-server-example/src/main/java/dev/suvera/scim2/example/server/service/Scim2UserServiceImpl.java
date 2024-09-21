package dev.suvera.scim2.example.server.service;

import dev.suvera.scim2.example.server.jpa.entity.ScimGroup;
import dev.suvera.scim2.example.server.jpa.entity.ScimUser;
import dev.suvera.scim2.example.server.jpa.repo.ScimUserDao;
import dev.suvera.scim2.example.server.jpa.repo.ScimUserRepository;
import dev.suvera.scim2.schema.ScimConstant;
import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.data.misc.ListResponse;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.data.user.UserRecord;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.service.Scim2UserService;
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
public class Scim2UserServiceImpl implements Scim2UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private ScimUserRepository scimUserRepo;

    @Value("${spring.application.scimBaseUrl}")
    private String scimBaseUrl;

    @Autowired
    private ScimUserDao scimUserDao;

    @Transactional
    @Override
    public UserRecord createUser(UserRecord record) throws ScimException {
        ScimUser usr = new ScimUser();

        usr.setUserName(record.getUserName());
        usr.setFormatted(record.getDisplayName());
        usr.setActive(record.isActive());
        usr.setExternalId(record.getExternalId());

        if (record.getName() != null) {
            usr.setFormatted(record.getName().getFormatted());
            usr.setFamilyName(record.getName().getFamilyName());
            usr.setGivenName(record.getName().getGivenName());
            usr.setMiddleName(record.getName().getMiddleName());
            usr.setHonorificPrefix(record.getName().getHonorificPrefix());
            usr.setHonorificSuffix(record.getName().getHonorificSuffix());
        }

        em.persist(usr);
        em.flush();

        record.setId(usr.getId().toString());
        record.getMeta().setCreated(Date.from(usr.getCreatedAt()));
        record.getMeta().setLastModified(Date.from(usr.getUpdatedAt()));
        record.getMeta().setLocation(scimBaseUrl + ScimConstant.PATH_USERS + "/" + usr.getId());
        record.getMeta().setVersion(usr.getUpdatedAt().toString());
        return record;
    }

    @Transactional
    @Override
    public UserRecord patchUser(String userId, PatchRequest<UserRecord> request) throws ScimException {

        // TODO: Implement patchUser

        return readUser(userId);
    }

    @Transactional
    @Override
    public UserRecord replaceUser(String userId, UserRecord record) throws ScimException {
        Optional<ScimUser> optGrp = scimUserRepo.findById(Long.parseLong(userId));

        if (optGrp.isPresent()) {
            ScimUser grp = optGrp.get();
//            grp.setFormatted(record.getName().getFormatted());
//            em.persist(grp);
//            em.flush();
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
    public void deleteUser(String userId) throws ScimException {
        Optional<ScimUser> optGrp = scimUserRepo.findById(Long.parseLong(userId));

        if (optGrp.isPresent()) {
            scimUserRepo.delete(optGrp.get());
        } else {
            throw new ScimException(new ErrorRecord(404, "Not Found"));
        }
    }

    @Override
    public ListResponse<UserRecord> searchUser(SearchRequest record) throws ScimException {
        ListResponse<UserRecord> response = new ListResponse<>();
        response.setResources(new ArrayList<>());

        ScimUserDao.SearchResults optList = scimUserDao.searchUsers(record);
        for (ScimUser usr : optList.getResults()) {
            response.getResources().add(buildUserRecord(usr));
        }
        
        response.setItemsPerPage(optList.getItemsPerPage());
        response.setStartIndex(optList.getStartIndex());
        response.setTotalResults(optList.getTotalResults());
        response.setSchemas(Set.of(ScimConstant.URN_LIST_RESPONSE));
        return response;
    }

    @Override
    public UserRecord readUser(String id) throws ScimException {
        return scimUserRepo.findById(Long.parseLong(id))
                .map(this::buildUserRecord)
                .orElseThrow(() -> new ScimException(new ErrorRecord(404, "Not Found")));
    }

    @NotNull
    private UserRecord buildUserRecord(ScimUser usr) {
        UserRecord record = new UserRecord();
        record.setId(usr.getId().toString());
        record.setDisplayName(usr.getFormatted());
        record.setExternalId(usr.getExternalId());
        record.setUserName(usr.getUserName());
        record.setActive(usr.isActive());

        UserRecord.UserName name = new UserRecord.UserName();
        name.setFormatted(usr.getFormatted());
        name.setFamilyName(usr.getFamilyName());
        name.setGivenName(usr.getGivenName());
        name.setMiddleName(usr.getMiddleName());
        name.setHonorificPrefix(usr.getHonorificPrefix());
        name.setHonorificSuffix(usr.getHonorificSuffix());
        record.setName(name);

        record.getMeta().setCreated(Date.from(usr.getCreatedAt()));
        record.getMeta().setLastModified(Date.from(usr.getUpdatedAt()));
        record.getMeta().setLocation(scimBaseUrl + ScimConstant.PATH_USERS + "/" + usr.getId());
        record.getMeta().setVersion(usr.getUpdatedAt().toString());

        if (record.getGroups() != null) {
            record.setGroups(getUserGroups(usr));
        }

        return record;
    }

    @NotNull
    private List<UserRecord.UserGroup> getUserGroups(ScimUser grp) {
        List<UserRecord.UserGroup> members = new ArrayList<>();
        for (ScimGroup member : grp.getGroups()) {
            UserRecord.UserGroup m = new UserRecord.UserGroup();
            m.setType(ScimConstant.NAME_GROUP);
            m.setDisplay(member.getDisplayName());
            m.setValue(member.getId().toString());
            m.setRef(scimBaseUrl + ScimConstant.PATH_GROUPS + "/" + member.getId());
            members.add(m);
        }
        return members;
    }
}
