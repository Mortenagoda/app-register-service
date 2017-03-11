package works.softwarethat.appregistry;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * Mongo DB runner.
 *
 * @author MONN@nykredit.dk
 */

public class MongoDBRunner {
    private final String host;
    private final int port;

    public MongoDBRunner(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startMongoDB() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(host, port, Network.localhostIsIPv6()))
            .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MongodProcess mongod = mongodExecutable.start();
    }
}
