package am2.clothing.management.entity;

import java.util.ArrayList;
import java.util.List;

public class SanPham {
    private String maSanPham;
    private LoaiSanPham loaiSanPham;
    private NhaCungCap nhaCungCap;
    private String tenSanPham;
    private double gia;
    private int soLuong;
    private List<String> danhSachKichCo;
    private String thuongHieu;
    private List<String> danhSachMauSac;
    private String hinhAnh;

    public SanPham() {
        setMaSanPham("Chưa xác định");
        setLoaiSanPham(new LoaiSanPham());
        setNhaCungCap(new NhaCungCap());
        setTenSanPham("Chưa xác định");
        setGia(0.0);
        setSoLuong(0);
        setDanhSachKichCo(new ArrayList<>());
        setThuongHieu("Chưa xác định");
        setDanhSachMauSac(new ArrayList<>());
        setHinhAnh("Chưa xác định");
    }

    /**
     * @param maSanPham
     */
    public SanPham(String maSanPham) {
        setMaSanPham(maSanPham);
        setLoaiSanPham(new LoaiSanPham());
        setNhaCungCap(new NhaCungCap());
        setTenSanPham("Chưa xác định");
        setGia(0.0);
        setSoLuong(0);
        setDanhSachKichCo(new ArrayList<>());
        setThuongHieu("Chưa xác định");
        setDanhSachMauSac(new ArrayList<>());
        setHinhAnh("Chưa xác định");
    }

    /**
     * @param maSanPham
     * @param loaiSanPham
     * @param nhaCungCap
     * @param tenSanPham
     * @param gia
     * @param soLuong
     * @param danhSachKichCo
     * @param thuongHieu
     * @param danhSachMauSac
     * @param hinhAnh
     */
    public SanPham(String maSanPham, LoaiSanPham loaiSanPham, NhaCungCap nhaCungCap, String tenSanPham, double gia,
                   int soLuong, List<String> danhSachKichCo, String thuongHieu, List<String> danhSachMauSac, String hinhAnh) {
        setMaSanPham(maSanPham);
        setLoaiSanPham(loaiSanPham);
        setNhaCungCap(nhaCungCap);
        setTenSanPham(tenSanPham);
        setGia(gia);
        setSoLuong(soLuong);
        setDanhSachKichCo(danhSachKichCo);
        setThuongHieu(thuongHieu);
        setDanhSachMauSac(danhSachMauSac);
        setHinhAnh(hinhAnh);
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public List<String> getDanhSachKichCo() {
        return danhSachKichCo;
    }

    public void setDanhSachKichCo(List<String> danhSachKichCo) {
        this.danhSachKichCo = danhSachKichCo;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public List<String> getDanhSachMauSac() {
        return danhSachMauSac;
    }

    public void setDanhSachMauSac(List<String> danhSachMauSac) {
        this.danhSachMauSac = danhSachMauSac;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public String toString() {
        return "SanPham{" + "maSanPham='" + maSanPham + '\'' + ", loaiSanPham=" + loaiSanPham + ", nhaCungCap="
                + nhaCungCap + ", tenSanPham='" + tenSanPham + '\'' + ", gia=" + gia + ", soLuong=" + soLuong
                + ", danhSachKichCo=" + danhSachKichCo + ", thuongHieu='" + thuongHieu + '\'' + ", danhSachMauSac="
                + danhSachMauSac + ", hinhAnh='" + hinhAnh + '\'' + '}';
    }
}
