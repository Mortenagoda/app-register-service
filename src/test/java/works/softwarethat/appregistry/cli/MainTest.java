package works.softwarethat.appregistry.cli;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import works.softwarethat.appregistry.cli.exposure.model.ApplicationsRepresentation;
import works.softwarethat.service.general.ServiceStarter;

/**
 * Tests the application.
 *
 * @author mortena@gmail.com
 */
@RunWith(VertxUnitRunner.class)
public class MainTest {
    private Vertx vertx = Vertx.vertx();

    @Before
    public void startServer(TestContext testContext) {
        Async async = testContext.async();
        ServiceStarter.start(new String[0], vertx, event -> {
            async.complete();
        }, new AppConfig());
    }

    @After
    public void stopServer(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void testMain(TestContext testContext) throws InterruptedException {
        Async asyncClient = testContext.async();
        Vertx vertx = Vertx.vertx();
        WebClient webClient = WebClient.create(vertx);
        webClient.get(8888, "localhost", "/applications")
            .as(BodyCodec.jsonObject())
            .send(response -> {
                if (response.succeeded()) {
                    ApplicationsRepresentation applicationsRepresentation = response.result().body().mapTo(ApplicationsRepresentation.class);
                    testContext.assertNotNull(applicationsRepresentation);
                    testContext.assertEquals(1, applicationsRepresentation.getApplications().size(), "Length should be one");
                    testContext.assertEquals("AppRegistry", applicationsRepresentation.getApplications().get(0).getName());
                } else {
                    LoggerFactory.getLogger(this.getClass()).error("Unable to get apps", response.cause());
                    testContext.fail("Unable to get applications");
                }
                asyncClient.complete();
            });
    }
}
