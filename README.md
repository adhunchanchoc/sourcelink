# sourcelink
**[Features](#features)**<br>
**[Technologies](#technologies)**<br>
**[Configuration](#configuration)**<br>
### Features
* Keep track of your downloads independently of the browser(s) that you use.
* Store origin a downloaded file with a single copy-paste operation.
* Convert the original URL to filename-compatible format.
### Technologies
REST Spring Boot Application with Spring Data JPA, Apache Tomcat, SLF4J Logger, JUnit testing. Build with Maven to secure all dependencies. 
Different databases can be used (tested with PostgreSQL, MySQL, H2 in memory).
Using JSON for data manipulation.
### Installation and configuration
Runs on any machine with JVM. Simply Default Tomcat server port is 8080, thus you can interact with the application via localhost:8080. 
For persistent storage, it is required to set access to a database of choice.
Application behavior can be modified by 1) creating/editing application.properties file located in the same folder as the .jar file. 2) The second approach is to run the jar with specific parameters. 3) The third way is by defining and editing certain environment variables.
1. application.properties
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/sampleDBname
spring.datasource.username=foouser
spring.datasource.password=barpassword
</pre>
2. commandline parameters
<pre> java -jar sourcelink-0.0.1-snapshot.jar --spring.datasource.url=jdbc:h2:mem:testdb --server.port=8085</pre>
3. environment variables (TODO in future releases)
<pre>
SPRING_CONFIG_NAME
SPRING_DATASOURCE_URL
</pre>