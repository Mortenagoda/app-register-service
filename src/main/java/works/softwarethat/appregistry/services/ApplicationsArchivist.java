package works.softwarethat.appregistry.services;

import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import org.mongodb.morphia.Datastore;
import works.softwarethat.appregistry.model.Application;

/**
 * Archivist handling the database of applications.
 *
 * @author mortena@gmail.com
 */
public class ApplicationsArchivist {
    private final Datastore datastore;

    @Inject
    public ApplicationsArchivist(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Application> listApplications() {
        Application application = new Application();
        application.setName("AppRegistry");
        return Arrays.asList(application);
    }
}
