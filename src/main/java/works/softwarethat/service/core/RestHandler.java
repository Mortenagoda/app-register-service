package works.softwarethat.service.core;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Base handler for a rest service.
 *
 * @author mortena@gmail.com
 */
public abstract class RestHandler implements Handler<RoutingContext> {
    protected final String path;

    protected RestHandler(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
