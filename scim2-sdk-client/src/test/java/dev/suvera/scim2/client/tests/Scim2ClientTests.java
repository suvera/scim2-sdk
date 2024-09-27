package dev.suvera.scim2.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * author: suvera
 * date: 10/18/2020 4:25 PM
 */
public class Scim2ClientTests {
    private static MockWebServer server;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void beforeAll() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @Test
    public void testJobdStatus01() throws Exception {
        MockResponse sp1 = new MockResponse().setBody("json_here")
                .setHeader("Content-Type", "application/scim+json;charset=UTF-8");

        MockResponse resources = new MockResponse().setBody("json_here")
                .setHeader("Content-Type", "application/scim+json;charset=UTF-8");

        MockResponse schemas = new MockResponse().setBody("json_here")
                .setHeader("Content-Type", "application/scim+json;charset=UTF-8");


        server.enqueue(sp1);
        server.enqueue(resources);
        server.enqueue(schemas);

        HttpUrl url = server.url("/");
        String endPoint = url.toString();

        System.out.println("testJobdStatus01: endPoint " + endPoint);
    }
}
