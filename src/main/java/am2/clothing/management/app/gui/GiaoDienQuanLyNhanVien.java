package am2.clothing.management.app.gui;

import am2.clothing.management.dao.NhanVienDao;
import am2.clothing.management.entity.NhanVien;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GiaoDienQuanLyNhanVien extends JFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    MongoClient client = MongoClients.create();
    NhanVienDao nhanVienDao = new NhanVienDao(client);
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

    public GiaoDienQuanLyNhanVien() throws InterruptedException {
        this.setTitle("Giao Diện Quản Lý Nhân Viên");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//setUndecorated(true);
        createGUI();
    }

    private void createGUI() throws InterruptedException {
        JPanel header;

        // Pháº§n North
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
        JLabel lblHeader = new JLabel("Quản Lý Nhân Viên");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 70));
        header.add(lblHeader);
        pn1.add(header);
        pn1.add(head);
// code  table

        pn2 = new JPanel();
        pn2.setBorder(BorderFactory.createTitledBorder("Danh Sách Nhân Viên"));

//get table  fullScreen
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.PAGE_AXIS));

        String[] cols = {"Mã nhân viên", "Họ tên", "Số điện thoại", "Giới tính", "Email", "Ngày sinh", "CMND/CCCD"};
        dtm = new DefaultTableModel(cols, 0);
        table = new JTable(dtm);
        JScrollPane scroll = new JScrollPane(table);
//setsize for table
        scroll.setPreferredSize(new DimensionUIResource(1400, 600));
        pn2.add(scroll);

// code function
        pn3 = new JPanel();
        pn3.setPreferredSize(new Dimension(1500, 100));
        pn3.setBorder(BorderFactory.createTitledBorder("Các chức năng"));
        pn3BL = new JPanel();
        pn3BL.setPreferredSize(new DimensionUIResource(1400, 50));
        pn3BL.setBorder(BorderFactory.createLoweredBevelBorder());
        cboFind = new JComboBox<String>();
        cboFind.setEditable(false);
        cboFind.addItem("Chọn tiêu chí");
        cboFind.addItem("Tìm theo mã nhân viên");
        cboFind.addItem("Tìm theo tên nhân viên");
        cboFind.addItem("Tìm theo số điện thoại");
        txtFind = new JTextField(20);
        JLabel lbFind = new JLabel("Tìm kiếm theo:");
        btnSearch = new JButton("Tìm Kiếm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xoá");
        btnPrint = new JButton("Kết Xuất");
        pn3BL.add(txtFind);
        pn3BL.add(lbFind);
        pn3BL.add(cboFind);

        List<NhanVien> dsNhanVien = nhanVienDao.layDanhSachNhanVien();
        lbFind.setPreferredSize(new DimensionUIResource(100, 40));
        txtFind.setPreferredSize(new DimensionUIResource(100, 40));
        cboFind.setPreferredSize(new DimensionUIResource(150, 40));
        btnSearch.setPreferredSize(new DimensionUIResource(100, 40));
        btnUpdate.setPreferredSize(new DimensionUIResource(150, 40));
        btnDelete.setPreferredSize(new DimensionUIResource(100, 40));
        btnPrint.setPreferredSize(new DimensionUIResource(100, 40));

        btnPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (nhanVienDao.writeToFile("DSNhanVien", dsNhanVien))
                    JOptionPane.showMessageDialog(null, "Kết xuất nhân viên thành công!");
                else
                    JOptionPane.showMessageDialog(null, "Kết xuất nhân viên thất bại!");

            }
        });
        pn3BL.add(btnSearch);
        pn3BL.add(btnUpdate);
        pn3BL.add(btnDelete);
        pn3BL.add(btnPrint);

        pn3.add(pn3BL);

        this.add(pn1, BorderLayout.NORTH);
        this.add(pn2, BorderLayout.CENTER);
        this.add(pn3, BorderLayout.SOUTH);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);
        btnUpdate.addActionListener(this);
        /**
         * load dữ liệu lên table
         */

        for (NhanVien nhanVien : dsNhanVien) {
            dtm.addRow(new Object[]{nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(),
                    nhanVien.isGioiTinh() ? "Nam" : "Nữ", nhanVien.getEmail(), nhanVien.getNgaySinh(),
                    nhanVien.getCMND()});
        }

    }

    private void btnBackActionPerformed(ActionEvent evt) throws InterruptedException {
        new GiaoDienDieuKhien().setVisible(true);
        setVisible(false);
    }

    /**
     * xử lý các chức năng
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnSearch)) {
            try {
                xuLyTimKiem();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (o.equals(btnDelete)) {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xoá");
            } else {
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá nhân viên không!", "Cảnh Báo",
                        JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        nhanVienDao.xoaNhanVien(table.getValueAt(row, 0).toString());
                        dtm.removeRow(row);
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!");
                    }
                }
            }
        } else if (o.equals(btnUpdate)) {
            int select = table.getSelectedRow();

            if (select < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa");
            } else {
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa sản phẩm không!", "Cảnh Báo",
                        JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        String ma = (String) table.getValueAt(select, 0);
                        String ten = (String) table.getValueAt(select, 1);
                        String SDT = (String) table.getValueAt(select, 2);
                        Boolean gioiTinh = (Boolean) table.getValueAt(select, 3).equals("Nam");
                        String email = (String) table.getValueAt(select, 4);
//						Date ngaySinh = (Date) table.getValueAt(select, 5);
                        String CMND = (String) table.getValueAt(select, 6);
                        NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(ma);

                        NhanVien nhanVien_1 = new NhanVien(ma, ten, nhanVien.getNgaySinh(), gioiTinh, email, SDT, CMND,
                                nhanVien.getChucVu());
                        nhanVienDao.suaNhanVien((String) table.getValueAt(select, 0), nhanVien_1);
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thất bại!");
                    }
                }
            }
        }

    }

    private void xuLyTimKiem() throws InterruptedException {
        int select = cboFind.getSelectedIndex();
        switch (select) {

            case 0: {
                List<NhanVien> dsNhanVien = nhanVienDao.layDanhSachNhanVien();
                dtm.setRowCount(0);
                for (NhanVien nhanVien : dsNhanVien) {
                    dtm.addRow(new Object[]{nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(),
                            nhanVien.isGioiTinh() ? "Nam" : "Nữ", nhanVien.getEmail(), nhanVien.getNgaySinh(),
                            nhanVien.getCMND()});
                }
                break;
            }

            case 1: {
                String maNhanVien = txtFind.getText();
                NhanVien nhanVien = nhanVienDao.timNhanVienTheoMa(maNhanVien);
                if (nhanVien != null) {
                    dtm.setRowCount(0);
                    dtm.addRow(new Object[]{nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(),
                            nhanVien.isGioiTinh() ? "Nam" : "Nữ", nhanVien.getEmail(), nhanVien.getNgaySinh(),
                            nhanVien.getCMND()});

                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                }

                break;

            }

            case 2: {
                String tenNhanVien = txtFind.getText();
                NhanVien nhanVien = nhanVienDao.timNhanVienTheoTen(tenNhanVien);
                if (nhanVien != null) {
                    dtm.setRowCount(0);
                    dtm.addRow(new Object[]{nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(),
                            nhanVien.isGioiTinh() ? "Nam" : "Nữ", nhanVien.getEmail(), nhanVien.getNgaySinh(),
                            nhanVien.getCMND()});

                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                }

                break;
            }

            case 3: {
                String soDienThoai = txtFind.getText();
                NhanVien nhanVien = nhanVienDao.timNhanVienTheoSDT(soDienThoai);
                if (nhanVien != null) {
                    dtm.setRowCount(0);
                    dtm.addRow(new Object[]{nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(),
                            nhanVien.isGioiTinh() ? "Nam" : "Nữ", nhanVien.getEmail(), nhanVien.getNgaySinh(),
                            nhanVien.getCMND()});

                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                }

                break;
            }
        }
    }
}
