package am2.clothing.management.entity;

import java.util.Date;

public class KhachHang {
    private String maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private boolean gioiTinh;
    private String email;
    private Date ngaySinh;

    public KhachHang() {
        setMaKhachHang("Chưa xác định");
        setHoTen("Chưa xác định");
        setSoDienThoai("Chưa xác định");
        setGioiTinh(false);
        setEmail("Chưa xác định");
        setNgaySinh(new Date(System.currentTimeMillis()));
    }

    /**
     * @param maKhachHang
     */
    public KhachHang(String maKhachHang) {
        setMaKhachHang(maKhachHang);
        setHoTen("Chưa xác định");
        setSoDienThoai("Chưa xác định");
        setGioiTinh(false);
        setEmail("Chưa xác định");
        setNgaySinh(new Date(System.currentTimeMillis()));
    }

    /**
     * @param maKhachHang
     * @param hoTen
     * @param soDienThoai
     * @param gioiTinh
     * @param email
     * @param ngaySinh
     */
    public KhachHang(String maKhachHang, String hoTen, String soDienThoai, boolean gioiTinh, String email,
                     Date ngaySinh) {
        setMaKhachHang(maKhachHang);
        setHoTen(hoTen);
        setSoDienThoai(soDienThoai);
        setGioiTinh(gioiTinh);
        setEmail(email);
        setNgaySinh(ngaySinh);
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKhachHang='" + maKhachHang + '\'' + ", hoTen='" + hoTen + '\'' + ", soDienThoai='"
                + soDienThoai + '\'' + ", gioiTinh=" + gioiTinh + ", email='" + email + '\'' + ", ngaySinh=" + ngaySinh
                + '}';
    }
}
