package am2.clothing.management.entity;

public class ChiTietHoaDon {
    private SanPham sanPham;
    private int soLuong;
    private double donGia;
    private double thanhTien = 0.0;

    /**
     * @param sanPham
     * @param soLuong
     * @param donGia
     */
    public ChiTietHoaDon(SanPham sanPham, int soLuong, double donGia) {
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setDonGia(donGia);
        this.thanhTien = this.soLuong * this.donGia;
    }

    /**
     *
     */
    public ChiTietHoaDon() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {

        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "sanPham=" + sanPham.getMaSanPham() + ", soLuong=" + soLuong + ", donGia=" + donGia
                + ", thanhTien=" + thanhTien + '}';
    }
}
