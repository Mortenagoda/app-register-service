package works.softwarethat.appregistry.cli;

import java.util.Arrays;
import java.util.List;

import works.softwarethat.appregistry.cli.exposure.ApplicationsExposure;
import works.softwarethat.service.general.ApplicationConfig;
import works.softwarethat.service.general.RestHandler;

/**
 * App configuration.
 *
 * @author mortena@gmail.com
 */
public class AppConfig extends ApplicationConfig {
    @Override
    public List<Class<? extends RestHandler>> getHandlers() {
        return Arrays.asList(ApplicationsExposure.class);
    }
}
