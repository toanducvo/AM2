package am2.clothing.management.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Date ngayTao;
    private double giamGia;
    private double tongTienHoaDon = 0.0;
    private List<ChiTietHoaDon> danhSachChiTietHoaDon;

    public HoaDon() {
        setMaHoaDon("Chưa xác định");
        setKhachHang(new KhachHang());
        setNhanVien(new NhanVien());
        setNgayTao(new Date(System.currentTimeMillis()));
        setGiamGia(0.0);
        setDanhSachChiTietHoaDon(new ArrayList<>());
    }

    /**
     * @param maHoaDon
     */
    public HoaDon(String maHoaDon) {
        setMaHoaDon(maHoaDon);
        setKhachHang(new KhachHang());
        setNhanVien(new NhanVien());
        setNgayTao(new Date(System.currentTimeMillis()));
        setGiamGia(0.0);
        setDanhSachChiTietHoaDon(new ArrayList<>());
    }

    /**
     * @param maHoaDon
     * @param khachHang
     * @param nhanVien
     * @param ngayTao
     * @param giamGia
     * @param danhSachChiTietHoaDon
     * @throws Exception
     */
    public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVien, Date ngayTao, double giamGia,
                  List<ChiTietHoaDon> danhSachChiTietHoaDon) throws Exception {
        setMaHoaDon(maHoaDon);
        setKhachHang(khachHang);
        setNhanVien(nhanVien);
        setNgayTao(ngayTao);
        setGiamGia(giamGia);
        setDanhSachChiTietHoaDon(danhSachChiTietHoaDon);
        this.tongTienHoaDon = this.danhSachChiTietHoaDon.stream().mapToDouble(ChiTietHoaDon::getThanhTien).sum()
                * ((100 - this.giamGia) / 100);
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getTongTienHoaDon() {
        return tongTienHoaDon;
    }

    public List<ChiTietHoaDon> getDanhSachChiTietHoaDon() {
        return danhSachChiTietHoaDon;
    }

    public void setDanhSachChiTietHoaDon(List<ChiTietHoaDon> danhSachChiTietHoaDon) {
        this.danhSachChiTietHoaDon = danhSachChiTietHoaDon;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHoaDon='" + maHoaDon + '\'' + ", khachHang=" + khachHang.getMaKhachHang() + ", nhanVien="
                + nhanVien.getMaNhanVien() + ", ngayTao=" + ngayTao + ", giamGia=" + giamGia + ", tongTienHoaDon="
                + tongTienHoaDon + ", danhSachChiTietHoaDon=" + danhSachChiTietHoaDon + '}';
    }
}
