# SCIM 2.0 Java SDK

System for Cross-domain Identity Management (**SCIM**) specification is designed to make managing 
user identities in cloud-based applications and services easier. The specification suite seeks to 
build upon experience with existing schemas and deployments, placing specific emphasis on 
simplicity of development and integration, while applying existing authentication, authorization, and 
privacy models. 

Its intent is to reduce the cost and complexity of user management operations by providing a common 
user schema and extension model, as well as binding documents to provide patterns for exchanging 
this schema using standard protocols. 
In essence: make it fast, cheap, and easy to move users in to, out of, and around the cloud.


**More Info on:**

http://www.simplecloud.info/

https://tools.ietf.org/html/rfc7644

https://tools.ietf.org/html/rfc7643


## Installation

This repo contains three components/libraries **schema**, **client** and **server** that supports scim2 protocol.

These libraries can be included in any of your java applications. 
Use below maven repository in your project.

```
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub suvera Apache Maven Packages</name>
        <url>https://maven.pkg.github.com/suvera/scim2-sdk</url>
    </repository>
</repositories>

```

## scim2-sdk-schema

This library contains SCIM 2.0 protocol definitions, json schemas, resources, and helper utilities. 

```
<dependency>
    <groupId>dev.suvera.scim2</groupId>
    <artifactId>scim2-sdk-schema</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```


## scim2-sdk-client

This library contains SCIM 2.0 compatible Http client.

```
<dependency>
    <groupId>dev.suvera.scim2</groupId>
    <artifactId>scim2-sdk-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```


You can build a **client** object like below.

```
Scim2Client client = new Scim2ClientBuilder("http://localhost:8880/scim2")
    .usernamePassword("username", "password").
    .bearerToken("OR, Bearer Auth token here")
    .allowSelfSigned(true)
    .build()
;


sp = client.getSpConfig();
ressourceTypes = client.getResourceTypes();
schemas = client.getSchemas();


userResourceType = client.getResourceType("urn:ietf:params:scim:schemas:core:2.0:User");
userSchema = client.getSchema("urn:ietf:params:scim:schemas:core:2.0:User");


# User operations
client.createUser(UserRecord record);
client.readUser(String userId);
client.replaceUser(String userId, UserRecord record);
client.deleteUser(String userId);
client.patchUser(String id, PatchRequest<UserRecord> request);

# Group operations
client.createGroup(GroupRecord record);
client.readGroup(String groupId);
client.replaceGroup(String groupId, GroupRecord record);
client.deleteGroup(String groupId);
client.patchGroup(String id, PatchRequest<GroupRecord> request);


# Generic methods - for any custom resource
client.create(T record, ResourceType resourceType);
client.read(String id, Class<T> cls, ResourceType resourceType);
client.replace(String id, T record, ResourceType resourceType);
client.delete(String id, ResourceType resourceType);
client.patch(String id, PatchRequest<T> request, ResourceType resourceType);
client.search(SearchRequest request, Class<T> cls, ResourceType resourceType);


# Root level SCIM Operations
client.bulk(BulkRequest request);
client.search(SearchRequest request);


```

Check the **Scim2Client.java** interface for more information.


## scim2-sdk-server

This is a Spring Boot library, can be added to your boot application like below.

```
<dependency>
    <groupId>dev.suvera.scim2</groupId>
    <artifactId>scim2-sdk-server</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
``` 

- You need to implement interfaces defined in **service** package of scim2-sdk-server.  Check the package code for more details.

- Append **"dev.suvera.scim2.server"** package to **scanBasePackages** config option of SpringBootApplication like below.
         
```
@SpringBootApplication(scanBasePackages = {"dev.suvera.scim2.server", "your packages here ..."})

```

### Example:

There is an example server implementation [scim2-sdk-server-example](scim2-sdk-server-example) for more information.


## Is your server is compliant to SCIM 2.0?

Here is the tool to test the compliance level  https://github.com/suvera/scim2-compliance-test-utility
