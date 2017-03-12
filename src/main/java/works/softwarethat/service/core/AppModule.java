package works.softwarethat.service.core;

import com.google.inject.AbstractModule;
import io.vertx.ext.mongo.MongoClient;

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
        bind(MongoClient.class).toProvider(mongoProvider);
    }
}
