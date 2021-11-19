package am2.clothing.management.helper;

import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class AutoID {
    private static final int increment = 1;
    private static final int idLength = 5;
    private static String id;
    private static String prefix;
    private static MongoCollection<Document> mongoCollection;

    public static String createNewId(MongoCollection<Document> mongoCollection, String key, String prefix) throws Exception {
        setMongoCollection(mongoCollection);
        setPrefix(prefix);
        setId(getLastItem(key));
        return id;
    }

    private static void setId(String lastItem) {
        int inc = parseId(lastItem) + increment;
        String newId = parseString(inc);
        id = prefix + "0".repeat(idLength - newId.length()) + newId;
    }

    private static String getPrefix() {
        return prefix;
    }

    private static void setPrefix(String prefix) {
        AutoID.prefix = prefix;
    }

    private static MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }

    private static void setMongoCollection(MongoCollection<Document> mongoCollection) throws Exception {
        if (mongoCollection == null)
            throw new Exception();
        AutoID.mongoCollection = mongoCollection;
    }

    private static String getLastItem(String key) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<String> result = new AtomicReference<>();

        Document query = Document.parse("{" + "'" + key + "'" + ": -1}");

        mongoCollection.find().sort(query).first().subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null)
                    result.set(document.getString(key));
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                onComplete();
            }

            @Override
            public void onComplete() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return result.get();
    }

    private static int parseId(String id) {
        String[] result = id.split("[^\\d]{1,}");
        return Integer.parseInt(result[1]);
    }

    private static String parseString(int id) {
        return String.valueOf(id);
    }
}
