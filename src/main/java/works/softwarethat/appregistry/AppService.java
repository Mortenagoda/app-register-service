package works.softwarethat.appregistry;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import works.softwarethat.appregistry.rest.handlers.ApplicationsHandler;
import works.softwarethat.appregistry.services.ApplicationsArchivist;

/**
 * The actual service application.
 *
 * @author mortena@gmail.com
 */
public class AppService {
    private final AppParameters appParameters;

    public AppService(AppParameters appParameters) {
        this.appParameters = appParameters;
    }

    public void start() {
        MongoProvider mongoProvider = new MongoProvider(appParameters.getMongoHost(), appParameters.getMongoPort(), "AppRegistry");

        AppModule appModule = new AppModule(mongoProvider);
        Injector injector = Guice.createInjector(appModule);

        ApplicationsArchivist instance = injector.getInstance(ApplicationsArchivist.class);
        System.out.println("Got archivist: " + instance);

        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);

        HttpServer httpServer =
            vertx.createHttpServer(new HttpServerOptions().setPort(appParameters.getServerPort()).setHost(appParameters.getServerHost()));

        Router router = Router.router(vertx);
        router.route().handler(io.vertx.ext.web.handler.CorsHandler.create("*")
            .allowedMethod(HttpMethod.GET)
            .allowedMethod(HttpMethod.PUT)
            .allowedMethod(HttpMethod.POST)
            .allowedMethod(HttpMethod.DELETE)
            .allowedMethod(HttpMethod.HEAD)
        );

        ApplicationsHandler applicationsHandler = injector.getInstance(ApplicationsHandler.class);
        router.route(applicationsHandler.getPath()).handler(applicationsHandler);

        httpServer.requestHandler(router::accept)
            .listen();
    }

}
