package works.softwarethat.service.general;

import java.io.IOException;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.Slf4jLevel;
import de.flapdoodle.embed.process.io.progress.Slf4jProgressListener;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.Logger;

/**
 * Mongo DB runner.
 *
 * @author mortena@gmail.com
 */

public class MongoDBRunner {
    private final String host;
    private final int port;

    public MongoDBRunner(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startMongoDB() throws IOException {
        Logger logger = org.slf4j.LoggerFactory.getLogger(getClass().getName());

        ProcessOutput processOutput = new ProcessOutput(Processors.logTo(logger, Slf4jLevel.INFO), Processors.logTo(logger,
            Slf4jLevel.ERROR), Processors.named("[console>]", Processors.logTo(logger, Slf4jLevel.DEBUG)));

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
            .defaultsWithLogger(Command.MongoD, logger)
            .processOutput(processOutput)
            .artifactStore(new ExtractedArtifactStoreBuilder()
                .defaults(Command.MongoD)
                .download(new DownloadConfigBuilder()
                    .defaultsForCommand(Command.MongoD)
                    .progressListener(new Slf4jProgressListener(logger)))).build();

        MongodStarter starter = MongodStarter.getInstance(runtimeConfig);

        IMongodConfig mongodConfig = new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(host, port, Network.localhostIsIPv6()))
            .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        MongodProcess mongod = mongodExecutable.start();
    }
}
