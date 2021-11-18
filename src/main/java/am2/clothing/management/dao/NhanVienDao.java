package am2.clothing.management.dao;

import am2.clothing.management.entity.NhanVien;
import am2.clothing.management.entity.VaiTro;
import am2.clothing.management.helper.Exportable;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class NhanVienDao extends AbstractDao implements Exportable {
    public static final Gson gson = new Gson();
    private static final String collectionName = "danhsachnhanvien";
    private MongoCollection<Document> dsNhanVien;

    public NhanVienDao(MongoClient client) {
        super(client);
        dsNhanVien = db.getCollection(collectionName);
    }

    public List<NhanVien> layDanhSachNhanVien() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<NhanVien> resultSet = new ArrayList<>();

        dsNhanVien.find().subscribe(new Subscriber<Document>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null) {
                    NhanVien nhanVien = new NhanVien(
                            document.getString("maNhanVien"),
                            document.getString("hoTen"),
                            document.getDate("ngaySinh"),
                            document.getBoolean("gioiTinh"),
                            document.getString("email"),
                            document.getString("soDienThoai"),
                            document.getString("CMND"),
                            document.getString("chucVu").equals("Nhân Viên") ? VaiTro.NHAN_VIEN : VaiTro.QUAN_LY
                    );
                    nhanVien.setNgaySinh(Date.from(Instant.from(nhanVien.getNgaySinh().toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")))));
                    resultSet.add(nhanVien);
                }
                this.subscription.request(1);
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
        return resultSet;
    }

    public boolean themNhanVien(NhanVien nhanVien) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean resultSet = new AtomicBoolean(false);

        String jsonString = gson.toJson(nhanVien);
        Document document = Document.parse(jsonString);
        document.put("chucVu", nhanVien.getChucVu().getChucVu());

        if (timNhanVienTheoMa(nhanVien.getMaNhanVien()) != null)
            return false;

        dsNhanVien.insertOne(document).subscribe(new Subscriber<InsertOneResult>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(InsertOneResult insertOneResult) {
                if (insertOneResult.getInsertedId() != null)
                    resultSet.set(true);
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

    public boolean xoaNhanVien(String maNhanVien) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean resultSet = new AtomicBoolean(false);
        dsNhanVien.deleteOne(Filters.eq("maNhanVien", maNhanVien)).subscribe(new Subscriber<DeleteResult>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(DeleteResult deleteResult) {
                if (deleteResult.getDeletedCount() > 0)
                    resultSet.set(true);
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

    public NhanVien timNhanVienTheoMa(String maNhanVien) throws InterruptedException {
        AtomicReference<NhanVien> resultSet = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        dsNhanVien.find(Filters.eq("maNhanVien", maNhanVien)).first().subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null) {
                    NhanVien nhanVien = new NhanVien(
                            document.getString("maNhanVien"),
                            document.getString("hoTen"),
                            document.getDate("ngaySinh"),
                            document.getBoolean("gioiTinh"),
                            document.getString("email"),
                            document.getString("soDienThoai"),
                            document.getString("CMND"),
                            document.getString("chucVu").equals("Nhân Viên") ? VaiTro.NHAN_VIEN : VaiTro.QUAN_LY
                    );
                    nhanVien.setNgaySinh(Date.from(Instant.from(nhanVien.getNgaySinh().toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")))));
                    resultSet.set(nhanVien);
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

    public NhanVien timNhanVienTheoTen(String tenNhanVien) throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        AtomicReference<NhanVien> resultSet = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        dsNhanVien.find(Filters.eq("hoTen", tenNhanVien)).first().subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null) {
                    NhanVien nhanVien = null;
                    nhanVien = new NhanVien(document.getString("maNhanVien"), document.getString("hoTen"),
                            document.getDate("ngaySinh"), document.getBoolean("gioiTinh"), document.getString("email"),
                            document.getString("soDienThoai"), document.getString("CMND"),
                            document.getString("chucVu").equals("Quản Lý") ? VaiTro.QUAN_LY : VaiTro.NHAN_VIEN);
                    resultSet.set(nhanVien);
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

    public NhanVien timNhanVienTheoSDT(String soDienThoai) throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        AtomicReference<NhanVien> resultSet = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        dsNhanVien.find(Filters.eq("soDienThoai", soDienThoai)).first().subscribe(new Subscriber<Document>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Document document) {
                if (document != null) {
                    NhanVien nhanVien = null;
                    nhanVien = new NhanVien(document.getString("maNhanVien"), document.getString("hoTen"),
                            document.getDate("ngaySinh"), document.getBoolean("gioiTinh"), document.getString("email"),
                            document.getString("soDienThoai"), document.getString("CMND"),
                            document.getString("chucVu").equals("Quản Lý") ? VaiTro.QUAN_LY : VaiTro.NHAN_VIEN);
                    resultSet.set(nhanVien);
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

    public boolean suaNhanVien(String maNhanVien, NhanVien nhanVien) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean resultSet = new AtomicBoolean(false);

        String jsonString = gson.toJson(nhanVien);
        Document document = Document.parse(jsonString);

        dsNhanVien.replaceOne(Filters.eq("maNhanVien", maNhanVien), document).subscribe(new Subscriber<UpdateResult>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(UpdateResult updateResult) {
                if (updateResult.getModifiedCount() > 0)
                    resultSet.set(true);
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

    @Override
    public boolean writeToFile(String fileName, List<?> data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Document> documents = new ArrayList<>();

        data.forEach((item) -> {
            NhanVien nhanVien = (NhanVien) item;
            Document document = Document.parse(gson.toJson(nhanVien));
            document.put("ngaySinh", sdf.format(nhanVien.getNgaySinh()));
            document.put("gioiTinh", nhanVien.isGioiTinh() ? "Nam" : "Nữ");
            document.put("chucVu", nhanVien.getChucVu().getChucVu());
            documents.add(document);
        });

        String jsonData = gson.toJson(documents);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("data/" + fileName + ".json");
            fileOutputStream.write(jsonData.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
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
