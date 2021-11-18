package am2.clothing.management.app.gui;

import am2.clothing.management.dao.HoaDonDao;
import am2.clothing.management.dao.KhachHangDao;
import am2.clothing.management.dao.NhanVienDao;
import am2.clothing.management.entity.HoaDon;
import am2.clothing.management.entity.KhachHang;
import am2.clothing.management.entity.NhanVien;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GiaoDienQuanLyHoaDon extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private JButton btnBack;
    private JPanel pn1;
    private JPanel head;
    private JPanel pn2;
    private DefaultTableModel dtm;
    private JTable table;
    private JComboBox<String> cboFind;
    private JPanel pn3;
    private JPanel pn3BL;
    private JTextField txtFind;
    private JButton btnFind;
    private JButton btnViewInfo;
    private JButton btnPrint;
    // private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSelect;
    private MongoClient client = MongoClients.create();
    private HoaDonDao hoaDonDao = new HoaDonDao(client);
    private KhachHangDao khachHangDao = new KhachHangDao(client);
    private NhanVienDao nhanVienDao = new NhanVienDao(client);

    public GiaoDienQuanLyHoaDon() throws InterruptedException {
        this.setTitle("Giao Diện Quản Lý Hóa Đơn");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//		setUndecorated(true);
        createGUI();
    }

    private void createGUI() throws InterruptedException {
        JPanel header, panelWest, panelSouth, panelCenter, panelCenterCenter;

        // Phần North
        pn1 = new JPanel();
        head = new JPanel();
        head.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		head.setLayout(new FlowLayout(FlowLayout.LEFT));
        head.setPreferredSize(new Dimension(200, 50));
        btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Arial", Font.BOLD, 20));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnBackActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        head.add(btnBack);
        header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        header.setPreferredSize(new Dimension(1300, 90));
        JLabel lblHeader = new JLabel("Quản Lý Hóa Đơn");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 70));
        header.add(lblHeader);
        pn1.add(header);
        pn1.add(head);

// code  table

        pn2 = new JPanel();
        pn2.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));

//		get table  fullScreen
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.PAGE_AXIS));

        String[] cols = {"Mã hóa đơn", "Ngày tạo", "Tên nhân viên", "Số điện thoại", "Số lượng sản phẩm", "giảm giá",
                "tống tiền hóa đơn"};
        dtm = new DefaultTableModel(cols, 0);
        table = new JTable(dtm);
        JScrollPane scroll = new JScrollPane(table);
        // setsize for table
        scroll.setPreferredSize(new DimensionUIResource(1400, 600));
        pn2.add(scroll);

//thêm dữ liệu vào bảng
        List<HoaDon> DSHoaDon = hoaDonDao.getAllListBill();

        for (HoaDon hoaDon : DSHoaDon) {
            List<KhachHang> SDTKhachHang = khachHangDao.timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang());
            NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien());
            dtm.addRow(new Object[]{hoaDon.getMaHoaDon(), hoaDon.getNgayTao(), nhanVien.getHoTen(),
                    SDTKhachHang.get(0).getSoDienThoai(), hoaDonDao.laySoLuongSanPhamTheoMaHoaDon(hoaDon.getMaHoaDon()),
                    hoaDon.getGiamGia(), currencyFormatter.format(hoaDon.getTongTienHoaDon())});
        }

// code function
        pn3 = new JPanel();
        pn3.setPreferredSize(new Dimension(1500, 100));
        pn3.setBorder(BorderFactory.createTitledBorder("các chức năng"));
        pn3BL = new JPanel();
        pn3BL.setPreferredSize(new DimensionUIResource(1400, 50));
        pn3BL.setBorder(BorderFactory.createLoweredBevelBorder());
        cboFind = new JComboBox<String>();
        cboFind.setEditable(false);
        cboFind.addItem("Chọn tiêu chí tìm");
        cboFind.addItem("Tìm theo mã khách hàng");
        cboFind.addItem("Tìm theo mã hóa đơn");
        cboFind.addItem("Tìm theo mã nhân viên");

        txtFind = new JTextField(20);
        JLabel lbFind = new JLabel("Tìm Kiếm theo:");
        btnFind = new JButton("Tìm Kiếm");
        pn3BL.add(txtFind);
        pn3BL.add(lbFind);
        pn3BL.add(cboFind);
        pn3BL.add(btnFind);
//        btnUpdate = new JButton("Cập Nhật");
//        btnDelete = new JButton("Xóa");
        btnPrint = new JButton("Kết Xuất");
        btnSelect = new JButton("Xem Chi Tiết");

        System.out.println(cboFind.getInputContext());

        lbFind.setPreferredSize(new DimensionUIResource(100, 40));
        txtFind.setPreferredSize(new DimensionUIResource(100, 40));
        cboFind.setPreferredSize(new DimensionUIResource(150, 40));
        btnFind.setPreferredSize(new DimensionUIResource(100, 40));
//        btnUpdate.setPreferredSize(new DimensionUIResource(100, 40));
//        btnDelete.setPreferredSize(new DimensionUIResource(100, 40));
        btnPrint.setPreferredSize(new DimensionUIResource(100, 40));
        btnSelect.setPreferredSize(new DimensionUIResource(150, 40));
//        pn3BL.add(btnUpdate);
//        pn3BL.add(btnDelete);
        pn3BL.add(btnPrint);
        pn3BL.add(btnSelect);

        pn3.add(pn3BL);

        this.add(pn1, BorderLayout.NORTH);
        this.add(pn2, BorderLayout.CENTER);
        this.add(pn3, BorderLayout.SOUTH);

        btnPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (hoaDonDao.writeToFile("DSHoaDon", DSHoaDon))
                        JOptionPane.showMessageDialog(null, "Kết xuất thành công");
                    else
                        JOptionPane.showMessageDialog(null, "Kết xuất thất bại!");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnSelectPerfromed(e);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnFind.addActionListener(this);

    }

    private void btnBackActionPerformed(ActionEvent evt) throws InterruptedException {
        new GiaoDienDieuKhien().setVisible(true);
        setVisible(false);
    }

    private void btnSelectPerfromed(ActionEvent evt) throws InterruptedException {
        int row = table.getSelectedRow();
        String maHoaDon = "";
        if (row != -1) {
            maHoaDon = table.getValueAt(row, 0).toString();

        }
        new GiaoDienHoaDon(maHoaDon).setVisible(true);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnFind)) {
            try {
                xuLyTimKiem();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }

    private void xuLyXemChiTietHoaDon() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String maHoaDon = table.getValueAt(row, 0).toString();

        }
    }

    private void xuLyTimKiem() throws InterruptedException {
        int select = cboFind.getSelectedIndex();
        switch (select) {

            case 0: {
                List<HoaDon> DSHoaDon = hoaDonDao.getAllListBill();
//				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                dtm.setRowCount(0);
                for (HoaDon hoaDon : DSHoaDon) {
                    List<KhachHang> SDTKhachHang = khachHangDao.timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang());
                    NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien());
                    dtm.addRow(new Object[]{hoaDon.getMaHoaDon(), hoaDon.getNgayTao(), nhanVien.getHoTen(),
                            SDTKhachHang.get(0).getSoDienThoai(),
                            hoaDonDao.laySoLuongSanPhamTheoMaHoaDon(hoaDon.getMaHoaDon()), hoaDon.getGiamGia(),
                            currencyFormatter.format(hoaDon.getTongTienHoaDon())});
                }

                break;
            }

            case 1: {
                String maKhachHang = txtFind.getText();

                List<HoaDon> DSHoaDon = hoaDonDao.timHoaDon("maKhachHang", maKhachHang);
//				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                if (DSHoaDon.size() != 0) {
                    dtm.setRowCount(0);

                    for (HoaDon hoaDon : DSHoaDon) {
                        List<KhachHang> SDTKhachHang = khachHangDao
                                .timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang());
                        NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien());
                        dtm.addRow(new Object[]{hoaDon.getMaHoaDon(), hoaDon.getNgayTao(), nhanVien.getHoTen(),
                                SDTKhachHang.get(0).getSoDienThoai(),
                                hoaDonDao.laySoLuongSanPhamTheoMaHoaDon(hoaDon.getMaHoaDon()), hoaDon.getGiamGia(),
                                currencyFormatter.format(hoaDon.getTongTienHoaDon())});
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                }
                break;

            }

            case 2: {
                String maHoaDon = txtFind.getText();

                List<HoaDon> DSHoaDon = hoaDonDao.timHoaDon("maHoaDon", maHoaDon);
//				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                if (DSHoaDon.size() != 0) {
                    dtm.setRowCount(0);

                    for (HoaDon hoaDon : DSHoaDon) {
                        List<KhachHang> SDTKhachHang = khachHangDao
                                .timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang());
                        NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien());
                        dtm.addRow(new Object[]{hoaDon.getMaHoaDon(), hoaDon.getNgayTao(), nhanVien.getHoTen(),
                                SDTKhachHang.get(0).getSoDienThoai(),
                                hoaDonDao.laySoLuongSanPhamTheoMaHoaDon(hoaDon.getMaHoaDon()), hoaDon.getGiamGia(),
                                hoaDon.getTongTienHoaDon()});
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                }
                break;
            }

            case 3: {
                String maNhanVien = txtFind.getText();

                List<HoaDon> DSHoaDon = hoaDonDao.timHoaDon("maNhanVien", maNhanVien);
//				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                if (DSHoaDon.size() != 0) {
                    dtm.setRowCount(0);

                    for (HoaDon hoaDon : DSHoaDon) {
                        List<KhachHang> SDTKhachHang = khachHangDao
                                .timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang());
                        NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien());
                        dtm.addRow(new Object[]{hoaDon.getMaHoaDon(), hoaDon.getNgayTao(), nhanVien.getHoTen(),
                                SDTKhachHang.get(0).getSoDienThoai(),
                                hoaDonDao.laySoLuongSanPhamTheoMaHoaDon(hoaDon.getMaHoaDon()), hoaDon.getGiamGia(),
                                currencyFormatter.format(hoaDon.getTongTienHoaDon())});
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                }

                break;
            }
        }
    }
}
