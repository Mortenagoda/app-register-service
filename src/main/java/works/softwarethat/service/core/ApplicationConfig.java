package works.softwarethat.service.core;

import java.util.List;

/**
 * Basic application setup.
 * @author mortena@gmail.com
 */
public abstract class ApplicationConfig {

    public abstract List<Class<? extends RestHandler>> getHandlers();
}
