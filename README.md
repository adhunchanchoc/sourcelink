# sourcelink
<p><i>Simple REST application to preserve URL origin of local files</i></p><hr>

**[Features](#features)**<br>
**[Technologies](#technologies)**<br>
**[Configuration](#configuration)**<br>
**[Installation and launch](#installation-and-launch)**<br>
**[How to use](#how-to-use)**<br>
### Features
* Keep track of your downloads independently of the browser(s) that you use.
* Store the origin of a downloaded file with a single copy-paste operation.
* Convert the original URL to filename-compatible format (that can be appended to filename).
### Technologies
REST Spring Boot Application with Spring Data JPA, Apache Tomcat, SLF4J Logger, JUnit testing. Build with Maven to secure all dependencies. 
Different databases can be used (tested with PostgreSQL, MySQL, H2 in memory).
Using JSON for data manipulation.
### Configuration
The application in default configuration is listening on the localhost. Default Tomcat server port is 8080, so that you can interact with the application via localhost:8080.
For persistent storage, it is required to set access to a database of choice.
Application behavior can be modified by 1) creating/editing application.properties file located in the same folder as the .jar file. 2) The second approach is to run the jar with specific parameters. 3) The third way is by defining and editing certain environment variables.
1. application.properties (see below for examples of DB configuration)
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/sampleDBname
spring.datasource.username=foouser
spring.datasource.password=barpassword
</pre>
2. commandline parameters
<pre> java -jar sourcelink-0.0.1-snapshot.jar --spring.datasource.url=jdbc:h2:mem:testdb --server.address=127.0.0.1 --server.port=8085</pre>
3. environment variables (not implemented yet - coming in future releases)
<pre>
SPRING_CONFIG_NAME
SPRING_DATASOURCE_URL
</pre>
#### Example of PostgreSQL database configuration in application.properties
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/linkdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
</pre>
#### Example of MySQL database configuration in application.properties
<pre>
spring.datasource.url=jdbc:mysql://localhost:3306/linkdb
spring.datasource.username=root
spring.datasource.password=
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
</pre>
### Installation and launch
As a Spring Boot project the application can be run in a standard way directly from command line 
or using executable .jar file.
* GRADLE:
Run directly using Gradle wrapper:
<code>./gradlew bootRun</code><br>
Build using Gradle wrapper and run jar package:
<code>./gradlew build<br>
java -jar build/libs/sourcelink-0.0.1-SNAPSHOT.<br>
</code><br>
* MAVEN:
Run directly using Maven wrapper:
<code>./mvnw clean package</code><br>
Build using Gradle wrapper and run jar package:
<code>./mvnw clean package<br>
java -jar target/sourcelink-0.0.1-SNAPSHOT.jar<br>
</code>

Application termination can be done easily by interrupting the command-line process, for example by <kbd>Ctrl</kbd>+<kbd>C</kbd>.

### How to use
By default, a running instance of the application is ready to use at http://localhost:8080/links (the aggregate root).
Different actions are performed by alternating request methods (POST, GET, PUT, DELETE) and by further specification
of the endpoint after the forward slash / character.  

#### Examples of requests in curl
<pre>
curl -X GET http://localhost:8080/links
curl -X DELETE http://localhost:8080/links/4
curl -X POST http://localhost:8080/links -H 'Content-type:application/json' -d '{"url":"https://www.securesite.com","file":"hashphrases.txt"}' | json
curl -X PUT http://localhost:8080/links/3 -H 'Content-type:application/json' -d '{"url":"https://www.securesite.com","file":"UPDATEDhashphrases.txt"}' | json
</pre>

[//]: # (* GET http://localhost:8080/links <br>)

[//]: # (When GET method is used, this request should return an empty field at start &#40;as there are no links saved&#41;,)

[//]: # (or the full list of stored links &#40;database fields represented as Link objects in JSON format&#41;.)

[//]: # (* GET http://localhost:8080/links/3 <br> Returns a single link with the given index.)
