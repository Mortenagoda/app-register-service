package works.softwarethat.appregistry;

import com.google.inject.Provider;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * MongoDB provider.
 *
 * @author mortena@gmail.com
 */
public class MongoProvider implements Provider<Datastore> {
    private final String mongoHost;
    private final int mongoPort;
    private final String dbName;

    public MongoProvider(String mongoHost, int mongoPort, String dbName) {
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
        this.dbName = dbName;
    }

    @Override
    public Datastore get() {
        Morphia morphia = getMorphia();
        return morphia.createDatastore(getMongoClient(), dbName);
    }

    private MongoClient getMongoClient() {
        return new MongoClient(mongoHost, mongoPort);
    }

    private Morphia getMorphia() {
        return new Morphia();
    }
}
