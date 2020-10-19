# Example Scim2 Server
Example server for SCIM2

This is a **Spring Boot** application.

## Installation

Download this example code.

```

cd scim2-sdk-server-example

mvn clean install

java -jar target/scim2-sdk-server-example-1.0.0-SNAPSHOT.jar


# Tomcat started on port(s): 8880 (http) with context path ''

```

Open your browser and check the SCIM 2.0 schemas.

http://localhost:8880/scim2/ServiceProviderConfig
http://localhost:8880/scim2/ResourceTypes
http://localhost:8880/scim2/Schemas



#### More information on this example code

Check **pom.xml** for dependencies.

```
<dependency>
    <groupId>dev.suvera.scim2</groupId>
    <artifactId>scim2-sdk-server</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

```

Check **service** package for sample implementations. 

Your Spring Boot application has to implement these basic services.

and also append **"dev.suvera.scim2.server"** package to **scanBasePackages** config option of SpringBootApplication like below.

```
@SpringBootApplication(scanBasePackages = {"dev.suvera.scim2.server", "dev.suvera.scim2.example.server"})

```


