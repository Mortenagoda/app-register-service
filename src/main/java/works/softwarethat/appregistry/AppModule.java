package works.softwarethat.appregistry;

import com.google.inject.AbstractModule;
import org.mongodb.morphia.Datastore;

/**
 * Application module wiring everything up.
 *
 * @author mortena@gmail.com
 */
public class AppModule extends AbstractModule {
    private final MongoProvider mongoProvider;

    public AppModule(MongoProvider mongoProvider) {
        this.mongoProvider = mongoProvider;
    }

    @Override
    protected void configure() {
        bind(Datastore.class).toProvider(mongoProvider);
    }
}
