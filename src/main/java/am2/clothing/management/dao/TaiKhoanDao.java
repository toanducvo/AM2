package am2.clothing.management.dao;

import am2.clothing.management.entity.TaiKhoan;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaiKhoanDao extends AbstractDao {
    private static final Gson gson = new Gson();
    private static final String collectionName = "danhsachtaikhoan";

    private MongoCollection<Document> dsTaiKhoan;

    public TaiKhoanDao(MongoClient client) {
        super(client);
        dsTaiKhoan = db.getCollection(collectionName);
    }

    public boolean dangNhap(String tenDangNhap, String matKhau) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean resultSet = new AtomicBoolean(false);

        dsTaiKhoan.find(Filters.eq("tenDangNhap", tenDangNhap)).subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null) {
                    String jsonString = document.toJson();
                    TaiKhoan taiKhoan = gson.fromJson(jsonString, TaiKhoan.class);
                    if (taiKhoan.getMatKhau().equals(matKhau))
                        resultSet.set(true);
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();
            }

            @Override
            public void onComplete() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return resultSet.get();
    }
}
