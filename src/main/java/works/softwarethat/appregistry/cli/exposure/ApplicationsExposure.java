package works.softwarethat.appregistry.cli.exposure;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import works.softwarethat.appregistry.cli.exposure.model.ApplicationsRepresentation;
import works.softwarethat.service.general.RestHandler;
import works.softwarethat.appregistry.cli.services.ApplicationsArchivist;

/**
 * /applications REST exposure.
 *
 * @author mortena@gmail.com
 */
public class ApplicationsExposure extends RestHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationsExposure.class);
    private final ApplicationsArchivist applicationsArchivist;

    @Inject
    protected ApplicationsExposure(ApplicationsArchivist applicationsArchivist) {
        super("/applications");
        this.applicationsArchivist = applicationsArchivist;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        if (routingContext.request().method() == HttpMethod.GET) {
            applicationsArchivist.listApplications(event -> {
                if (event.succeeded()) {
                    ApplicationsRepresentation apps = new ApplicationsRepresentation();
                    apps.addApplications(event.result());
                    routingContext.response().end(Json.encode(apps));
                } else {
                    LOGGER.error("Error listing applications", event.cause());
                    routingContext.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
                }
            });
        }
    }
}
