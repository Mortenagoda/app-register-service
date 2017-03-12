package works.softwarethat.appregistry.rest.handlers;

import java.util.List;

import com.google.inject.Inject;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import works.softwarethat.appregistry.model.Application;
import works.softwarethat.appregistry.rest.RestHandler;
import works.softwarethat.appregistry.services.ApplicationsArchivist;

/**
 * /applications REST exposure.
 *
 * @author mortena@gmail.com
 */
public class ApplicationsHandler extends RestHandler implements Handler<RoutingContext> {
    private final ApplicationsArchivist applicationsArchivist;

    @Inject
    protected ApplicationsHandler(ApplicationsArchivist applicationsArchivist) {
        super("/applications");
        this.applicationsArchivist = applicationsArchivist;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        if (routingContext.request().method() == HttpMethod.GET) {
            List<Application> applicationsList = applicationsArchivist.listApplications();
            routingContext.response().end(Json.encode(applicationsList));
        }
    }
}
