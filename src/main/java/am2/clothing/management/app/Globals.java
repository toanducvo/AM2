package am2.clothing.management.app;

import am2.clothing.management.entity.NhanVien;

public class Globals {

    public static NhanVien nhanVien;

    public static NhanVien getNhanVien() {
        return nhanVien;
    }

    public static void setNhanVien(NhanVien nhanVien) {
        Globals.nhanVien = nhanVien;
    }

}
