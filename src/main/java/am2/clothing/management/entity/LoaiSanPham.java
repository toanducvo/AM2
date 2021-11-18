package am2.clothing.management.entity;

public class LoaiSanPham {
    private String maLoai;
    private String tenLoai;

    public LoaiSanPham() {
        setMaLoai("Chưa xác định");
        setTenLoai("Chưa xác định");
    }

    /**
     * @param maLoai
     */
    public LoaiSanPham(String maLoai) {
        setMaLoai(maLoai);
        setTenLoai("Chưa xác định");
    }

    /**
     * @param maLoai
     * @param tenLoai
     */
    public LoaiSanPham(String maLoai, String tenLoai) {
        setMaLoai(maLoai);
        setTenLoai(tenLoai);
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "LoaiSanPham{" + "maLoai='" + maLoai + '\'' + ", tenLoai='" + tenLoai + '\'' + '}';
    }
}
