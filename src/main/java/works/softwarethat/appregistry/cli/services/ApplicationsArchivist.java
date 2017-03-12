package works.softwarethat.appregistry.cli.services;

import java.util.List;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import works.softwarethat.service.core.Archivist;
import works.softwarethat.appregistry.cli.model.Application;

/**
 * Archivist handling the database of applications.
 *
 * @author mortena@gmail.com
 */
public class ApplicationsArchivist extends Archivist {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationsArchivist.class);
    private static final String APP_NAME = "AppRegistry";

    @Inject
    public ApplicationsArchivist(MongoClient datastore) {
        super(datastore, Application.class);

        findApplication(APP_NAME, event -> {
            if (!event.succeeded()) {
                Application thisApplication = new Application();
                thisApplication.setName(APP_NAME);
                addApplication(thisApplication, this::handleAddedThisApp);
            }
        });
    }

    private void handleAddedThisApp(AsyncResult<String> stringAsyncResult) {
        if (stringAsyncResult.failed()) {
            LOGGER.error("Unable to add this application.");
        } else {
            LOGGER.info("Added this application to collection.");
        }
    }

    public void listApplications(Handler<AsyncResult<List<Application>>> handler) {
        datastore.find(Application.class.getSimpleName(), new JsonObject(), listAsyncResult -> handleList(listAsyncResult, handler));
    }

    public void findApplication(String name, Handler<AsyncResult<Application>> handler) {
        datastore.findOne(Application.class.getSimpleName(), new JsonObject(), null, event -> handleOne(event, handler));
    }

    public void addApplication(Application application, Handler<AsyncResult<String>> handler) {
        datastore.save(Application.class.getSimpleName(), JsonObject.mapFrom(application), handler);
    }
}
