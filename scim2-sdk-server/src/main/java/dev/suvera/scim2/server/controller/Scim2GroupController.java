package dev.suvera.scim2.server.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.data.group.GroupRecord;
import dev.suvera.scim2.schema.data.misc.PatchRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.enums.SortOrder;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.Scim2Server;
import dev.suvera.scim2.server.service.Scim2GroupService;
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
@RestController("scim2-group")
@RequestMapping(value = "/scim2/Groups", produces = Scim2Server.APPLICATION_JSON)
public class Scim2GroupController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Scim2GroupService service;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody String data) {
        GroupRecord record;
        try {
            record = objectMapper.readValue(data, GroupRecord.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createGroup(record));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceGroup(@PathVariable String id, @RequestBody String data) {
        GroupRecord record;
        try {
            record = objectMapper.readValue(data, GroupRecord.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.ok().body(service.replaceGroup(id, record));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchGroup(@PathVariable String id, @RequestBody String data) {
        PatchRequest<GroupRecord> request;
        try {
            JavaType type = objectMapper.getTypeFactory().
                    constructParametricType(PatchRequest.class, GroupRecord.class);

            request = objectMapper.readValue(data, type);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.patchGroup(id, request));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable String id) {
        try {
            service.deleteGroup(id);
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readGroup(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(service.readGroup(id));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @GetMapping("/.search")
    public ResponseEntity<?> searchGroup(
            @RequestParam(required = false, name = "filter") String filter,
            @RequestParam(required = false, name = "sortBy") String sortBy,
            @RequestParam(required = false, name = "sortOrder") String sortOrder
    ) {
        try {
            SearchRequest search = new SearchRequest();
            search.setFilter(filter);
            search.setSortBy(sortBy);
            if (sortOrder != null) {
                search.setSortOrder(SortOrder.valueOf(sortOrder.toUpperCase()));
            }

            return ResponseEntity.ok().body(service.searchGroup(search));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }
}
