package am2.clothing.management.app.gui;

import am2.clothing.management.app.Globals;
import am2.clothing.management.dao.KhachHangDao;
import am2.clothing.management.entity.KhachHang;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class GiaoDienQuanLyKhachHang extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnPrint;
    private MongoClient client = MongoClients.create();
    private KhachHangDao khachHangDao = new KhachHangDao(client);

    public GiaoDienQuanLyKhachHang() throws InterruptedException {
        this.setTitle("Giao Diện Quản Lý Khách Hàng");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//setUndecorated(true);

        JPanel header;

        // Phần North
        pn1 = new JPanel();
        head = new JPanel();
        head.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		head.setLayout(new FlowLayout(FlowLayout.LEFT));
        head.setPreferredSize(new Dimension(150, 50));
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
        header.setPreferredSize(new Dimension(1350, 90));
        JLabel lblHeader = new JLabel("Quản Lý Khách Hàng");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 70));
        header.add(lblHeader);
        pn1.add(header);
        pn1.add(head);

// code  table

        pn2 = new JPanel();
        pn2.setBorder(BorderFactory.createTitledBorder("Danh sách Nhân Viên"));

//get table  fullScreen
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.PAGE_AXIS));

        String[] cols = {"Mã khách hàng", "Họ tên", "Số điện thoại", "Giới tính", "Email", "Ngày sinh"};
        dtm = new DefaultTableModel(cols, 0);
        table = new JTable(dtm);
        JScrollPane scroll = new JScrollPane(table);

        List<KhachHang> DSKhachHang = khachHangDao.layDanhSachKhachHang();

        for (KhachHang khachHang : DSKhachHang) {
            dtm.addRow(new Object[]{khachHang.getMaKhachHang(), khachHang.getHoTen(), khachHang.getSoDienThoai(),
                    khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getEmail(), khachHang.getNgaySinh()

            });
        }
//setsize for table
        scroll.setPreferredSize(new DimensionUIResource(1400, 600));
        pn2.add(scroll);

// code function
        pn3 = new JPanel();
        pn3.setPreferredSize(new Dimension(1500, 100));
        pn3.setBorder(BorderFactory.createTitledBorder("các chức năng"));
        pn3BL = new JPanel();
        pn3BL.setPreferredSize(new DimensionUIResource(1400, 50));
        pn3BL.setBorder(BorderFactory.createLoweredBevelBorder());
        cboFind = new JComboBox<String>();
        cboFind.setEditable(false);
        cboFind.addItem("Chọn tiêu chí cần tìm");
        cboFind.addItem("Tìm theo mã khách hàng");
        cboFind.addItem("Tìm theo tên khách hàng");
        cboFind.addItem("Tìm theo số điện thoại");
        txtFind = new JTextField(20);
        JLabel lbFind = new JLabel("Tìm Kiếm theo:");
        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnSearchActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        btnUpdate = new JButton("Cập Nhật");

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnUpdateActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        btnDelete = new JButton("Xoá");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnPrint = new JButton("Kết Xuất");
        btnPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (khachHangDao.writeToFile("DSKhachHang", DSKhachHang))
                        JOptionPane.showMessageDialog(null, "Kết xuất thành công");
                    else
                        JOptionPane.showMessageDialog(null, "Kết xuất thất bại!");
                } catch (HeadlessException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        pn3BL.add(txtFind);
        pn3BL.add(lbFind);
        pn3BL.add(cboFind);

        lbFind.setPreferredSize(new DimensionUIResource(100, 40));
        txtFind.setPreferredSize(new DimensionUIResource(100, 40));
        cboFind.setPreferredSize(new DimensionUIResource(150, 40));
        btnSearch.setPreferredSize(new DimensionUIResource(100, 40));
        btnUpdate.setPreferredSize(new DimensionUIResource(150, 40));
        btnDelete.setPreferredSize(new DimensionUIResource(100, 40));
        btnPrint.setPreferredSize(new DimensionUIResource(100, 40));
        pn3BL.add(btnSearch);
        pn3BL.add(btnUpdate);
        pn3BL.add(btnDelete);
        pn3BL.add(btnPrint);
        pn3.add(pn3BL);

        this.add(pn1, BorderLayout.NORTH);
        this.add(pn2, BorderLayout.CENTER);
        this.add(pn3, BorderLayout.SOUTH);
    }

    private void btnBackActionPerformed(ActionEvent evt) throws InterruptedException {
        switch (Globals.getNhanVien().getChucVu()) {
            case NHAN_VIEN -> {
                setVisible(false);
                new GiaoDienBanHang().setVisible(true);
            }
            case QUAN_LY -> {
                setVisible(false);
                new GiaoDienDieuKhien().setVisible(true);
            }
        }
    }

    private void btnUpdateActionPerformed(ActionEvent evt) throws InterruptedException {

        int select = table.getSelectedRow();

        if (select < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa");
        } else {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa khách hàng không!", "Cảnh Báo",
                    JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    String ma = (String) table.getValueAt(select, 0);
                    String ten = (String) table.getValueAt(select, 1);
                    String SDT = (String) table.getValueAt(select, 2);
                    Boolean gioiTinh = (Boolean) table.getValueAt(select, 3).equals("Nam");
                    String email = (String) table.getValueAt(select, 4);
                    // String ngaySinh = (String) table.getValueAt(select, 5);

                    List<KhachHang> DSKhachHang = khachHangDao.timKhachHangTheoMa(ma);
                    KhachHang khachHang = new KhachHang(ma, ten, SDT, gioiTinh, email,
                            DSKhachHang.get(0).getNgaySinh());
                    khachHangDao.capNhatThongTinKhachHang((String) table.getValueAt(select, 0), khachHang);
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
                } catch (Exception e2) {
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!");
                }
            }
        }
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
        } else {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá khách hàng này không!", "Cảnh Báo",
                    JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    khachHangDao.xoaKhachHang(table.getValueAt(row, 0).toString());
                    dtm.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");

                } catch (Exception e2) {
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
                }
            }

        }

    }

    private void btnSearchActionPerformed(ActionEvent evt) throws InterruptedException {
        int select = cboFind.getSelectedIndex();
        switch (select) {

            case 0: {
                List<KhachHang> DSKhachHang = khachHangDao.layDanhSachKhachHang();
                dtm.setRowCount(0);
                for (KhachHang khachHang : DSKhachHang) {
                    dtm.addRow(new Object[]{khachHang.getMaKhachHang(), khachHang.getHoTen(), khachHang.getSoDienThoai(),
                            khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getEmail(), khachHang.getNgaySinh()

                    });
                }

                break;
            }

            case 1: {
                String maKhachHang = txtFind.getText();

                List<KhachHang> DSKhachHang = khachHangDao.timKhachHangTheoMa(maKhachHang);

                if (DSKhachHang.size() != 0) {
                    dtm.setRowCount(0);

                    for (KhachHang khachHang : DSKhachHang) {

                        dtm.addRow(new Object[]{khachHang.getMaKhachHang(), khachHang.getHoTen(),
                                khachHang.getSoDienThoai(), khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getEmail(),
                                khachHang.getNgaySinh()});
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
                }

                break;

            }

            case 2: {
                String tenKhachHang = txtFind.getText();

                List<KhachHang> DSKhachHang = khachHangDao.timKhachHangTheoTen(tenKhachHang);

                if (DSKhachHang.size() != 0) {
                    dtm.setRowCount(0);

                    for (KhachHang khachHang : DSKhachHang) {

                        dtm.addRow(new Object[]{khachHang.getMaKhachHang(), khachHang.getHoTen(),
                                khachHang.getSoDienThoai(), khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getEmail(),
                                khachHang.getNgaySinh()});
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khác hàng!");
                }

                break;
            }

            case 3: {

                String SDTKhachHang = txtFind.getText();

                KhachHang khachHang = khachHangDao.timKhachHangTheoSDT(SDTKhachHang);
                if (khachHang != null) {
                    dtm.setRowCount(0);

                    dtm.addRow(new Object[]{khachHang.getMaKhachHang(), khachHang.getHoTen(), khachHang.getSoDienThoai(),
                            khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getEmail(), khachHang.getNgaySinh()});

                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khác hàng!");
                }

                break;
            }
        }
    }

}