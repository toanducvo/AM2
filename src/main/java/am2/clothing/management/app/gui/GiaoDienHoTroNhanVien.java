package am2.clothing.management.app.gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GiaoDienHoTroNhanVien extends JFrame {

    private JTextArea txaContent;
    private BufferedReader br;

    public GiaoDienHoTroNhanVien() throws IOException {
        setTitle("Hỗ trợ nhân viên");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        br = new BufferedReader(new FileReader("help/NhanVien.txt"));

        Container cp = getContentPane();
        JLabel lblHeader = new JLabel("Hướng Dẫn Sử Dụng Cho Nhân Viên");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 30));
        cp.add(lblHeader, BorderLayout.NORTH);
        cp.add(new JScrollPane(txaContent = new JTextArea()));

        String text = "";
        String thisLine = null;
        while ((thisLine = br.readLine()) != null) {
            text = thisLine + "\r\n";
            txaContent.append(text);
        }

    }
}
