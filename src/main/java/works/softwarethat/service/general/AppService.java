package works.softwarethat.service.general;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import works.softwarethat.appregistry.cli.services.ApplicationsArchivist;

/**
 * The actual service application.
 *
 * @author mortena@gmail.com
 */
public class AppService {
    private static final String DB_NAME = "AppRegistry";
    private final AppParameters appParameters;
    private final Vertx vertx;
    private final List<Class<? extends RestHandler>> restHandlers;

    public AppService(AppParameters appParameters, Vertx vertx, List<Class<? extends RestHandler>> restHandlers) {
        this.vertx = vertx;
        this.appParameters = appParameters;
        this.restHandlers = restHandlers;
    }

    public void start(Handler<AsyncResult<HttpServer>> httpServerListenHandler) {

        MongoProvider mongoProvider = new MongoProvider(appParameters.getMongoHost(), appParameters.getMongoPort(), DB_NAME, vertx);

        AppModule appModule = new AppModule(mongoProvider);
        Injector injector = Guice.createInjector(appModule);

        ApplicationsArchivist instance = injector.getInstance(ApplicationsArchivist.class);

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

        restHandlers.forEach(aClass -> {
            RestHandler restHandler = injector.getInstance(aClass);
            router.route(restHandler.getPath()).handler(restHandler);
        });

        httpServer.requestHandler(router::accept)
            .listen(httpServerListenHandler);
    }
}
