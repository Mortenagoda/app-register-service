package works.softwarethat.service.core;

/**
 * Application parameters to start the service.
 *
 * @author mortena@gmail.com
 */
public class AppParameters {
    private final String serverHost;
    private final int serverPort;
    private final String mongoHost;
    private final int mongoPort;

    public AppParameters(String serverHost, int serverPort, String mongoHost, int mongoPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public int getMongoPort() {
        return mongoPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }
}
