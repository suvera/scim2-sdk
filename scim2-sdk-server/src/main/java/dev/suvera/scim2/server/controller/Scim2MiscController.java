package dev.suvera.scim2.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.suvera.scim2.schema.data.misc.BulkRequest;
import dev.suvera.scim2.schema.data.misc.SearchRequest;
import dev.suvera.scim2.schema.ex.ScimException;
import dev.suvera.scim2.server.Scim2Server;
import dev.suvera.scim2.server.service.Scim2BulkService;
import dev.suvera.scim2.server.service.Scim2SearchService;
import dev.suvera.scim2.server.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: suvera
 * date: 10/19/2020 12:23 PM
 */
@SuppressWarnings("unused")
@RestController("scim2-operations")
@RequestMapping(value = "/scim2", produces = Scim2Server.APPLICATION_JSON)
public class Scim2MiscController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Scim2BulkService bulkService;

    @Autowired
    private Scim2SearchService searchService;

    @PostMapping("/.search")
    public ResponseEntity<?> search(@RequestBody String data) {
        SearchRequest request;
        try {
            request = objectMapper.readValue(data, SearchRequest.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(searchService.search(request));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> bulk(@RequestBody String data) {
        BulkRequest request;
        try {
            request = objectMapper.readValue(data, BulkRequest.class);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bulkService.bulk(request));
        } catch (ScimException e) {
            return ResponseUtil.badRequest(e);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e);
        }
    }

}
