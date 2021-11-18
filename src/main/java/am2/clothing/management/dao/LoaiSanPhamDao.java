package am2.clothing.management.dao;

import am2.clothing.management.entity.LoaiSanPham;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoaiSanPhamDao extends AbstractDao {
    private static final Gson GSON = new Gson();
    private MongoCollection<Document> loaiSPCollection;

    public LoaiSanPhamDao(MongoClient client) {
        super(client);
        loaiSPCollection = db.getCollection("danhsachloaisanpham");

    }

    public boolean themLoaiSanPham(LoaiSanPham loaiSanPham) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean rs = new AtomicBoolean(false);
        String json = GSON.toJson(loaiSanPham);
        Document doc = Document.parse(json);

        Publisher<InsertOneResult> pub = loaiSPCollection.insertOne(doc);
        Subscriber<InsertOneResult> sub = new Subscriber<InsertOneResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);

            }

            @Override
            public void onNext(InsertOneResult t) {
                if (t.getInsertedId() != null)
                    rs.set(true);

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
        };
        pub.subscribe(sub);
        latch.await();

        return rs.get();
    }

    public List<LoaiSanPham> layDanhSachLoaiSanPham() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<LoaiSanPham> dSLoaiSanPham = new ArrayList<>();

        loaiSPCollection.find().subscribe(new Subscriber<Document>() {

            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {
                String json = t.toJson();
                LoaiSanPham loaiSanPham = GSON.fromJson(json, LoaiSanPham.class);

                dSLoaiSanPham.add(loaiSanPham);
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

        return dSLoaiSanPham;
    }

    public boolean capNhatLoaiSanPham(String maLoai, LoaiSanPham newLoaiSanPham) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        AtomicBoolean rs = new AtomicBoolean(false);

        String json = GSON.toJson(newLoaiSanPham);
        Document doc = Document.parse(json);

        loaiSPCollection.replaceOne(Filters.eq("maLoai", maLoai), doc).subscribe(new Subscriber<UpdateResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(UpdateResult t) {
                if (t.getUpsertedId() != null)
                    rs.set(true);
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

        return rs.get();

    }

    public boolean xoaLoaiSanPham(String maLoai) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicBoolean rs = new AtomicBoolean(false);

        loaiSPCollection.deleteOne(Filters.eq("maLoai", maLoai)).subscribe(new Subscriber<DeleteResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);

            }

            @Override
            public void onNext(DeleteResult t) {
                if (t.getDeletedCount() != 0)
                    rs.set(true);
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

        return rs.get();
    }

    public List<LoaiSanPham> timTheoMaLoai(String maLoai) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<LoaiSanPham> dsLoaiSP = new ArrayList<LoaiSanPham>();
        Document doc = Document.parse("{'maLoai':'" + maLoai + "'}");
        loaiSPCollection.find(doc).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {

                String json = t.toJson();
                LoaiSanPham loaiSP = GSON.fromJson(json, LoaiSanPham.class);
                dsLoaiSP.add(loaiSP);
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

        return dsLoaiSP;
    }

    public List<LoaiSanPham> timTheoTenLoai(String tenLoai) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<LoaiSanPham> dsLoaiSP = new ArrayList<LoaiSanPham>();
        Document doc = Document.parse("{'tenLoai':'" + tenLoai + "'}");
        loaiSPCollection.find(doc).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {

                String json = t.toJson();
                LoaiSanPham loaiSP = GSON.fromJson(json, LoaiSanPham.class);
                dsLoaiSP.add(loaiSP);
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

        return dsLoaiSP;
    }

}
