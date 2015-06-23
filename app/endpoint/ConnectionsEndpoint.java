package endpoint;

public class ConnectionsEndpoint {
    public final String url;
    public final String username;
    public final String password;

    public ConnectionsEndpoint(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
