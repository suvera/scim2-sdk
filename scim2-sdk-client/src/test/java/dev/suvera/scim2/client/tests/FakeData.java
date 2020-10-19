package dev.suvera.scim2.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import dev.suvera.scim2.schema.data.Attribute;
import dev.suvera.scim2.schema.data.resource.ResourceType;
import dev.suvera.scim2.schema.data.schema.Schema;
import dev.suvera.scim2.schema.data.schema.SchemaExtension;
import dev.suvera.scim2.schema.data.user.UserRecord;
import dev.suvera.scim2.schema.util.Xmap;
import org.apache.commons.collections4.IterableUtils;

import java.util.*;

/**
 * author: suvera
 * date: 10/19/2020 8:17 AM
 */
@SuppressWarnings("unused")
public class FakeData {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UserRecord generateUser(
            ResourceType resourceType
    ) {
        Faker faker = new Faker();
        Xmap root = Xmap.q();

        Name name = faker.name();
        root.k("userName", name.username().replaceAll("[^a-z-A-Z_0-9]+", ""));
        root.k("externalId", name.username());

        root.k("displayName", name.name());
        root.k("nickName", faker.funnyName().name());

        root.k("active", true);
        root.k("title", name.title());

        PhoneNumber phone = faker.phoneNumber();
        root.k(
                "phoneNumbers",
                Collections.singletonList(
                        Xmap.q()
                                .k("value", phone.cellPhone())
                                .k("primary", true)
                                .get()
                )
        );

        Address address = faker.address();
        root.k(
                "addresses",
                Collections.singletonList(
                        Xmap.q()
                                .k("formatted", address.fullAddress())
                                .k("streetAddress", address.streetAddress())
                                .k("locality", address.cityName())
                                //.k("region", address.state())
                                .k("postalCode", address.zipCode())
                                .k("country", "Philippines")
                                .k("primary", true)
                                .get()
                )
        );

        root.k(
                "emails",
                Collections.singletonList(
                        Xmap.q()
                                .k("value", name.username() + "@opensource.suvera.dev")
                                .k("primary", true)
                                .get()
                )
        );

        root.k(
                "name",
                Xmap.q()
                        .k("familyName", name.lastName())
                        .k("givenName", name.firstName())
                        .k("formatted", name.fullName())
                        .k("honorificPrefix", name.prefix())
                        .get()
        );

        root.k("password", name.username());

        List<String> schemaList = new ArrayList<>();
        schemaList.add(resourceType.getSchema());
        if (resourceType.getSchemaExtensionObjects() != null) {
            for (SchemaExtension ext : resourceType.getSchemaExtensionObjects()) {
                schemaList.add(ext.getSchema().getId());
                root.k(ext.getSchema().getId(), getExtensionData(ext.getSchema()));
            }
        }
        root.k("schemas", schemaList);

        return objectMapper.convertValue(root.get(), UserRecord.class);
    }

    private static Map<String, Object> getExtensionData(
            Schema schema
    ) {
        Xmap root = getAttributesData(schema.getAttributes());
        return root.get();
    }

    private static Xmap getAttributesData(Collection<Attribute> attributes) {
        Faker faker = new Faker();
        Xmap root = Xmap.q();

        for (Attribute attr : attributes) {
            if (attr.getName().equals("manager")) {
                continue;
            }

            boolean hasEnum = (attr.getCanonicalValues() != null && !attr.getCanonicalValues().isEmpty());

            switch (attr.getType()) {
                case COMPLEX:
                    if (attr.isMultiValued()) {
                        root.k(attr.getName(),
                                Collections.singletonList(getAttributesData(attr.getSubAttributes()).get()));
                    } else {
                        root.k(attr.getName(), getAttributesData(attr.getSubAttributes()).get());
                    }
                    break;

                case BOOLEAN:
                    root.k(attr.getName(), true);
                    break;

                case DECIMAL:
                    if (hasEnum) {
                        //noinspection RedundantCast
                        root.k(attr.getName(), (double) IterableUtils.get(attr.getCanonicalValues(), 0));
                    } else {
                        root.k(attr.getName(), faker.number().randomDouble(2, 0, 100));
                    }
                    break;

                case INTEGER:
                    if (hasEnum) {
                        //noinspection RedundantCast
                        root.k(attr.getName(), (long) IterableUtils.get(attr.getCanonicalValues(), 0));
                    } else {
                        root.k(attr.getName(), faker.number().numberBetween(0, 99999));
                    }
                    break;

                default:
                    if (hasEnum) {
                        root.k(attr.getName(), IterableUtils.get(attr.getCanonicalValues(), 0).toString());
                    } else {
                        if (attr.getName().equals("employeeNumber")) {
                            root.k(attr.getName(), "E" + faker.number().numberBetween(100000, 999999));
                        } else {
                            root.k(attr.getName(), faker.lorem().fixedString(10));
                        }
                    }
                    break;
            }
        }

        return root;
    }
}
