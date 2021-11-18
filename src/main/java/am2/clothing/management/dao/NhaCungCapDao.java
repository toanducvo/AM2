package am2.clothing.management.dao;

import am2.clothing.management.entity.NhaCungCap;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class NhaCungCapDao extends AbstractDao {
    private Gson gson = new Gson();
    private MongoCollection<Document> nCCCollection;

    public NhaCungCapDao(MongoClient client) {
        super(client);
        nCCCollection = db.getCollection("danhsachnhacungcap");
    }

    public List<NhaCungCap> getListSupllier() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();

        nCCCollection.find().subscribe(new Subscriber<Document>() {

            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {
                String json = t.toJson();
                NhaCungCap nhaCungCap = gson.fromJson(json, NhaCungCap.class);

                nhaCungCaps.add(nhaCungCap);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await();

        return nhaCungCaps;
    }

    public List<NhaCungCap> searchSupplier(String criteria, String info) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<NhaCungCap> nhaCungCaps = new ArrayList<NhaCungCap>();
        nCCCollection.find(Filters.eq(criteria, info)).subscribe(new Subscriber<Document>() {

            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {
                NhaCungCap cap = gson.fromJson(t.toJson(), NhaCungCap.class);
                nhaCungCaps.add(cap);
                this.s.request(1);

            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });
        latch.await();
        return nhaCungCaps;

    }

    public Boolean updateSuppiler(String nccID, NhaCungCap nhaCungCap) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean bl = new AtomicBoolean(false);
        String json = gson.toJson(nhaCungCap);

        nCCCollection.replaceOne(Filters.eq("maNhaCungCap", nccID), Document.parse(json))
                .subscribe(new Subscriber<UpdateResult>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(UpdateResult t) {
                        if (t.getModifiedCount() > 0) {
                            bl.set(true);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }
                });
        latch.await();
        return bl.get();
    }

    public boolean addSuppiler(NhaCungCap nhaCungCap) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean bl = new AtomicBoolean(false);
        String json = gson.toJson(nhaCungCap);
        Document doc = Document.parse(json);

        nCCCollection.insertOne(doc).subscribe(new Subscriber<InsertOneResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(InsertOneResult t) {
                if (t.getInsertedId() != null) {
                    bl.set(true);
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });
        latch.await();
        return bl.get();
    }

    public boolean deleteSuppiler(String nccId) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean bl = new AtomicBoolean(false);
        nCCCollection.deleteOne(Filters.eq("maNhaCungCap", nccId)).subscribe(new Subscriber<DeleteResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(DeleteResult t) {
                if (t.getDeletedCount() != 0) {
                    bl.set(true);
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }

        });
        latch.await();
        return bl.get();
    }

    public List<NhaCungCap> timTheoMaNhaCungCap(String maNhaCungCap) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<NhaCungCap> dsNhaCC = new ArrayList<NhaCungCap>();
        Document doc = Document.parse("{'maNhaCungCap':'" + maNhaCungCap + "'}");
        nCCCollection.find(doc).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {

                String json = t.toJson();
                NhaCungCap nhaCC = gson.fromJson(json, NhaCungCap.class);
                dsNhaCC.add(nhaCC);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();

            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await();

        return dsNhaCC;
    }

    public List<NhaCungCap> timTheoTenNhaCC(String tenNhaCC) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<NhaCungCap> dsNhaCC = new ArrayList<NhaCungCap>();
        Document doc = Document.parse("{'tenNhaCungCap':'" + tenNhaCC + "'}");
        nCCCollection.find(doc).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {

                String json = t.toJson();
                NhaCungCap nhaCC = gson.fromJson(json, NhaCungCap.class);
                dsNhaCC.add(nhaCC);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();

            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await();

        return dsNhaCC;
    }

}
