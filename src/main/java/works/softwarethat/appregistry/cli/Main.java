package works.softwarethat.appregistry.cli;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import works.softwarethat.service.core.ServiceStarter;

/**
 * Starts the app registry application services.
 *
 * @author mortena@gmail.com
 */

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        ServiceStarter.start(args, vertx, event -> LOGGER.info("Server started on port: " + event.result().actualPort()), new AppConfig());
    }
}
