package am2.clothing.management.entity;

public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String soDienThoai;

    public NhaCungCap() {
        setMaNhaCungCap("Chưa xác định");
        setTenNhaCungCap("Chưa xác định");
        setDiaChi("Chưa xác định");
        setSoDienThoai("Chưa xác định");
    }

    /**
     * @param maNhaCungCap
     */
    public NhaCungCap(String maNhaCungCap) {
        setMaNhaCungCap(maNhaCungCap);
        setTenNhaCungCap("Chưa xác định");
        setDiaChi("Chưa xác định");
        setSoDienThoai("Chưa xác định");
    }

    /**
     * @param maNhaCungCap
     * @param tenNhaCungCap
     * @param diaChi
     * @param soDienThoai
     */
    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String diaChi, String soDienThoai) {
        setMaNhaCungCap(maNhaCungCap);
        setTenNhaCungCap(tenNhaCungCap);
        setDiaChi(diaChi);
        setSoDienThoai(soDienThoai);
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return "NhaCungCap{" + "maNhaCungCap='" + maNhaCungCap + '\'' + ", tenNhaCungCap='" + tenNhaCungCap + '\''
                + ", diaChi='" + diaChi + '\'' + ", soDienThoai='" + soDienThoai + '\'' + '}';
    }
}
