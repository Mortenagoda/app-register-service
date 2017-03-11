package works.softwarethat.appregistry;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts the app registry application services.
 *
 * @author MONN@nykredit.dk
 */

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Options options = new Options();
        Option help = Option.builder("h")
            .argName("help")
            .desc("Prints out this help")
            .build();
        Option mongoHost = Option.builder("mh")
            .longOpt("mongo-host")
            .argName("mongo-host")
            .hasArg()
            .desc("MongoDB host")
            .build();
        Option mongoPort = Option.builder("mp")
            .longOpt("mongo-port")
            .argName("mongo-port")
            .hasArg()
            .desc("MongoDB port")
            .type(Integer.class)
            .build();
        Option connectToMongoDB = Option.builder("mc")
            .longOpt("mongo-connect")
            .argName("mongo-connect")
            .desc("Telss application server to connect to a remote mongo db.")
            .build();
        options.addOption(mongoHost);
        options.addOption(mongoPort);
        options.addOption(connectToMongoDB);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine program = parser.parse(options, args);
            if (program.hasOption(help.getOpt())) {
                printHelp(options);
            }
            String mHost = program.getOptionValue(mongoPort.getOpt());
            Integer mPort = (Integer) program.getParsedOptionValue(mongoPort.getOpt());
            if (mHost == null) {
                mHost = "localhost";
            }
            if (mPort == null) {
                mPort = 27017;
            }
            boolean isConnectToMongoDB = program.hasOption(connectToMongoDB.getOpt());
            if (!isConnectToMongoDB) {
                MongoDBRunner mongoDBRunner = new MongoDBRunner(mHost, mPort);
                try {
                    mongoDBRunner.startMongoDB();
                } catch (IOException e) {
                    LOGGER.error("Starting mongodb failed.", e);
                    System.out.println("Unable to start mongo db.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid arguments for program.");
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java " + Main.class.getName(), options);
    }
}
