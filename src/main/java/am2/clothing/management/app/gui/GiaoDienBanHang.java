package am2.clothing.management.app.gui;

import am2.clothing.management.app.Globals;
import am2.clothing.management.dao.*;
import am2.clothing.management.entity.*;
import am2.management.dao.*;
import am2.management.entity.*;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.toedter.calendar.JDateChooser;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

public class GiaoDienBanHang extends JFrame implements ActionListener {
    MongoClient client = MongoClients.create();
    KhachHangDao khachHangDao = new KhachHangDao(client);
    SanPhamDao sanPhamDao = new SanPhamDao(client);
    NhanVienDao nhanVienDao = new NhanVienDao(client);
    HoaDonDao hoaDonDao = new HoaDonDao(client);
    LoaiSanPhamDao loaiSanPhamDao = new LoaiSanPhamDao(client);
    NhaCungCapDao nhaCungCapDao = new NhaCungCapDao(client);
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    Calendar cal = Calendar.getInstance();
    private JPanel pnlSouthTieuDe;
    private JPanel pnlCenterLeft;
    private JPanel pnlCenterRight;
    private JPanel pnlR5;
    private JPanel pnlR6;
    private JPanel pnlR2;
    private JPanel pnlCenter;
    private JPanel pnlBang;
    private DefaultTableModel dtm;
    private JTable tblSanPham;
    private JTextField txtMaNVLHD;
    private JTextField txtDiaChiKH;
    private JPanel pnlDangXuat;
    private JButton btnDangXuat;
    private JPanel pnlR3;
    private JPanel pnlR4;
    private JPanel pnlCenterLeftCN;
    private JPanel pn2BottomLeft;
    private JPanel pnlCenterRightCN;
    private JPanel pnlCenterLeftThem;
    private JComboBox<String> cmbTim;
    private JButton btnTim;
    private JPanel pnlBanHang;
    private JPanel pnlR7;
    private JButton btnData;
    private JLabel lblMKH;
    private JLabel lblHT;
    private JTextField txtMKH;
    private JTextField txtHT;
    private JLabel lblSDT;
    private JTextField txtSDT;
    private JButton btnKiemTraSDT;
    private JLabel lblNam;
    private JRadioButton radGTNam;
    private JLabel lblNu;
    private JRadioButton radGTNu;
    private JLabel lblNS;
    //    private JTextField txtNS;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JPanel pnlR1;
    private JLabel lblTrong;
    private JLabel lblKhoangCach;
    private JPanel pnlSouth;
    private JPanel pnlChucNang;
    private JPanel pnlTungChucNang;
    private JPanel pnlTongTienHD;
    private JLabel lblTongTienHD;
    private JTextField txtTMHD;
    private JButton btnTKMaHD;
    private JButton btnXoaSP;
    private JButton btnQLKH;
    private JButton btnLHD;
    private JLabel lblKetQuaTTHD;
    private double tongThanhTien;
    private boolean tinhTrangKH = false;
    private JDateChooser txtNS;
    private JPanel pnlHoTro;
    private JButton btnHoTro;

    public GiaoDienBanHang() throws InterruptedException {
        this.setTitle("BÁN HÀNG");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

// Bắt đầu vùng South bao gồm: Tiêu đề và chức năng đăng xuất.
        pnlSouthTieuDe = new JPanel();
        pnlSouthTieuDe.setBorder(BorderFactory.createTitledBorder(""));
        pnlDangXuat = new JPanel();
        pnlDangXuat.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlDangXuat.setPreferredSize(new Dimension(137, 45));
        switch (Globals.getNhanVien().getChucVu()) {
            case NHAN_VIEN -> {
                btnDangXuat = new JButton("Đăng Xuất");

            }
            case QUAN_LY -> {
                btnDangXuat = new JButton("Quay lại");
            }
        }
        btnDangXuat.addActionListener(evt -> {
            try {
                btnDangXuatActionPerformed(evt);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        btnDangXuat.setPreferredSize(new DimensionUIResource(132, 35));
        btnDangXuat.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlDangXuat.add(btnDangXuat);
        pnlBanHang = new JPanel();
        pnlBanHang.setPreferredSize(new Dimension(1300, 40));
        JLabel lblHeader = new JLabel("BÁN HÀNG");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 37));
        pnlBanHang.add(lblHeader);
        /**
         * khu vực phía dưới code phần button hổ trợ
         */
        pnlHoTro = new JPanel();
        pnlHoTro.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnHoTro = new JButton();
        btnHoTro.setIcon(new ImageIcon("images/HoTro.png"));
        btnHoTro.addActionListener(evt -> {

            try {
                try {
                    btnHoTroActionPerformed(evt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        pnlHoTro.add(btnHoTro);

        pnlSouthTieuDe.add(pnlBanHang);
        pnlSouthTieuDe.add(pnlHoTro);
        pnlSouthTieuDe.add(pnlDangXuat);
        /**
         * Bắt đầu vùng center bao gồm: Logo, thông tin khách hàng.
         */

        pnlCenter = new JPanel();
        pnlCenterLeft = new JPanel();
        pnlCenterLeft.setPreferredSize(new Dimension(750, 300));
        pnlCenterLeft.add(new JLabel(new ImageIcon(new ImageIcon("images/LogoAM2.png").getImage().getScaledInstance(400,
                305, java.awt.Image.SCALE_DEFAULT))));

        pnlCenterRight = new JPanel();
        pnlCenterRight.setBorder(BorderFactory.createTitledBorder("Khách Hàng"));
        pnlCenterRight.setPreferredSize(new Dimension(750, 295));
        pnlCenterRight.setLayout(new GridLayout(10, 1));
        /**
         * phía dưới là vùng các texField và các Label
         */
        lblMKH = new JLabel("Mã khách hàng");
        lblMKH.setPreferredSize(new Dimension(150, 25));
        txtMKH = new JTextField(50);

        lblHT = new JLabel("Họ tên");
        lblHT.setPreferredSize(new Dimension(150, 25));
        txtHT = new JTextField(50);

        txtMKH.setText(khachHangDao.phatSinhMaKhachHang());
        txtMKH.disable();

        lblTrong = new JLabel("");
        lblTrong.setPreferredSize(new Dimension(150, 25));

        lblKhoangCach = new JLabel("");
        lblKhoangCach.setPreferredSize(new Dimension(50, 25));

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setPreferredSize(new Dimension(150, 25));
        txtSDT = new JTextField(50);
        Icon icon = new ImageIcon("images/KinhLup.png");
        btnKiemTraSDT = new JButton(icon);

        btnKiemTraSDT.setPreferredSize(new Dimension(30, 20));

        JLabel lblNS = new JLabel("Ngày sinh:");
        lblNS.setPreferredSize(new Dimension(150, 25));
        txtNS = new JDateChooser("yyyy/MM/dd/HH:mm:ss", "####/##/##/##:##:##", '_');
        txtNS.setPreferredSize(new DimensionUIResource(150, 25));
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setPreferredSize(new Dimension(150, 25));
        txtEmail = new JTextField(50);
        /**
         * Phía dưới là các JPanel để add từng label và textField
         */
        pnlR1 = new JPanel();
        pnlR2 = new JPanel();
        pnlR2.setPreferredSize(new Dimension(770, pnlR2.HEIGHT));
        pnlR2.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlR2.add(lblMKH);
        pnlR2.add(txtMKH);
//****************************************************************
        pnlR3 = new JPanel();
        pnlR3.setPreferredSize(new Dimension(770, pnlR3.HEIGHT));
        pnlR3.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlR3.add(lblHT);
        pnlR3.add(txtHT);
//*****************************************************************
        pnlR4 = new JPanel();
        pnlR4.setLayout(new FlowLayout(FlowLayout.LEFT));
        lblNam = new JLabel("Nam");
        radGTNam = new JRadioButton();
        lblNu = new JLabel("Nữ");
        radGTNu = new JRadioButton();
        pnlR4.add(lblTrong);
        pnlR4.add(lblNam);
        pnlR4.add(radGTNam);
        pnlR4.add(lblKhoangCach);
        pnlR4.add(lblNu);
        pnlR4.add(radGTNu);
//****************************************************************
        pnlR5 = new JPanel();
        pnlR5.setPreferredSize(new Dimension(770, pnlR4.HEIGHT));
        pnlR5.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlR5.add(lblSDT);
        pnlR5.add(txtSDT);
        pnlR5.add(btnKiemTraSDT);
//****************************************************************
        pnlR6 = new JPanel();
        pnlR6.setPreferredSize(new Dimension(770, pnlR6.HEIGHT));
        pnlR6.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlR6.add(lblNS);
        pnlR6.add(txtNS);
//****************************************************************
        pnlR7 = new JPanel();
        pnlR7.setPreferredSize(new Dimension(770, pnlR7.HEIGHT));
        pnlR7.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlR7.add(lblEmail);
        pnlR7.add(txtEmail);
//*****************************************************************
        pnlCenterRight.add(pnlR1);
        pnlCenterRight.add(pnlR2);
        pnlCenterRight.add(pnlR3);
        pnlCenterRight.add(pnlR4);
        pnlCenterRight.add(pnlR5);
        pnlCenterRight.add(pnlR6);
        pnlCenterRight.add(pnlR7);
//*****************************************************************
        pnlCenterLeftCN = new JPanel();
        pn2BottomLeft = new JPanel();
        pn2BottomLeft.setPreferredSize(new Dimension(770, 40));
        pnlCenterLeftThem = new JPanel();
        pnlCenterLeftThem.setBorder(BorderFactory.createLoweredBevelBorder());
        cmbTim = new JComboBox<String>();

        cmbTim.setEditable(true);
        SanPhamDao sanPhamDao = new SanPhamDao(client);
        sanPhamDao.layDanhSachMaSanPham().forEach(maSP -> {
            cmbTim.addItem(maSP);
        });
        AutoCompleteDecorator.decorate(cmbTim);
        btnTim = new JButton("Thêm");
        pnlCenterLeftThem.add(cmbTim);

        pnlCenterLeftThem.add(btnTim);
        pn2BottomLeft.add(pnlCenterLeftThem);
//******************************************************************
        pnlCenterRightCN = new JPanel();
        pnlCenterRightCN.setPreferredSize(new Dimension(770, 40));
//*************************************************************
        pnlCenterLeftCN.add(pn2BottomLeft, BorderLayout.WEST);
        pnlCenterLeftCN.add(pnlCenterRightCN, BorderLayout.EAST);
        pnlCenter.add(pnlCenterLeft, BorderLayout.WEST);
        pnlCenter.add(pnlCenterRight, BorderLayout.EAST);
        pnlCenter.add(pnlCenterLeftCN, BorderLayout.SOUTH);
//**********************************************************************
        pnlSouth = new JPanel();
        pnlSouth.setPreferredSize(new Dimension(1500, 430));
        pnlBang = new JPanel();
        pnlBang.setPreferredSize(new Dimension(1530, 300));
        pnlBang.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        pnlBang.setLayout(new BoxLayout(pnlBang, BoxLayout.PAGE_AXIS));
        String[] cols = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Nhà cung cấp", "Kích cỡ", "Số lượng",
                "Đơn giá", "Thành tiền"};
        dtm = new DefaultTableModel(cols, 0);
        tblSanPham = new JTable(dtm);

        JScrollPane scroll = new JScrollPane(tblSanPham);
        pnlBang.add(scroll);

//**********************************************************************
        pnlTongTienHD = new JPanel();
        lblTongTienHD = new JLabel("Tổng tiền hoá đơn: ");
        pnlTongTienHD.setPreferredSize(new Dimension(1200, 20));
        pnlTongTienHD.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lblKetQuaTTHD = new JLabel("0.0 VND");
        pnlTongTienHD.add(lblTongTienHD);
        pnlTongTienHD.add(lblKetQuaTTHD);

//*******************************************************************
        pnlChucNang = new JPanel();
        pnlChucNang.setPreferredSize(new DimensionUIResource(1500, 100));
        pnlTungChucNang = new JPanel();
        pnlTungChucNang.setPreferredSize(new DimensionUIResource(1500, 50));
        pnlTungChucNang.setBorder(BorderFactory.createLoweredBevelBorder());
        txtTMHD = new JTextField(30);
        btnTKMaHD = new JButton("Tìm kiếm mã hoá đơn");
        btnXoaSP = new JButton("Xoá sản phẩm");
        btnQLKH = new JButton("Quản lý khách hàng");
        btnQLKH.addActionListener(evt -> {

            try {
                btnQLKHActionPerformed(evt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        btnLHD = new JButton("Lập hoá đơn");

        txtTMHD.setPreferredSize(new DimensionUIResource(100, 30));
        btnTKMaHD.setPreferredSize(new DimensionUIResource(200, 30));
        btnXoaSP.setPreferredSize(new DimensionUIResource(150, 30));
        btnQLKH.setPreferredSize(new DimensionUIResource(200, 30));
        btnLHD.setPreferredSize(new DimensionUIResource(150, 30));

        pnlTungChucNang.add(txtTMHD);
        pnlTungChucNang.add(btnTKMaHD);
        pnlTungChucNang.add(btnXoaSP);
        pnlTungChucNang.add(btnQLKH);
        pnlTungChucNang.add(btnLHD);

        pnlChucNang.add(pnlTungChucNang);

        pnlSouth.add(pnlBang, BorderLayout.NORTH);
        pnlSouth.add(pnlTongTienHD, BorderLayout.CENTER);
        pnlSouth.add(pnlChucNang, BorderLayout.SOUTH);
//***************************************************************
        this.add(pnlSouthTieuDe, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        /**
         * Nơi bắt sự kiện các button
         */
        btnKiemTraSDT.addActionListener(this);
        btnTim.addActionListener(this);
        btnXoaSP.addActionListener(this);
        btnLHD.addActionListener(this);
        btnTKMaHD.addActionListener(this);

    }

    @SuppressWarnings("deprecation")
    public boolean laythongtinkhachhang() throws InterruptedException {
        KhachHang khachhang = khachHangDao.timKhachHangTheoSDT(txtSDT.getText());
        if (khachhang.getMaKhachHang() != "Chưa xác định") {
            txtMKH.setText(khachhang.getMaKhachHang());
            txtHT.setText(khachhang.getHoTen());
            txtEmail.setText(khachhang.getEmail());
            txtNS.setDate(khachhang.getNgaySinh());
            if (khachhang.isGioiTinh() == true) {
                radGTNam.doClick();
            } else {
                radGTNu.doClick();
            }
            txtMKH.disable();
            txtHT.disable();
            txtEmail.disable();
            radGTNam.disable();

            return true;

        } else
            JOptionPane.showMessageDialog(this, "Khách hàng chưa có. Vui lòng nhập thông tin khách hàng ");
        return false;
    }

    private void btnDangXuatActionPerformed(ActionEvent evt) throws InterruptedException {
        switch (Globals.getNhanVien().getChucVu()) {
            case NHAN_VIEN -> {
                setVisible(false);
                new GiaoDienDangNhap().setVisible(true);
            }
            case QUAN_LY -> {
                setVisible(false);
                new GiaoDienDieuKhien().setVisible(true);
            }
        }
    }

    private void btnHoTroActionPerformed(ActionEvent evt) throws InterruptedException, IOException {
        new GiaoDienHoTroNhanVien().setVisible(true);

    }

    private void btnQLKHActionPerformed(ActionEvent evt) throws InterruptedException, IOException {
        new GiaoDienQuanLyKhachHang().setVisible(true);
        setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnKiemTraSDT)) {
            String SDT = txtSDT.getText();
            KhachHang khachHang;
            try {
                khachHang = khachHangDao.timKhachHangTheoSDT(SDT);
                if (khachHang == null) {
                    JOptionPane.showMessageDialog(this, "Chưa có khách hàng này!");
                } else {
                    try {
                        tinhTrangKH = laythongtinkhachhang();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();

                    }
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }

        } else if (obj.equals(btnTim)) {
            String maSanPham = (String) cmbTim.getSelectedItem();
            SanPham sanPham;
            try {
                sanPham = sanPhamDao.timSanPhamTheoMa(maSanPham);
                Locale locale = new Locale("vi", "VN");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                int sTT = dtm.getRowCount();
                String a = JOptionPane.showInputDialog("nhập vào số lượng sản của sản phẩm", 1);
                int soLuongTrongKhoa = sanPham.getSoLuong();
                if (Integer.parseInt(a) <= sanPham.getSoLuong()) {
                    sanPham.setSoLuong(Integer.parseInt(a));
                    double thanhtien = sanPham.getGia() * sanPham.getSoLuong();
                    List<LoaiSanPham> loaiSanPham = loaiSanPhamDao.timTheoMaLoai(sanPham.getLoaiSanPham().getMaLoai());
                    List<NhaCungCap> nhaCungCap = nhaCungCapDao
                            .timTheoMaNhaCungCap(sanPham.getNhaCungCap().getMaNhaCungCap());

                    dtm.addRow(new Object[]{sTT + 1, sanPham.getMaSanPham(), sanPham.getTenSanPham(),
                            loaiSanPham.get(0).getTenLoai(), nhaCungCap.get(0).getTenNhaCungCap(),
                            sanPham.getDanhSachKichCo(), sanPham.getSoLuong(),
                            currencyFormatter.format(sanPham.getGia()), currencyFormatter.format(thanhtien)});
                    sanPham.setSoLuong(soLuongTrongKhoa - Integer.parseInt(a));
                    sanPhamDao.capNhatLoaiSanPham(maSanPham, (SanPham) sanPham);
                    int soDong = dtm.getRowCount();
                    tongThanhTien += thanhtien;
                    lblKetQuaTTHD.setText(currencyFormatter.format(tongThanhTien));
                } else {
                    JOptionPane.showMessageDialog(this, "số lượng của sản phẩm này chỉ còn " + sanPham.getSoLuong()
                            + "\n\t.Vui lòng chọn số nhỏ hơn!");
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        } else if (obj.equals(btnXoaSP)) {
            try {
                int a = tblSanPham.getSelectedRow();
                int soluong = (int) dtm.getValueAt(a, 6);
                String maSanPham = (String) dtm.getValueAt(a, 1);
                SanPham sanPham = sanPhamDao.timSanPhamTheoMa(maSanPham);
                double thanhTienCuaSP = soluong * sanPham.getGia();
                tongThanhTien -= thanhTienCuaSP;
                lblKetQuaTTHD.setText(currencyFormatter.format(tongThanhTien));
                dtm.removeRow(a);
                sanPham.setSoLuong(sanPham.getSoLuong() + soluong);
                sanPhamDao.capNhatLoaiSanPham(maSanPham, (SanPham) sanPham);
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }

            capNhatSoThuTu(tblSanPham.getRowCount());
        } else if (obj.equals(btnLHD)) {

            if (regex()) {

                if (dtm.getRowCount() > 0) {

                    NhanVien nhanVien;
                    try {
                        nhanVien = nhanVienDao.timNhanVienTheoMa(Globals.getNhanVien().getMaNhanVien());
                        boolean gioitinh = true;
                        if (!radGTNam.isSelected()) {
                            gioitinh = false;
                        }
                        KhachHang khachHang = new KhachHang(txtMKH.getText(), txtHT.getText(), txtSDT.getText(),
                                gioitinh, txtEmail.getText(), txtNS.getDate());
                        if (tinhTrangKH == false) {
                            try {
                                khachHangDao.themKhachHang(khachHang);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }

                        HoaDon hoadon;
                        try {
                            hoadon = new HoaDon(tuPhatSinhMa(), khachHang, nhanVien,
                                    new Date(System.currentTimeMillis()), cal.get(Calendar.DAY_OF_WEEK) == 8 ? 5 : 0,
                                    laydsCTHD());
                            hoaDonDao.createBill(hoadon);
                            new GiaoDienHoaDon(hoadon.getMaHoaDon()).setVisible(true);
                            setVisible(false);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Vui Lòng chọn Sản Phẩm muốn mua ");
                }
            }
        } else if (obj.equals(btnTKMaHD)) {
            String maHoaDon = txtTMHD.getText();
            List<HoaDon> DSHoaDon;
            try {
                DSHoaDon = hoaDonDao.timHoaDon("maHoaDon", maHoaDon);
                if (DSHoaDon.size() != 0) {
                    if (txtTMHD.getText().equalsIgnoreCase("") != true) {
                        try {
                            new GiaoDienHoaDon(txtTMHD.getText()).setVisible(true);
                            setVisible(false);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        ;
                    } else {
                        JOptionPane.showMessageDialog(this, "vui lòng nhập mã sản phẩm trước khi tìm ");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }

        }
    }

    //	lấy chi tiết hóa đơn
    public List<ChiTietHoaDon> laydsCTHD() throws InterruptedException {
        List<ChiTietHoaDon> dsCTHD = new ArrayList<ChiTietHoaDon>();
        int soDong = dtm.getRowCount();
        for (int i = 0; i < soDong; i++) {
            SanPham sanPham = sanPhamDao.timSanPhamTheoMa((String) dtm.getValueAt(i, 1));
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(sanPham, (int) dtm.getValueAt(i, 6), sanPham.getGia());
            dsCTHD.add(chiTietHoaDon);
        }
        return dsCTHD;
    }

    //	ham cập nhật lại STT
    public void capNhatSoThuTu(int row) {
        int dong = 1;
        while (dong <= row) {
            dtm.setValueAt(dong, dong - 1, 0);
            dong++;
        }
    }

    public String tuPhatSinhMa() throws NumberFormatException, InterruptedException {
        int maHoaDonMoiInterger = Integer.parseInt(hoaDonDao.layMaCuoiCung().substring(2)) + 1;
        String maHoaDonMoiString = String.valueOf(maHoaDonMoiInterger);
        String maHDmoi = "HD";
        for (int i = 0; i < 5 - maHoaDonMoiString.length(); i++) {
            maHDmoi += "0";
        }
        maHDmoi += maHoaDonMoiString;
        return maHDmoi;
    }

    private boolean regex() {

        String maKH = txtMKH.getText();
        String tenKH = txtHT.getText();
        boolean gioiTinh = radGTNam.isSelected();
        String ngaySinh = txtNS.getDateFormatString();
        String SDT = txtSDT.getText();
        String email = txtEmail.getText();

        if (!Pattern.matches("^(KH)\\d{5}", maKH)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ, vui lòng kiểm tra lại!");
            txtMKH.selectAll();
            txtMKH.requestFocus();
            return false;
        } else if (!Pattern.matches(
                "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẾẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽếềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$",
                tenKH)) {
            JOptionPane.showMessageDialog(this, "Họ và tên khách hàng không hợp lệ, vui lòng kiểm tra lại!");
            txtHT.selectAll();
            txtHT.requestFocus();
            return false;

        } else if (!Pattern.matches("^(09|03|07|08|05)+([0-9]{8})", SDT)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ, vui lòng kiểm tra lại!");
            txtSDT.selectAll();
            txtSDT.requestFocus();
            return false;
        } else if (!Pattern.matches("^(\\w{1,})@(\\w{1,})(\\.(\\w{2,})){1,}", email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ, vui lòng kiểm tra lại!");
            txtEmail.selectAll();
            txtEmail.requestFocus();
            return false;
        }

        return true;

    }

}
