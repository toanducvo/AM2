package am2.clothing.management.entity;

public enum VaiTro {
    NHAN_VIEN("Nhân Viên"), QUAN_LY("Quản Lý");

    private final String chucVu;

    /**
     * @param chucVu
     */
    private VaiTro(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getChucVu() {
        return chucVu;
    }
}
