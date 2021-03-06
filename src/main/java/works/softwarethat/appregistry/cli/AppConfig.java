package works.softwarethat.appregistry.cli;

import java.util.Arrays;
import java.util.List;

import works.softwarethat.appregistry.cli.exposure.ApplicationsExposure;
import works.softwarethat.service.core.ApplicationConfig;
import works.softwarethat.service.core.RestHandler;

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
