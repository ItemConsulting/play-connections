# Play Connections

A Java library to access Connections in a reactive manner. Is primarily created to work with [Play Framework](http://playframework.com), but can work in any context.

## Usage

Read in or set up properties, like this:

```java
Properties properties = new Properties();
properties.setProperty("connections.server.host", "https://connections.myserver.com");
properties.setProperty("connections.server.username", "username");
properties.setProperty("connections.server.password", "password");
```

Create an instence of the [ConnectionsClient](src/main/java/no/item/play/connections/ConnectionsClient.java), select the service, and do an operation on it.

```java
ConnectionsClient client = ConnectionsClient.getInstance(properties);
Promise<List<Blog>> blogPromise = client.blogs().myBlogs();
List<Blog> blogs = blogPromise.get();
```

## Testing Connection to Server

To test the connection to server, you need to supply the system with your credentials.

 1. Create a new file  `src/test/resources/conf/secret.conf`
 2. Add this configuration to the file:

 ```properties
 connections.server.host="https://connections.myserver.com"
 connections.server.username="username"
 connections.server.password="password"
 ```

 3. Run the [ConnectionsTest](src/test/java/no/item/play/connections/ConnectionsTest.java).testConnection unittest.
