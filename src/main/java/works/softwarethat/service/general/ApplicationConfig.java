package works.softwarethat.service.general;

import java.util.List;

/**
 * Basic application setup.
 * @author mortena@gmail.com
 */
public abstract class ApplicationConfig {

    public abstract List<Class<? extends RestHandler>> getHandlers();
}
