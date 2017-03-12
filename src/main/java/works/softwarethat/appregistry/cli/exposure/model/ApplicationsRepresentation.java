package works.softwarethat.appregistry.cli.exposure.model;

import java.util.List;
import java.util.stream.Collectors;

import works.softwarethat.appregistry.cli.model.Application;

/**
 * A list of applications.
 * @author mortena@gmail.com
 */
public class ApplicationsRepresentation {
    private List<ApplicationRepresentation> applications;

    public List<ApplicationRepresentation> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationRepresentation> applications) {
        this.applications = applications;
    }

    public void addApplications(List<Application> applications) {
        this.applications = applications.stream().map(ApplicationRepresentation::map).collect(Collectors.toList());
    }
}
