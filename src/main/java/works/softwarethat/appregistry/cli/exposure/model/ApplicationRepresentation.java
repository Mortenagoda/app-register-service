package works.softwarethat.appregistry.cli.exposure.model;

import works.softwarethat.appregistry.cli.model.Application;

/**
 * Representation of an application.
 *
 * @author mortena@gmail.com
 */
public class ApplicationRepresentation {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ApplicationRepresentation map(Application application) {
        ApplicationRepresentation applicationRepresentation = new ApplicationRepresentation();
        applicationRepresentation.setName(application.getName());
        return applicationRepresentation;
    }
}
