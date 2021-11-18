package am2.clothing.management.app.gui;

import am2.clothing.management.app.Globals;
import am2.clothing.management.dao.HoaDonDao;
import am2.clothing.management.dao.KhachHangDao;
import am2.clothing.management.dao.SanPhamDao;
import am2.clothing.management.entity.ChiTietHoaDon;
import am2.clothing.management.entity.HoaDon;
import am2.clothing.management.entity.KhachHang;
import am2.clothing.management.entity.SanPham;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GiaoDienHoaDon extends JFrame {
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    MongoClient client = MongoClients.create();
    HoaDonDao hoaDonDao = new HoaDonDao(client);
    KhachHangDao khachHangDao = new KhachHangDao(client);
    SanPhamDao sanPhamDao = new SanPhamDao(client);
    private JPanel pnlNorth;
    private JPanel pnlCenter;
    private JPanel pnlSouth;
    private JTextField txtMaHoaDon;
    private JTextField txtNgayLap;
    private JTextField txtKhachHang;
    private JTextField txtSoDienThoai;
    private JTextField txtThanhTien;
    private JButton btnThoat;
    private JTable table;
    private DefaultTableModel model;

    public GiaoDienHoaDon(String maHoaDon) throws InterruptedException {
        setTitle("Của Hàng Thời Trang AM2");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        add(pnlNorth = new JPanel());
        pnlNorth.setPreferredSize(new Dimension(0, 130));
        add(pnlNorth, BorderLayout.NORTH);
        JLabel lblHeader = new JLabel("Thông Tin Hóa Đơn");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 80));
        pnlNorth.add(lblHeader);

        pnlCenter = new JPanel();
        add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.setPreferredSize(new Dimension(0, 150));
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlCenter.setLayout(null);

        JLabel lblMaHoaDon, lblNgayLap, lblKhachHang, lblSoDienThoai, lblThanhTien;
        pnlCenter.add(lblMaHoaDon = new JLabel("Mã hóa đơn"));
        lblMaHoaDon.setFont(new Font("Arial", Font.BOLD, 17));
        pnlCenter.add(lblNgayLap = new JLabel("Ngày lập"));
        lblNgayLap.setFont(new Font("Arial", Font.BOLD, 17));
        pnlCenter.add(lblKhachHang = new JLabel("Khách hàng"));
        lblKhachHang.setFont(new Font("Arial", Font.BOLD, 17));
        pnlCenter.add(lblSoDienThoai = new JLabel("Số điện thoại"));
        lblSoDienThoai.setFont(new Font("Arial", Font.BOLD, 17));
        pnlCenter.add(lblThanhTien = new JLabel("Thành tiền"));
        lblThanhTien.setFont(new Font("Arial", Font.BOLD, 17));

        pnlCenter.add(txtMaHoaDon = new JTextField());
        pnlCenter.add(txtNgayLap = new JTextField());
        pnlCenter.add(txtKhachHang = new JTextField());
        pnlCenter.add(txtSoDienThoai = new JTextField());
        pnlCenter.add(txtThanhTien = new JTextField());

        int wlbl = 150, wtxt = 900, h = 30;
        int xlbl = 100, xtxt = 250;

        lblMaHoaDon.setBounds(xlbl, 50, wlbl, h);
        txtMaHoaDon.setBounds(xtxt, 50, wtxt, h);
        lblNgayLap.setBounds(xlbl, 100, wlbl, h);
        txtNgayLap.setBounds(xtxt, 100, wtxt, h);
        lblKhachHang.setBounds(xlbl, 150, wlbl, h);
        txtKhachHang.setBounds(xtxt, 150, wtxt, h);
        lblSoDienThoai.setBounds(xlbl, 200, wlbl, h);
        txtSoDienThoai.setBounds(xtxt, 200, wtxt, h);
        lblThanhTien.setBounds(xlbl, 250, wlbl, h);
        txtThanhTien.setBounds(xtxt, 250, wtxt, h);

        txtMaHoaDon.setEditable(false);
        txtNgayLap.setEditable(false);
        txtKhachHang.setEditable(false);
        txtSoDienThoai.setEditable(false);
        txtThanhTien.setEditable(false);

        pnlSouth = new JPanel(new BorderLayout());
        add(pnlSouth, BorderLayout.SOUTH);
        pnlSouth.setPreferredSize(new Dimension(0, 400));
        pnlSouth.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        JScrollPane scroll;
        String[] headers = "STT;Mã sản phẩm;Tên sản phẩm;Đơn vị tính;Số lượng;Đơn giá;Thành tiền".split(";");

        model = new DefaultTableModel(headers, 0);
        table = new JTable(model);

        JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlSouth.add(tablePanel, BorderLayout.CENTER);

        btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnThoatActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        JPanel pnlTemp = new JPanel(new BorderLayout());
        pnlTemp.add(btnThoat, BorderLayout.EAST);
        pnlSouth.add(pnlTemp, BorderLayout.SOUTH);

        List<HoaDon> hoaDon = hoaDonDao.timHoaDon("maHoaDon", maHoaDon);

        List<KhachHang> khachHang = khachHangDao.timKhachHangTheoMa(hoaDon.get(0).getKhachHang().getMaKhachHang());

        txtMaHoaDon.setText(hoaDon.get(0).getMaHoaDon());
        txtNgayLap.setText(hoaDon.get(0).getNgayTao().toString());
        txtKhachHang.setText(khachHang.get(0).getHoTen());
        txtSoDienThoai.setText(khachHang.get(0).getSoDienThoai());
        txtThanhTien.setText(currencyFormatter.format(hoaDon.get(0).getTongTienHoaDon()));
        int STT = 1;
        for (ChiTietHoaDon chiTietHoaDon : hoaDon.get(0).getDanhSachChiTietHoaDon()) {

            List<SanPham> sanPham = sanPhamDao.timSanPhamTheoTieuChi("maSanPham",
                    chiTietHoaDon.getSanPham().getMaSanPham());

            model.addRow(new Object[]{STT, chiTietHoaDon.getSanPham().getMaSanPham(), sanPham.get(0).getTenSanPham(),
                    "cai", chiTietHoaDon.getSoLuong(), chiTietHoaDon.getDonGia(), currencyFormatter.format(chiTietHoaDon.getThanhTien())});
            STT++;
        }

    }

    private void btnThoatActionPerformed(ActionEvent evt) throws InterruptedException {
        switch (Globals.getNhanVien().getChucVu()) {
            case NHAN_VIEN -> {
                setVisible(false);
                new GiaoDienBanHang().setVisible(true);
            }
            case QUAN_LY -> {
                setVisible(false);
                new GiaoDienQuanLyHoaDon().setVisible(true);
            }
        }
    }
}
