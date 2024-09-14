package dev.suvera.scim2.server.controller;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.server.Scim2Server;
import dev.suvera.scim2.server.service.Scim2ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: suvera
 * date: 10/19/2020 12:23 PM
 */
@RestController("scim2-schema")
@RequestMapping(value = "/scim2/", produces = Scim2Server.APPLICATION_JSON)
public class Scim2ProtocolController {

    @Autowired
    private Scim2ProtocolService service;

    @GetMapping("/ServiceProviderConfig")

    public Object getServiceProviderConfig() {
        try {
            return service.buildServiceProviderConfig();
        } catch (Exception e) {
            return new ErrorRecord(400, e.getMessage());
        }
    }

    @GetMapping("/ResourceTypes")
    public Object getResourceTypes() {
        try {
            return service.buildResourceTypes();
        } catch (Exception e) {
            return new ErrorRecord(400, e.getMessage());
        }
    }

    @GetMapping("/Schemas")
    public Object getSchemas() {
        try {
            return service.buildSchemas();
        } catch (Exception e) {
            return new ErrorRecord(400, e.getMessage());
        }
    }

    @GetMapping("/ResourceTypes/{id}")
    public Object getResourceType(@PathVariable String id) {
        try {
            return service.buildResourceType(id);
        } catch (Exception e) {
            return new ErrorRecord(400, e.getMessage());
        }
    }

    @GetMapping("/Schemas/{id}")
    public Object getSchema(@PathVariable String id) {
        try {
            return service.buildSchema(id);
        } catch (Exception e) {
            return new ErrorRecord(400, e.getMessage());
        }
    }
}
