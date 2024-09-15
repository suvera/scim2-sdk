package dev.suvera.scim2.server.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.data.user.UserRecord;
import dev.suvera.scim2.schema.enums.SortOrder;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.Scim2Server;
import dev.suvera.scim2.server.service.Scim2UserService;
import dev.suvera.scim2.server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author: suvera
 * date: 10/19/2020 12:23 PM
 */
@SuppressWarnings("unused")
@RestController("scim2-user")
@RequestMapping(value = "/scim2/Users", produces = Scim2Server.APPLICATION_JSON)
public class Scim2UserController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Scim2UserService service;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody String data) {
        UserRecord record;
        try {
            record = objectMapper.readValue(data, UserRecord.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(record));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceUser(@PathVariable String id, @RequestBody String data) {
        UserRecord record;
        try {
            record = objectMapper.readValue(data, UserRecord.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.ok().body(service.replaceUser(id, record));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable String id, @RequestBody String data) {
        PatchRequest<UserRecord> request;
        try {
            JavaType type = objectMapper.getTypeFactory().
                    constructParametricType(PatchRequest.class, UserRecord.class);

            request = objectMapper.readValue(data, type);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.patchUser(id, request));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            service.deleteUser(id);
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(service.readUser(id));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false, name = "filter") String filter,
            @RequestParam(required = false, name = "sortBy") String sortBy,
            @RequestParam(required = false, name = "sortOrder") String sortOrder,
            @RequestParam(required = false, name = "attributes") String attributes,
            @RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "count") Integer count
    ) {
        try {
            SearchRequest search = new SearchRequest();
            search.setFilter(filter);
            search.setSortBy(sortBy);
            if (startIndex != null) {
                search.setStartIndex(startIndex);
            }
            if (count != null) {
                search.setCount(count);
            }
            if (attributes != null) {
                search.setAttributes(ResponseUtil.cleanCsvStrings(attributes));
            }
            if (excludedAttributes != null) {
                search.setExcludedAttributes(ResponseUtil.cleanCsvStrings(excludedAttributes));
            }
            if (sortOrder != null) {
                search.setSortOrder(SortOrder.valueOf(sortOrder.toUpperCase()));
            }

            return ResponseEntity.ok().body(service.searchUser(search));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PostMapping("/.search")
    public ResponseEntity<?> searchUser(
            @RequestBody String data
    ) {
        SearchRequest record;
        try {
            record = objectMapper.readValue(data, SearchRequest.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.ok().body(service.searchUser(record));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }
}
