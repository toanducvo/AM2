package am2.clothing.management.entity;

import java.util.Date;

public class NhanVien {
    private String maNhanVien;
    private String hoTen;
    private Date ngaySinh;
    private boolean gioiTinh;
    private String email;
    private String soDienThoai;
    private String CMND;
    private VaiTro chucVu;

    public NhanVien() {
        setMaNhanVien("Chưa xác định");
        setHoTen("Chưa xác định");
        setNgaySinh(new Date(System.currentTimeMillis()));
        setGioiTinh(false);
        setEmail("Chưa xác định");
        setSoDienThoai("Chưa xác định");
        setCMND("Chưa xác định");
        setChucVu(VaiTro.NHAN_VIEN);
    }

    /**
     * @param maNhanVien
     */
    public NhanVien(String maNhanVien) {
        setMaNhanVien(maNhanVien);
        setHoTen("Chưa xác định");
        setNgaySinh(new Date(System.currentTimeMillis()));
        setGioiTinh(false);
        setEmail("Chưa xác định");
        setSoDienThoai("Chưa xác định");
        setCMND("Chưa xác định");
        setChucVu(VaiTro.NHAN_VIEN);
    }

    /**
     * @param maNhanVien  - Mã nhân viên
     * @param hoTen       - Họ tên nhân viên
     * @param ngaySinh    - Ngày sinh
     * @param gioiTinh    - Giới tính
     * @param email       - Email
     * @param soDienThoai - Số điện thoại
     * @param CMND        - CMND/CCCD
     * @param chucVu      - Chức vụ
     */
    public NhanVien(String maNhanVien, String hoTen, Date ngaySinh, boolean gioiTinh, String email, String soDienThoai,
                    String CMND, VaiTro chucVu) {
        setMaNhanVien(maNhanVien);
        setHoTen(hoTen);
        setNgaySinh(ngaySinh);
        setGioiTinh(gioiTinh);
        setEmail(email);
        setSoDienThoai(soDienThoai);
        setCMND(CMND);
        setChucVu(chucVu);
    }

    public NhanVien(String maNhanVien, String hoTen, Date ngaySinh, boolean gioiTinh, String email, String soDienThoai,
                    String cMND) {
        super();
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.soDienThoai = soDienThoai;
        CMND = cMND;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public VaiTro getChucVu() {
        return chucVu;
    }

    public void setChucVu(VaiTro chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien='" + maNhanVien + '\'' + ", hoTen='" + hoTen + '\'' + ", ngaySinh=" + ngaySinh
                + ", gioiTinh=" + gioiTinh + ", email='" + email + '\'' + ", soDienThoai='" + soDienThoai + '\''
                + ", CMND='" + CMND + '\'' + ", chucVu=" + chucVu + '}';
    }
}
