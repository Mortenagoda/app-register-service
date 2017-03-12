package works.softwarethat.service.core;

import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * The basics of any archivist.
 *
 * @author mortena@gmail.com
 */
public abstract class Archivist<T> {
    protected final MongoClient datastore;
    protected final Class<T> type;

    protected Archivist(MongoClient datastore, Class<T> type) {
        this.datastore = datastore;
        this.type = type;
    }

    protected void handleList(AsyncResult<List<JsonObject>> listAsyncResult, Handler<AsyncResult<List<T>>> handler) {
        if (listAsyncResult.succeeded()) {
            AsyncResult<List<T>> mappedResult =
                listAsyncResult.map(jsonObjects -> jsonObjects.stream().map(entries -> entries.mapTo(type)).collect(Collectors.toList()));
            handler.handle(mappedResult);
        } else {
            handler.handle(Future.failedFuture(listAsyncResult.cause()));
        }
    }

    protected void handleOne(AsyncResult<JsonObject> event, Handler<AsyncResult<T>> handler) {
        if (event.succeeded()) {
            handler.handle(event.map(entries -> entries.mapTo(type)));
        } else {
            handler.handle(Future.failedFuture(event.cause()));
        }
    }
}
