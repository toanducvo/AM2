package am2.clothing.management.entity;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;

    /**
     * @param tenDangNhap
     * @param matKhau
     */
    public TaiKhoan(String tenDangNhap, String matKhau) {
        setTenDangNhap(tenDangNhap);
        setMatKhau(matKhau);
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "tenDangNhap='" + tenDangNhap + '\'' + ", matKhau='" + matKhau + '\'' + '}';
    }
}
