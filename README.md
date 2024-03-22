# sourcelink
<p><i>Simple REST application to preserve URL origin of local files.</i></p><hr>

**[Features](#features)**<br>
**[Technologies](#technologies)**<br>
**[Installation and launch](#installation-and-launch)**<br>
**[How to use](#how-to-use)**<br>
**[Configuration](#configuration)**<br>
### Features
* Keep track of your downloads independently of the browser(s) that you use.
* Store the origin of a downloaded file with a single copy-paste operation.
* Convert the original URL to filename-compatible format (that can be appended to filename).
### Technologies
REST Spring Boot Application with Spring Data JPA, Apache Tomcat, SLF4J Logger, JUnit testing. Build with Maven to secure all dependencies. 
Different databases can be used (tested with PostgreSQL, MySQL, H2 in memory).
Using JSON for data manipulation.
### Installation and launch
To build a .jar needed for deployment, you can use Maven or its wrapper in the following way.
<code>./mvnw clean package</code><br>

A) Docker with PostgreSQL included
* For convenience, you can use <code>docker compose up</code> to spin up the app together with PostgreSQL database.

B) Standard Java execution with a custom database (described in the Configuration section below)
* As a common Spring Boot project, the application can be run in a standard way directly from the command line/terminal 
or using executable .jar file.
<code>java -jar target/sourcelink-YOUR_VERSION.jar</code><br>


Application termination can be done easily by interrupting the command-line process, for example by <kbd>Ctrl</kbd>+<kbd>C</kbd>.
If you prefer the Docker way, you can use <code>docker compose stop</code> or <code>docker compose down</code> respectively.

### How to use
By default, a running instance of the application is ready to use at http://localhost:8080/api/v1/links (the aggregate root).
Different actions are performed by alternating request methods (POST, GET, PUT, DELETE) and by further specification
of the endpoint after the forward slash / character. <br>
For conversion and storing of your URL in a database, paste your URL after this address: http://localhost:8080/api/v1/links/convert/ in your browser. 

#### Example of conversion using /convert/ endpoint
1. browser request: <code> GET http://localhost:8080/api/v1/links/convert/https://www.newegg.com/Black-Friday-Deals-Shop-Now-Worry-Later/EventSaleStore/ID-1133?cm_sp=Homepage-Top2021-_-nepro%2f23-1689-_-%2f%2fpromotions.newegg.com%2fnepro%2f23-1689%2f1920x660.jpg&icid=765861 </code><br>
2. will return: <code> newegg.com_Black-Friday-Deals-Shop-Now-Worry-Later_EventSaleStore_ID-1133 </code><br>
3. which will be saved to the database along with the original URL and the current timestamp 
   <pre>"id": 5,
   "created": "2023-11-19T18:31:04.450161",
   "url": "https://www.newegg.com/Black-Friday-Deals-Shop-Now-Worry-Later/EventSaleStore/ID-1133",
   "file": "newegg.com_Black-Friday-Deals-Shop-Now-Worry-Later_EventSaleStore_ID-1133"</pre>
#### Examples of CRUD requests in curl
<pre>
curl -X GET http://localhost:8080/api/v1/links
curl -X DELETE http://localhost:8080/api/v1/links/4
curl -X POST http://localhost:8080/api/v1/links -H 'Content-type:application/json' -d '{"url":"https://www.securesite.com","file":"hashphrases.txt"}'
curl -X PUT http://localhost:8080/api/v1/links/3 -H 'Content-type:application/json' -d '{"url":"https://www.securesite.com","file":"UPDATEDhashphrases.txt"}'
</pre>
### Configuration
The application in the default configuration is listening on the localhost. Default Tomcat server port is 8080, so that you can interact with the application via localhost:8080.
For persistent storage, it is required to set access to a database of choice.
The database of the defined name and type must exist before the application starts (if not, use: CREATE DATABASE yourDBname;).  
<br>
Application behavior can be modified by 1) creating/editing application.properties file located in the same folder as the .jar file. 2) The second approach is to run the jar with specific parameters. 3) The third way is by defining and editing certain environment variables.
1. application.properties (see below for examples of DB configuration)
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DB_NAME
spring.datasource.username=foouser
spring.datasource.password=barpassword
</pre>
2. commandline parameters
<pre> java -jar sourcelink-YOUR_VERSION.jar --spring.datasource.url=jdbc:h2:mem:testdb --server.address=127.0.0.1 --server.port=8085</pre>
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
</pre>

[//]: # (* GET http://localhost:8080/api/v1/links <br>)

[//]: # (When GET method is used, this request should return an empty field at start &#40;as there are no links saved&#41;,)

[//]: # (or the full list of stored links &#40;database fields represented as Link objects in JSON format&#41;.)

[//]: # (* GET http://localhost:8080/api/v1/links/3 <br> Returns a single weblink with the given index.)
