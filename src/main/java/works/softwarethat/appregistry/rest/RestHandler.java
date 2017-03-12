package works.softwarethat.appregistry.rest;

/**
 * @author mortena@gmail.com
 */
public abstract class RestHandler {
    protected final String path;

    protected RestHandler(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
