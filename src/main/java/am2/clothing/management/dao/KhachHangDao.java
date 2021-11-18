package am2.clothing.management.dao;

import am2.clothing.management.entity.KhachHang;
import am2.clothing.management.helper.Exportable;
import am2.clothing.management.helper.AutoID;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class KhachHangDao extends AbstractDao implements Exportable {
    private Gson GSON = new Gson();
    private MongoCollection<Document> khachHangCollection;

    public KhachHangDao(MongoClient client) {
        super(client);
        khachHangCollection = db.getCollection("danhsachkhachhang");
    }

    public boolean themKhachHang(KhachHang khachHang) throws InterruptedException, ParseException {
        CountDownLatch latch = new CountDownLatch(1);

        AtomicBoolean rs = new AtomicBoolean(false);

        String json = GSON.toJson(khachHang);
        Document doc = Document.parse(json);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date admissiondate = dateFormat.parse(khachHang.getNgaySinh().toInstant().toString());
        doc.remove("ngaySinh");
        doc.append("ngaySinh", admissiondate);

        Publisher<InsertOneResult> pub = khachHangCollection.insertOne(doc);
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

    // db.danhsachkhachhang.aggregate([{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:"$ngaySinh"},thang:{$month:"$ngaySinh"},ngay:{$dayOfMonth:"$ngaySinh"}}}])
    public List<KhachHang> layDanhSachKhachHang() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<KhachHang> dSKhachHang = new ArrayList<>();
        Document doc = Document.parse(
                "{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}");

        khachHangCollection.aggregate(Arrays.asList(doc)).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {
//					Date date = t.getDate("ngaySinh");
//					Date a = t.getDate("ngaySinh");
//					// Conversion
//					SimpleDateFormat sdf;
//					sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//					sdf.setTimeZone(TimeZone.getTimeZone("CET"));
//					String text = sdf.format(a);
                t.remove("ngaySinh");
                String json = t.toJson();
                KhachHang khachHang = GSON.fromJson(json, KhachHang.class);
                Date date1 = new Date(t.getInteger("nam") - 1900, t.getInteger("thang"), t.getInteger("ngay") + 1,
                        1 - 10, 40, 30);
                khachHang.setNgaySinh(date1);
                dSKhachHang.add(khachHang);
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
        return dSKhachHang;
    }

    private void createIndex() {

        CountDownLatch latch = new CountDownLatch(1);

        Publisher<String> publisher = khachHangCollection
                .createIndex(Document.parse("{hoTen: \"text\", soDienThoai : \"text\"}"));

        Subscriber<String> subscriber = new Subscriber<String>() {
            public void onSubscribe(Subscription s) {
                s.request(1);

            }

            public void onNext(String t) {

            }

            public void onError(Throwable t) {
                t.printStackTrace();
                onComplete();

            }

            public void onComplete() {
                latch.countDown();
            }
        };

        publisher.subscribe(subscriber);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // db.danhsachkhachhang.createIndex({hoTen: "text"})
//    db.danhsachkhachhang.aggregate([{$match: {$expr: {$eq: ["$maKhachHang", "KH00001"]}}}, 
//    								{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:"$ngaySinh"},thang:{$month:"$ngaySinh"},ngay:{$dayOfMonth:"$ngaySinh"}}}])
    public List<KhachHang> timKhachHangTheoTen(String tuKhoa) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<KhachHang> DSKhachHang = new ArrayList<>();
        createIndex();
        Document doc = Document.parse(
                "{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}");

        khachHangCollection.aggregate(Arrays.asList(
                        Document.parse("{$match: {$expr: {$eq: [\"$hoTen\", \"" + tuKhoa + "\"]}}}"),
                        Document.parse(
                                "{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}")))
                .subscribe(new Subscriber<Document>() {
                    private Subscription s;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        this.s.request(1);
                    }

                    @Override
                    public void onNext(Document t) {
                        t.remove("ngaySinh");
                        String json = t.toJson();
                        KhachHang khachHang = GSON.fromJson(json, KhachHang.class);
                        Date date1 = new Date(t.getInteger("nam") - 1900, t.getInteger("thang"),
                                t.getInteger("ngay") + 1, 1 - 10, 40, 30);
                        khachHang.setNgaySinh(date1);
                        DSKhachHang.add(khachHang);
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
        return DSKhachHang;
    }

    // db.danhsachkhachhang.createIndex({soDienThoai: "text"})
//    public List<KhachHang> timKhachHangTheoSDT(String soDienThoai) throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        List<KhachHang> DSKhachHang = new ArrayList<>();
//        createIndex();
//
//        khachHangCollection
//        .aggregate(Arrays.asList(
//        		Document.parse("{$match: {$expr: {$eq: [\"$soDienThoai\", \""+soDienThoai+"\"]}}}"),
//        		Document.parse("{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}")))
//                .subscribe(new Subscriber<Document>() {
//
//                    private Subscription s;
//
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        this.s = s;
//                        this.s.request(1);
//                    }
//
//                    @Override
//                    public void onNext(Document t) {
//                    	t.remove("ngaySinh");
//                        String json = t.toJson();
//                        KhachHang khachHang = GSON.fromJson(json, KhachHang.class);
//                        Date date1 = new Date(t.getInteger("nam") - 1900, t.getInteger("thang"), t.getInteger("ngay") + 1, 1 - 10, 40, 30);
//                        khachHang.setNgaySinh(date1);
//                        DSKhachHang.add(khachHang);
//                        this.s.request(1);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        t.printStackTrace();
//                        onComplete();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        latch.countDown();
//                    }
//                });
//        latch.await();
//        return DSKhachHang;
//    }

    public List<KhachHang> timKhachHangTheoMa(String maKH) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<KhachHang> DSKhachHang = new ArrayList<>();

        khachHangCollection.aggregate(Arrays.asList(
                        Document.parse("{$match: {$expr: {$eq: [\"$maKhachHang\", \"" + maKH + "\"]}}}"),
                        Document.parse(
                                "{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}")))
                .subscribe(new Subscriber<Document>() {

                    private Subscription s;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        this.s.request(1);
                    }

                    @Override
                    public void onNext(Document t) {
                        t.remove("ngaySinh");
                        String json = t.toJson();
                        KhachHang khachHang = GSON.fromJson(json, KhachHang.class);
                        Date date1 = new Date(t.getInteger("nam") - 1900, t.getInteger("thang"),
                                t.getInteger("ngay") + 1, 1 - 10, 40, 30);
                        khachHang.setNgaySinh(date1);
                        DSKhachHang.add(khachHang);
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
        return DSKhachHang;
    }

    // dang loi
    public boolean capNhatThongTinKhachHang(String maKH, KhachHang khachHang)
            throws InterruptedException, ParseException {

        CountDownLatch latch = new CountDownLatch(1);

        AtomicBoolean rs = new AtomicBoolean(false);

        String json = GSON.toJson(khachHang);
        Document doc = Document.parse(json);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        Date admissiondate = dateFormat.parse(khachHang.getNgaySinh().toInstant().toString());
        doc.remove("ngaySinh");
        doc.append("ngaySinh", admissiondate);
        khachHangCollection.replaceOne(Filters.eq("maKhachHang", maKH), doc).subscribe(new Subscriber<UpdateResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(UpdateResult t) {

                if (t.getModifiedCount() > 0)
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

    public boolean xoaKhachHang(String maKH) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean rs = new AtomicBoolean(false);
        khachHangCollection.deleteOne(Filters.eq("maKhachHang", maKH)).subscribe(new Subscriber<DeleteResult>() {
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

    public KhachHang timKhachHangTheoSDT(String tuKhoa) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
//        List<KhachHang> DSKhachHang = new ArrayList<>();
//        KhachHang khachHang = new KhachHang();
//        createIndex();
        AtomicReference<KhachHang> reference = new AtomicReference<KhachHang>();

        Document doc1 = Document.parse("{$match:{\"soDienThoai\":" + "\"" + tuKhoa + "\"" + "}}");
        Document doc2 = Document.parse(
                "{$project:{_id:0,maKhachHang:1,hoTen:1,soDienThoai:1,gioiTinh:1,email:1,nam:{$year:\"$ngaySinh\"},thang:{$month:\"$ngaySinh\"},ngay:{$dayOfMonth:\"$ngaySinh\"}}}");
        khachHangCollection.aggregate(Arrays.asList(doc1)).subscribe(new Subscriber<Document>() {
            private Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                this.s.request(1);
            }

            @Override
            public void onNext(Document t) {
//                        t.remove("ngaySinh");
//                        String json = t.toJson();
////                        KhachHang khachHang = GSON.fromJson(json, KhachHang.class);
////                        @SuppressWarnings("deprecation")
////    					Date date1 = new Date(t.getInteger("nam") - 1900, t.getInteger("thang")-1, t.getInteger("ngay") + 1);
//                        
//                        khachHang.setNgaySinh(t.getDate("ngaySinh"));
//                        khachHang.setEmail(t.getString("email"));
//                        khachHang.setHoTen(t.getString("hoTen"));
//                        khachHang.setGioiTinh(t.getBoolean("gioiTinh", false));
//                        khachHang.setMaKhachHang(t.getString("maKhachHang"));
//                        khachHang.setSoDienThoai(t.getString("soDienThoai"));
                if (t != null) {
                    KhachHang khachHang = new KhachHang(t.getString("maKhachHang"), t.getString("hoTen"),
                            t.getString("soDienThoai"), t.getBoolean("gioiTinh"), t.getString("email"),
                            t.getDate("ngaySinh"));
                    reference.set(khachHang);
                }
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
        return reference.get();
    }

    public String phatSinhMaKhachHang() {
        try {
            return AutoID.createNewId(khachHangCollection, "maKhachHang", "KH");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean writeToFile(String fileName, List<?> data) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Document> documents = new ArrayList<>();
        data.forEach(
                (item) -> {
                    KhachHang khachHang = (KhachHang) item;
                    Document document = Document.parse(GSON.toJson(khachHang));
                    document.put("gioiTinh", khachHang.isGioiTinh() ? "Nam" : "Nữ");
                    khachHang.setNgaySinh(Date.from(Instant.from(khachHang.getNgaySinh().toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")))));
                    document.put("ngaySinh", sdf.format(khachHang.getNgaySinh()));
                    documents.add(document);
                }
        );

        String jsonData = GSON.toJson(documents);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("data/" + fileName + ".json");
            fileOutputStream.write(jsonData.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException ioException) {
            return false;
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return false;
            }
        }
    }
}
