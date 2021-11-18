package am2.clothing.management.app.gui;

import am2.clothing.management.dao.LoaiSanPhamDao;
import am2.clothing.management.dao.NhaCungCapDao;
import am2.clothing.management.dao.SanPhamDao;
import am2.clothing.management.entity.LoaiSanPham;
import am2.clothing.management.entity.NhaCungCap;
import am2.clothing.management.entity.SanPham;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class GiaoDienThemSanPham extends JFrame implements ActionListener {
    /**
     *
     */
    MongoClient client = MongoClients.create();
    NhaCungCapDao nhaCCDao = new NhaCungCapDao(client);
    LoaiSanPhamDao loaiSPDao = new LoaiSanPhamDao(client);
    SanPhamDao sanPhanDao = new SanPhamDao(client);
    /**
     *
     */
    private JButton btnQuayLai;
    private JButton btnThemSanPham;
    private JComboBox<String> cboMaLoai;
    private JComboBox<String> cboMaNCC;
    private JLabel lblChinh;
    private JLabel lblMaNCC;
    private JLabel lblTenSanPham;
    private JLabel lblGia;
    private JLabel lblSoLuong;
    private JLabel lblKichCo;
    private JLabel lblThuongHieu;
    private JLabel lblMauSac;
    private JLabel lblHinhAnh;
    private JLabel lblMaLoai;
    private JPanel pnlChinh;
    private JPanel pnlTop;
    private JPanel pnlBot;
    private JTextField txtTenSanPham;
    private JTextField txtGia;
    private JTextField txtSoLuong;
    private JTextField txtKichCo;
    private JTextField txtThuongHieu;
    private JTextField txtMauSac;
    private JTextField txtHinhAnh;

    public GiaoDienThemSanPham() throws InterruptedException {

        pnlChinh = new JPanel();
        pnlTop = new JPanel();
        lblChinh = new JLabel();
        btnQuayLai = new JButton();
        pnlBot = new JPanel();
        lblTenSanPham = new JLabel();
        lblGia = new JLabel();
        lblSoLuong = new JLabel();
        lblKichCo = new JLabel();
        lblThuongHieu = new JLabel();
        lblMauSac = new JLabel();
        lblHinhAnh = new JLabel();
        lblMaLoai = new JLabel();
        lblMaNCC = new JLabel();
        txtTenSanPham = new JTextField();
        txtGia = new JTextField();
        txtSoLuong = new JTextField();
        txtKichCo = new JTextField();
        txtThuongHieu = new JTextField();
        txtMauSac = new JTextField();
        txtHinhAnh = new JTextField();
        btnThemSanPham = new JButton();
        cboMaLoai = new JComboBox<>();
        cboMaNCC = new JComboBox<>();

        setTitle("Thêm Sản Phẩm");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        pnlTop.setBorder(BorderFactory.createTitledBorder(""));

        lblChinh.setFont(new Font("Tahoma", 0, 48));
        lblChinh.setText("THÊM SẢN PHẨM");

        btnQuayLai.setFont(new Font("Tahoma", 0, 14));
        btnQuayLai.setText("Quay Lại");
        btnQuayLai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnQuayLaiActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        btnThemSanPham.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnThemSanPhamActionPerformed(evt);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        GroupLayout pnlTopLayout = new GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(pnlTopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        pnlTopLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnQuayLai))
                .addGroup(GroupLayout.Alignment.TRAILING,
                        pnlTopLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblChinh).addGap(573, 573, 573)));
        pnlTopLayout.setVerticalGroup(pnlTopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup().addComponent(btnQuayLai)
                        .addGap(18, 18, 18).addComponent(lblChinh).addContainerGap(45, Short.MAX_VALUE)));

        pnlBot.setBorder(BorderFactory.createTitledBorder(""));

        lblTenSanPham.setFont(new Font("Tahoma", 0, 24));
        lblTenSanPham.setText("Tên Sản Phẩm:");

        lblGia.setFont(new Font("Tahoma", 0, 24));
        lblGia.setText("Giá:");

        lblSoLuong.setFont(new Font("Tahoma", 0, 24));
        lblSoLuong.setText("Số Lượng:");

        lblKichCo.setFont(new Font("Tahoma", 0, 24));
        lblKichCo.setText("Kích Cỡ:");

        lblThuongHieu.setFont(new Font("Tahoma", 0, 24));
        lblThuongHieu.setText("Thương Hiệu:");

        lblMauSac.setFont(new Font("Tahoma", 0, 24));
        lblMauSac.setText("Màu Sắc:");

        lblHinhAnh.setFont(new Font("Tahoma", 0, 24));
        lblHinhAnh.setText("Hình Ảnh:");

        lblMaLoai.setFont(new Font("Tahoma", 0, 24));
        lblMaLoai.setText("Loại Sản Phẩm");

        lblMaNCC.setFont(new Font("Tahoma", 0, 24));
        lblMaNCC.setText("Nhà Cung Cấp");

        txtTenSanPham.setFont(new Font("Tahoma", 0, 24));

        txtGia.setFont(new Font("Tahoma", 0, 24));

        txtSoLuong.setFont(new Font("Tahoma", 0, 24));

        txtKichCo.setFont(new Font("Tahoma", 0, 24));

        txtThuongHieu.setFont(new Font("Tahoma", 0, 24));

        txtMauSac.setFont(new Font("Tahoma", 0, 24));

        txtHinhAnh.setFont(new Font("Tahoma", 0, 24));

        btnThemSanPham.setFont(new Font("Tahoma", 0, 24));
        btnThemSanPham.setText("Thêm Sản Phẩm");
        /**
         * load dữ liệu mã loại sản phẩm lên jcombobox
         */
        cboMaLoai.setFont(new Font("Tahoma", 0, 24));
        cboMaLoai.setModel(new DefaultComboBoxModel<>(new String[]{}));
        List<LoaiSanPham> dsMaLoai = loaiSPDao.layDanhSachLoaiSanPham();
        for (LoaiSanPham loaiSanPham : dsMaLoai) {
            cboMaLoai.addItem(loaiSanPham.getTenLoai());
        }
        /**
         * load dữ liệu mã nhà cung cấp lên jcombobox
         */
        cboMaNCC.setFont(new Font("Tahoma", 0, 24));
        cboMaNCC.setModel(new DefaultComboBoxModel<>(new String[]{}));
        cboMaNCC.setPreferredSize(new Dimension(50, 40));
        List<NhaCungCap> dsNhaCC = nhaCCDao.getListSupllier();
        for (NhaCungCap nhaCungCap : dsNhaCC) {
            cboMaNCC.addItem(nhaCungCap.getTenNhaCungCap());
        }
        GroupLayout pnlBotLayout = new GroupLayout(pnlBot);
        pnlBot.setLayout(pnlBotLayout);
        pnlBotLayout.setHorizontalGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        pnlBotLayout.createSequentialGroup().addContainerGap(156, Short.MAX_VALUE)
                                .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblThuongHieu).addComponent(lblTenSanPham).addComponent(lblGia)
                                        .addComponent(lblSoLuong).addComponent(lblKichCo))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtThuongHieu)
                                        .addComponent(txtTenSanPham, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtGia, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtKichCo, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(163, 163, 163)
                                .addGroup(pnlBotLayout
                                        .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblHinhAnh)
                                        .addComponent(lblMaLoai).addComponent(lblMauSac).addComponent(lblMaNCC))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtHinhAnh).addComponent(txtMauSac)
                                        .addComponent(cboMaNCC, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboMaLoai, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(147, 147, 147))
                .addGroup(pnlBotLayout.createSequentialGroup().addGap(647, 647, 647).addComponent(btnThemSanPham)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlBotLayout.setVerticalGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlBotLayout.createSequentialGroup().addGap(41, 41, 41)
                        .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTenSanPham).addComponent(lblMauSac)
                                .addComponent(txtTenSanPham, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMauSac, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblGia)
                                .addComponent(lblHinhAnh)
                                .addComponent(txtGia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHinhAnh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSoLuong).addComponent(lblMaLoai)
                                .addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMaLoai, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblKichCo).addComponent(lblMaNCC)
                                .addComponent(txtKichCo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMaNCC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(pnlBotLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblThuongHieu).addComponent(txtThuongHieu, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btnThemSanPham, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)));

        GroupLayout pnlChinhLayout = new GroupLayout(pnlChinh);
        pnlChinh.setLayout(pnlChinhLayout);
        pnlChinhLayout.setHorizontalGroup(pnlChinhLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlChinhLayout.createSequentialGroup().addContainerGap()
                        .addGroup(pnlChinhLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(pnlBot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(pnlTop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .addContainerGap()));
        pnlChinhLayout.setVerticalGroup(pnlChinhLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlChinhLayout.createSequentialGroup().addContainerGap()
                        .addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlBot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(pnlChinh,
                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(pnlChinh,
                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        btnThemSanPham.addActionListener(this);
    }

    private void btnQuayLaiActionPerformed(ActionEvent evt) throws InterruptedException {
        new GiaoDienQuanLySanPham().setVisible(true);
        setVisible(false);
    }

    private void btnThemSanPhamActionPerformed(ActionEvent evt) throws InterruptedException {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThemSanPham)) {
            if (kiemTraDuLieuNhap()) {
                String maSanPham = "";
                String tenSanPham = txtTenSanPham.getText();
                double gia = Double.parseDouble((String) txtGia.getText());
                int soLuong = Integer.parseInt((String) txtSoLuong.getText());
                List<String> dsKichCo = Arrays.asList(txtKichCo.getText());
                String thuongHieu = txtThuongHieu.getText();
                List<String> dsMauSau = Arrays.asList(txtMauSac.getText());
                String hinhAnh = txtHinhAnh.getText();
                String tenLoai = (String) cboMaLoai.getSelectedItem();
//				LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai);
                List<LoaiSanPham> loaiSanPham = null;
                try {
                    loaiSanPham = loaiSPDao.timTheoTenLoai(tenLoai);
                } catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                String maNCC = (String) cboMaNCC.getSelectedItem();
//				NhaCungCap nhaCungCap = new NhaCungCap(maNCC);
                List<NhaCungCap> nhaCungCap = null;
                try {
                    nhaCungCap = nhaCCDao.timTheoTenNhaCC(maNCC);
                } catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                SanPham sanPham = new SanPham(maSanPham, loaiSanPham.get(0), nhaCungCap.get(0), tenSanPham, gia,
                        soLuong, dsKichCo, thuongHieu, dsMauSau, hinhAnh);

                try {
                    if (sanPhanDao.themSanPham(sanPham)) {
                        new GiaoDienQuanLySanPham().setVisible(true);
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại!");
                    }
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    private boolean kiemTraDuLieuNhap() {
        String tenSp = txtTenSanPham.getText().trim();
        String giaSp = txtGia.getText().trim();
        String soLuong = txtSoLuong.getText().trim();
        String kichCo = txtKichCo.getText().trim();
        String thuongHieu = txtThuongHieu.getText().trim();
        String mauSac = txtMauSac.getText().trim();
        String hinhAnh = txtHinhAnh.getText().trim();
        if (!(tenSp.length() > 0 && tenSp.matches(
                "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$"))) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không hợp lệ VD: Áo Khoác, vui lòng kiểm tra lại!");
            return false;
        }
        if (giaSp.length() > 0) {
            try {
                double x = Double.parseDouble(giaSp);
                if (x < 0) {
                    JOptionPane.showMessageDialog(this, "Giá sản phẩm >0");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Phải nhập số");
                return false;
            }
        }
        if (soLuong.length() > 0) {
            try {
                int y = Integer.parseInt(soLuong);
                if (y < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng >0");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Phải nhập số ");
                return false;
            }
        }
        if (!(thuongHieu.length() > 0 && thuongHieu.matches(
                "^[a-zA-Z0-9_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$"))) {
            JOptionPane.showMessageDialog(this, "Thương hiệu không hợp lệ VD: Yame, vui lòng kiểm tra lại!");
            return false;
        }
        if (!(kichCo.length() > 0 && kichCo.matches(
                "^[a-zA-Z0-9_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$"))) {
            JOptionPane.showMessageDialog(this, "Kích cỡ không hợp lệ VD: M, vui lòng kiểm tra lại!");
            return false;
        }
        if (!(mauSac.length() > 0 && mauSac.matches(
                "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$"))) {
            JOptionPane.showMessageDialog(this, "Màu sắc không hợp lệ VD: Tím, vui lòng kiểm tra lại!");
            return false;
        }
        if (!(hinhAnh.length() > 0 && hinhAnh.matches("[a-zA-Z0-9]+.[a-zA-Z]{3}"))) {
            JOptionPane.showMessageDialog(this, "Hình ảnh không hợp lệ VD: SP00001.png, vui lòng kiểm tra lại!");
            return false;
        }
        return true;
    }

}
