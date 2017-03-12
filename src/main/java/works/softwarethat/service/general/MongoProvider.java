package works.softwarethat.service.general;

import com.google.inject.Provider;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * MongoDB provider.
 *
 * @author mortena@gmail.com
 */
public class MongoProvider implements Provider<io.vertx.ext.mongo.MongoClient> {
    private final String mongoHost;
    private final int mongoPort;
    private final String dbName;
    private final Vertx vertx;

    public MongoProvider(String mongoHost, int mongoPort, String dbName, Vertx vertx) {
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
        this.dbName = dbName;
        this.vertx = vertx;
    }

    @Override
    public MongoClient get() {
        JsonObject connection = new JsonObject();
        connection.put("host", mongoHost);
        connection.put("port", mongoPort);
        return MongoClient.createShared(vertx, connection, dbName);
    }
}
