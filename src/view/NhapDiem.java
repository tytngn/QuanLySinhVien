/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import controller.Document;
import controller.Document.RealNumberFilter;
import static controller.Program.connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author nguyenthituyetngan
 */
public class NhapDiem extends javax.swing.JDialog {

    private boolean stt = true;

    public NhapDiem(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setValueDiemTF();

    }

    // Các trường hợp cấm nhập vào JTextField
    public void setValueDiemTF() {
        // Áp dụng RealNumberFilter cho JTextField
        ((AbstractDocument) Diem1TextField.getDocument()).setDocumentFilter(new Document.RealNumberFilter());
        ((AbstractDocument) Diem2TextField.getDocument()).setDocumentFilter(new Document.RealNumberFilter());
    }

    // Load mã lớp lên JComboBox
    private void reloadMaLop() {
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT MaLop FROM QLSV.DM_Lop;");
            while (rs.next()) {
                MaLopTextField.setText(rs.getString(1));
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load năm học lên JComboBox
    private void reloadNamHoc() {
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT NamHoc FROM QLSV.NAMHOC;");

            DefaultComboBoxModel<String> CBModel = (DefaultComboBoxModel<String>) NamHocComboBox.getModel();

            while (rs.next()) {
                CBModel.addElement(rs.getString(1));
            }
            CBModel.setSelectedItem(null);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load mã môn học lên JComboBox
    private void reloadMonHoc() {
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT MaMH FROM QLSV.DM_MonHoc;");

            DefaultComboBoxModel<String> CBModel = (DefaultComboBoxModel<String>) MaMHComboBox.getModel();

            while (rs.next()) {
                CBModel.addElement(rs.getString(1));
            }
            CBModel.setSelectedItem(null);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // // Load thông tin sinh viên bao gồm MSSV, HoTen, MaLop, TenLop, NamHoc, MonHoc
    public void loadDatafromSV(Object mssv) {
        try {

            MSSVTextField.setEditable(false);
            HoTenTextField.setEditable(false);
            MaLopTextField.setEnabled(false);
            TenLopTextField.setEditable(false);

            Statement s = connection.createStatement();

            // lấy dữ liệu và hiển thị lên MSSV và Họ tên 
            ResultSet rsv = s.executeQuery("SELECT * FROM SINHVIEN WHERE MaSV = N'" + mssv + "';");
            if (rsv.next()) {
                MSSVTextField.setText((String) mssv);
                HoTenTextField.setText((String) rsv.getString(2));
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Mã số sinh viên không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // lấy dữ liệu và hiển thị lên Mã lớp và Tên lớp
            reloadMaLop();
            ResultSet rsl = s.executeQuery("SELECT SINHVIEN.MaLop, DM_Lop.TenLop\n"
                    + "FROM SINHVIEN\n"
                    + "INNER JOIN DM_Lop ON SINHVIEN.MaLop = DM_Lop.MaLop\n"
                    + "WHERE SINHVIEN.MaSV = '" + mssv + "';");
            if (rsl.next()) {
                MaLopTextField.setText(rsl.getString("MaLop"));
                TenLopTextField.setText((String) rsl.getString("TenLop"));
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Mã lớp không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // Lấy dữ liệu và hiển thị Năm học
            reloadNamHoc();
            ResultSet rsn = s.executeQuery("SELECT NamHoc FROM QLSV.NAMHOC;");
            if (rsn.next()) {
                NamHocComboBox.setSelectedItem(rsn.getString(1));
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Năm học không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // lấy dữ liệu và hiển thị lên mã môn học và môn học
            reloadMonHoc();
            MonHocTextField.setText("");

            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy điểm đã có trong csdl theo Năm học - học kỳ - môn học
    public void loadDiemSV() {
        if (MaMHComboBox.getSelectedItem() == null || NamHocComboBox.getSelectedItem() == null || HocKyComboBox.getSelectedItem() == null) {
            return;
        }
        try {
            Statement s = connection.createStatement();
            String MaSV = MSSVTextField.getText();
            String MaMH = MaMHComboBox.getSelectedItem().toString();
            String NamHoc = NamHocComboBox.getSelectedItem().toString();
            String HocKy = HocKyComboBox.getSelectedItem().toString();

            ResultSet rs = s.executeQuery("Select Diem1, Diem2, DiemTB, XepLoai from BANGDIEM "
                    + "WHERE MaSV='" + MaSV + "' AND MaMH='" + MaMH + "' "
                    + "AND NamHoc = N'" + NamHoc + "' AND HocKy = '" + HocKy + "'");
            if (rs.next()) {
                Diem1TextField.setText(String.valueOf(rs.getFloat(1)));
                Diem2TextField.setText(String.valueOf(rs.getFloat(2)));
                DiemTBTextField.setText(String.valueOf(rs.getFloat(3)));
                XepLoaiTextField.setText(String.valueOf(rs.getString(4)));
                stt = false;
            } else {
                Diem1TextField.setText("");
                Diem2TextField.setText("");
                DiemTBTextField.setText("");
                XepLoaiTextField.setText("");
                stt = true;
            }
            if (MaMHComboBox.getSelectedIndex() == -1) {
                Diem1TextField.setText("");
                Diem2TextField.setText("");
                DiemTBTextField.setText("");
                XepLoaiTextField.setText("");
                stt = true;
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Kiểm tra dữ liệu có rỗng không
    public void checkData() {
        if (HocKyComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Lỗi! Vui lòng chọn học kỳ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        if (MaMHComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Lỗi! Vui lòng chọn mã môn học.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        if (Diem1TextField.getText() == null) {
            JOptionPane.showMessageDialog(this, "Lỗi! Vui lòng nhập điểm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        if (Diem2TextField.getText() == null) {
            JOptionPane.showMessageDialog(this, "Lỗi! Vui lòng nhập điểm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Kiểm tra điểm số thực
    public static boolean isFloat(String text) {
        try {
            float value = Float.parseFloat(text);
            return value >= 0 && value <= 10;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lỗi! Vui lòng nhập điểm hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Xếp loại
    public void setXepLoai(String MaSV, String MaMH, String NamHoc, String HocKy) {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate("UPDATE BANGDIEM\n"
                    + "SET XepLoai = CASE \n"
                    + "                WHEN DiemTB >= 8 THEN 'Giỏi'\n"
                    + "                WHEN DiemTB >= 6.5 THEN 'Khá'\n"
                    + "                WHEN DiemTB >= 5 THEN 'Trung bình'\n"
                    + "                ELSE 'Yếu'\n"
                    + "              END\n"
                    + "WHERE MaSV = '" + MaSV + "' AND MaMH = '" + MaMH + "' AND NamHoc = '" + NamHoc + "' AND HocKy = '" + HocKy + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá dữ liệu điểm đối với SV
    public void clearDiem() {
        HocKyComboBox.setSelectedIndex(-1);
        MaMHComboBox.setSelectedIndex(-1);
        MonHocTextField.setText("");
        Diem1TextField.setText("");
        Diem2TextField.setText("");
        DiemTBTextField.setText("");
        XepLoaiTextField.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderPanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        UnderPanel = new javax.swing.JPanel();
        TopPanel = new javax.swing.JPanel();
        MSSVLabel = new javax.swing.JLabel();
        MSSVTextField = new javax.swing.JTextField();
        HoTenLabel = new javax.swing.JLabel();
        MaLopTextField = new javax.swing.JTextField();
        MaLopLabel = new javax.swing.JLabel();
        NamHocLabel = new javax.swing.JLabel();
        NamHocComboBox = new javax.swing.JComboBox<>();
        HocKyLabel = new javax.swing.JLabel();
        HocKyComboBox = new javax.swing.JComboBox<>();
        MaMHLabel = new javax.swing.JLabel();
        MaMHComboBox = new javax.swing.JComboBox<>();
        TenLopLabel = new javax.swing.JLabel();
        TenLopTextField = new javax.swing.JTextField();
        MonHocLabel = new javax.swing.JLabel();
        MonHocTextField = new javax.swing.JTextField();
        HoTenTextField = new javax.swing.JTextField();
        BottomPanel = new javax.swing.JPanel();
        Diem1Label = new javax.swing.JLabel();
        Diem2Label = new javax.swing.JLabel();
        Diem1TextField = new javax.swing.JTextField();
        Diem2TextField = new javax.swing.JTextField();
        DiemTBLabel = new javax.swing.JLabel();
        XepLoaiLabel = new javax.swing.JLabel();
        DiemTBTextField = new javax.swing.JTextField();
        XepLoaiTextField = new javax.swing.JTextField();
        ControlPanel = new javax.swing.JPanel();
        LuuButton = new javax.swing.JButton();
        XoaButton = new javax.swing.JButton();
        ThoatButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nhập điểm môn học");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        HeaderPanel.setBackground(new java.awt.Color(247, 104, 2));

        TitleLabel.setFont(new java.awt.Font("Skia", 1, 36)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("NHẬP ĐIỂM");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );

        UnderPanel.setBackground(new java.awt.Color(255, 255, 255));

        TopPanel.setBackground(new java.awt.Color(249, 247, 201));

        MSSVLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MSSVLabel.setText("Mã số sinh viên");

        MSSVTextField.setEditable(false);
        MSSVTextField.setBackground(new java.awt.Color(255, 255, 255));
        MSSVTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        MSSVTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        MSSVTextField.setEnabled(false);

        HoTenLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        HoTenLabel.setText("Họ và tên");

        MaLopTextField.setEditable(false);
        MaLopTextField.setBackground(new java.awt.Color(255, 255, 255));
        MaLopTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        MaLopTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        MaLopTextField.setEnabled(false);

        MaLopLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MaLopLabel.setText("Mã lớp");

        NamHocLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        NamHocLabel.setText("Năm học");

        NamHocComboBox.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        NamHocComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NamHocComboBoxItemStateChanged(evt);
            }
        });

        HocKyLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        HocKyLabel.setText("Học kỳ");

        HocKyComboBox.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        HocKyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        HocKyComboBox.setSelectedIndex(-1);
        HocKyComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HocKyComboBoxItemStateChanged(evt);
            }
        });

        MaMHLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MaMHLabel.setText("Mã môn học");

        MaMHComboBox.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        MaMHComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MaMHComboBoxItemStateChanged(evt);
            }
        });

        TenLopLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        TenLopLabel.setText("Tên lớp");

        TenLopTextField.setEditable(false);
        TenLopTextField.setBackground(new java.awt.Color(255, 255, 255));
        TenLopTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        TenLopTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TenLopTextField.setEnabled(false);

        MonHocLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MonHocLabel.setText("Môn học");

        MonHocTextField.setEditable(false);
        MonHocTextField.setBackground(new java.awt.Color(255, 255, 255));
        MonHocTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        MonHocTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        MonHocTextField.setEnabled(false);

        HoTenTextField.setEditable(false);
        HoTenTextField.setBackground(new java.awt.Color(255, 255, 255));
        HoTenTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        HoTenTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        HoTenTextField.setEnabled(false);

        javax.swing.GroupLayout TopPanelLayout = new javax.swing.GroupLayout(TopPanel);
        TopPanel.setLayout(TopPanelLayout);
        TopPanelLayout.setHorizontalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TenLopLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MaLopLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(TenLopTextField)
                    .addComponent(MaLopTextField)
                    .addComponent(HoTenTextField))
                .addGap(50, 50, 50)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(MaMHLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NamHocLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MonHocLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HocKyLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NamHocComboBox, 0, 220, Short.MAX_VALUE)
                    .addComponent(MaMHComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MonHocTextField)
                    .addComponent(HocKyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TopPanelLayout.setVerticalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamHocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamHocComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HocKyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HocKyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HoTenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MaLopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MaLopTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MaMHLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MaMHComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TenLopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TenLopTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MonHocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MonHocTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        BottomPanel.setBackground(new java.awt.Color(249, 247, 201));

        Diem1Label.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        Diem1Label.setText("Điểm lần 1");

        Diem2Label.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        Diem2Label.setText("Điểm lần 2");

        Diem1TextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        Diem2TextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        DiemTBLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        DiemTBLabel.setText("Điểm trung bình");

        XepLoaiLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        XepLoaiLabel.setText("Xếp loại");

        DiemTBTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        XepLoaiTextField.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        ControlPanel.setBackground(new java.awt.Color(255, 255, 255));

        LuuButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        LuuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-save-20.png"))); // NOI18N
        LuuButton.setText("Lưu");
        LuuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuuButtonActionPerformed(evt);
            }
        });

        XoaButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        XoaButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-trash.png"))); // NOI18N
        XoaButton.setText("Xoá");
        XoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaButtonActionPerformed(evt);
            }
        });

        ThoatButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        ThoatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-exit-20.png"))); // NOI18N
        ThoatButton.setText("Thoát");
        ThoatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThoatButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(LuuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(ThoatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LuuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ThoatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout BottomPanelLayout = new javax.swing.GroupLayout(BottomPanel);
        BottomPanel.setLayout(BottomPanelLayout);
        BottomPanelLayout.setHorizontalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Diem1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DiemTBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Diem1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DiemTBTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Diem2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XepLoaiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Diem2TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(XepLoaiTextField))
                .addGap(6, 6, 6))
        );
        BottomPanelLayout.setVerticalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Diem1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Diem1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Diem2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Diem2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(XepLoaiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(XepLoaiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiemTBTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                        .addComponent(DiemTBLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)))
                .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout UnderPanelLayout = new javax.swing.GroupLayout(UnderPanel);
        UnderPanel.setLayout(UnderPanelLayout);
        UnderPanelLayout.setHorizontalGroup(
            UnderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        UnderPanelLayout.setVerticalGroup(
            UnderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UnderPanelLayout.createSequentialGroup()
                .addComponent(TopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 192, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(UnderPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UnderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MaMHComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MaMHComboBoxItemStateChanged
        try {
            Statement s = connection.createStatement();

            ResultSet rsm = s.executeQuery("SELECT TenMH FROM QLSV.DM_MonHoc WHERE MaMH = '" + evt.getItem() + "';");
            if (rsm.next()) {
                MonHocTextField.setText((String) rsm.getString("TenMH"));
                if (NamHocComboBox.getSelectedItem() != null && HocKyComboBox.getSelectedItem() != null && MaMHComboBox.getSelectedItem() != null) {
                    loadDiemSV();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Mã môn học không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDiem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MaMHComboBoxItemStateChanged

    private void LuuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LuuButtonActionPerformed
        checkData(); // Kiểm tra dữ liệu có rỗng không

        // Kiểm tra điểm có hợp lệ không
        if (!isFloat(Diem1TextField.getText())) {
            Diem1TextField.setText("");
        }
        if (!isFloat(Diem2TextField.getText())) {
            Diem2TextField.setText("");
        }

        try {
            Statement s = connection.createStatement();
            String MaSV = MSSVTextField.getText();
            String MaMH = MaMHComboBox.getSelectedItem().toString();
            String NamHoc = NamHocComboBox.getSelectedItem().toString();
            String HocKy = HocKyComboBox.getSelectedItem().toString();
            String Diem1 = Diem1TextField.getText();
            String Diem2 = Diem2TextField.getText();

            int r;
            if (stt == true) {
                r = s.executeUpdate("INSERT INTO `QLSV`.`BANGDIEM` (`MaSV`, `MaMH`, `NamHoc`, `HocKy`, `Diem1`, `Diem2`) VALUES ('"
                        + MaSV + "', '" + MaMH + "', '" + NamHoc + "', '" + HocKy + "', '" + Diem1 + "', '" + Diem2 + "');");
                if (r == 1) {
                    JOptionPane.showMessageDialog(this, "Đã lưu điểm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                r = s.executeUpdate("UPDATE `QLSV`.`BANGDIEM` SET `Diem1` = '" + Diem1 + "', `Diem2` = '" + Diem2 + "' WHERE (`HocKy` = '"
                        + HocKy + "') and (`MaSV` = '" + MaSV + "') and (`MaMH` = '" + MaMH + "') and (`NamHoc` = '" + NamHoc + "');");
                if (r == 1) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật điểm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (r == 1) {
                setXepLoai(MaSV, MaMH, NamHoc, HocKy);
            }
            loadDiemSV();
            s.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_LuuButtonActionPerformed

    private void XoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaButtonActionPerformed
        try {
            Statement s = connection.createStatement();

            String MaSV = MSSVTextField.getText();
            String MaMH = MaMHComboBox.getSelectedItem().toString();
            String NamHoc = NamHocComboBox.getSelectedItem().toString();
            String HocKy = HocKyComboBox.getSelectedItem().toString();

            int x = s.executeUpdate("DELETE FROM `QLSV`.`BANGDIEM` WHERE (`HocKy` = '" + HocKy + "') and (`MaSV` = '" + MaSV + "') and (`MaMH` = '" + MaMH + "') and (`NamHoc` = '" + NamHoc + "');");
            if (x == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Chưa có điểm, không thể xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            s.close();
            loadDiemSV();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_XoaButtonActionPerformed

    private void ThoatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThoatButtonActionPerformed
        try {
            this.setVisible(false);
            DM_TTSV.getInstance().reload();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_ThoatButtonActionPerformed

    private void NamHocComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NamHocComboBoxItemStateChanged
        if (NamHocComboBox.getSelectedItem() != null && HocKyComboBox.getSelectedItem() != null && MaMHComboBox.getSelectedItem() != null) {
            loadDiemSV();
        }
    }//GEN-LAST:event_NamHocComboBoxItemStateChanged

    private void HocKyComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HocKyComboBoxItemStateChanged
        if (NamHocComboBox.getSelectedItem() != null && HocKyComboBox.getSelectedItem() != null && MaMHComboBox.getSelectedItem() != null) {
            loadDiemSV();
        }
    }//GEN-LAST:event_HocKyComboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BottomPanel;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JLabel Diem1Label;
    private javax.swing.JTextField Diem1TextField;
    private javax.swing.JLabel Diem2Label;
    private javax.swing.JTextField Diem2TextField;
    private javax.swing.JLabel DiemTBLabel;
    private javax.swing.JTextField DiemTBTextField;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HoTenLabel;
    private javax.swing.JTextField HoTenTextField;
    private javax.swing.JComboBox<String> HocKyComboBox;
    private javax.swing.JLabel HocKyLabel;
    private javax.swing.JButton LuuButton;
    private javax.swing.JLabel MSSVLabel;
    private javax.swing.JTextField MSSVTextField;
    private javax.swing.JLabel MaLopLabel;
    private javax.swing.JTextField MaLopTextField;
    private javax.swing.JComboBox<String> MaMHComboBox;
    private javax.swing.JLabel MaMHLabel;
    private javax.swing.JLabel MonHocLabel;
    private javax.swing.JTextField MonHocTextField;
    private javax.swing.JComboBox<String> NamHocComboBox;
    private javax.swing.JLabel NamHocLabel;
    private javax.swing.JLabel TenLopLabel;
    private javax.swing.JTextField TenLopTextField;
    private javax.swing.JButton ThoatButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JPanel UnderPanel;
    private javax.swing.JLabel XepLoaiLabel;
    private javax.swing.JTextField XepLoaiTextField;
    private javax.swing.JButton XoaButton;
    // End of variables declaration//GEN-END:variables
}
