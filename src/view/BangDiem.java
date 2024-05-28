/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Program;
import static controller.Program.connection;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.Statement;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nguyenthituyetngan
 */
public class BangDiem extends javax.swing.JFrame {

    private static BangDiem instance;

    private DrawerController drawer;

    private boolean stt = true;
    private Object selected = new Object();

    public static synchronized BangDiem getInstance() {
        if (instance == null) {
            instance = new BangDiem();
        }
        instance.setVisible(true);
        return instance;
    }

    private static void closeThisUI() {
        instance.drawer.hide();
        instance.dispose();
    }

    public BangDiem() {
        initComponents();

        drawer = Drawer.newDrawer(this)
                .header(new Header())
                .separator(2, Color.BLACK)
                .drawerBackground(new Color(255, 255, 255))
                .enableScroll(true)
                .space(30)
                .addChild(new DrawerItem("THÔNG TIN SINH VIÊN").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addChild(new DrawerItem("DANH MỤC LỚP HỌC").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addChild(new DrawerItem("DANH MỤC MÔN HỌC").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addChild(new DrawerItem("BẢNG ĐIỂM HỌC KỲ").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addFooter(new DrawerItem("THOÁT").icon(new ImageIcon(getClass().getResource("/asserts/exit-icon.png"))).build())
                .event(new EventDrawer() {
                    @Override
                    public void selected(int i, DrawerItem di) {
                        //di.initMouseOver();
                        di.effectColor(new Color(128, 188, 189)).build();
                        if (i == 0) {
                            closeThisUI();
                            DM_TTSV.getInstance();
                        }
                        if (i == 1) {
                            closeThisUI();
                            DM_LopHoc.getInstance();
                        }
                        if (i == 2) {
                            closeThisUI();
                            DM_MonHoc.getInstance();
                        }
                        if (i == 4) {
                            System.exit(0);
                        }

                    }

                })
                .build();

        Program.connectToDatabase();
        reloadNamHoc();
    }

    //reload Điểm theo MaSV - Năm học - Học kỳ
    private void reloadDiem() {
        try {
            Statement s = connection.createStatement();

            String MaSV = MSSVTextField.getText().toString();
            String NamHoc = NamHocComboBox.getSelectedItem().toString();
            String HocKy = HocKyComboBox.getSelectedItem().toString();

            DefaultTableModel m = (DefaultTableModel) BangDiemTable.getModel();
            m.setRowCount(0);
            ResultSet rs = s.executeQuery("SELECT TenSV, NamSinh from SINHVIEN WHERE MaSV= '" + MaSV + "';");
            if (rs.next()) {
                HoTenTextField.setText(rs.getString(1));
                NamSinhTextField.setText(rs.getString(2));
            } else {
                JOptionPane.showMessageDialog(this, "Mã sinh viên không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                m.setRowCount(0);
                HoTenTextField.setText("");
                TBTextField.setText("");
                XepLoaiTextField.setText("");
            }
            rs = s.executeQuery("SELECT MaMH, Diem1, Diem2, DiemTB, XepLoai \n"
                    + "FROM BANGDIEM \n"
                    + "WHERE MaSV = '" + MaSV + "' \n"
                    + "  AND NamHoc = '" + NamHoc + "' \n"
                    + "  AND HocKy = '" + HocKy + "';");

            if (rs.next()) {
                float avg = 0;
                do {
                    Object[] obj = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
                    m.addRow(obj);
                    avg += rs.getFloat(4);
                } while (rs.next());
                avg = avg / m.getRowCount();
                TBTextField.setText(String.valueOf(avg));
                XepLoaiTextField.setText(xepLoai(avg));
            } else {
                m.setRowCount(0);
                TBTextField.setText("");
                XepLoaiTextField.setText("");

            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String xepLoai(float diemTB) {
        if (diemTB >= 8.0) {
            return "Giỏi";
        } else if (diemTB >= 6.5) {
            return "Khá";
        } else if (diemTB >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
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

    // Đặt lại giá trị các trường dữ liệu là rỗng
    private void setTF() {
        MSSVTextField.setText("");
        NamHocComboBox.setSelectedIndex(-1);
        HocKyComboBox.setSelectedIndex(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MiddelPanel1 = new javax.swing.JPanel();
        HeaderPanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        MenuLabel = new javax.swing.JLabel();
        UnderPanel = new javax.swing.JPanel();
        LeftPanel = new javax.swing.JPanel();
        MSSVLabel = new javax.swing.JLabel();
        MSSVTextField = new javax.swing.JTextField();
        NamHocLabel = new javax.swing.JLabel();
        NamHocComboBox = new javax.swing.JComboBox<>();
        HocKyLabel = new javax.swing.JLabel();
        HocKyComboBox = new javax.swing.JComboBox<>();
        LietKeBtn = new javax.swing.JButton();
        HoTenLabel = new javax.swing.JLabel();
        HoTenTextField = new javax.swing.JTextField();
        NamSinhLabel = new javax.swing.JLabel();
        NamSinhTextField = new javax.swing.JTextField();
        RightPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BangDiemTable = new javax.swing.JTable();
        XepLoaiLabel = new javax.swing.JLabel();
        TBTextField = new javax.swing.JTextField();
        TBLabel = new javax.swing.JLabel();
        XepLoaiTextField = new javax.swing.JTextField();

        MiddelPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout MiddelPanel1Layout = new javax.swing.GroupLayout(MiddelPanel1);
        MiddelPanel1.setLayout(MiddelPanel1Layout);
        MiddelPanel1Layout.setHorizontalGroup(
            MiddelPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );
        MiddelPanel1Layout.setVerticalGroup(
            MiddelPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1136, 650));
        setResizable(false);
        setSize(new java.awt.Dimension(1136, 547));

        HeaderPanel.setBackground(new java.awt.Color(247, 104, 2));

        TitleLabel.setBackground(new java.awt.Color(247, 104, 2));
        TitleLabel.setFont(new java.awt.Font("Lao MN", 1, 36)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("BẢNG ĐIỂM HỌC KỲ");

        MenuLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MenuLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/menu-icon.png"))); // NOI18N
        MenuLabel.setSize(new java.awt.Dimension(50, 50));
        MenuLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        UnderPanel.setBackground(new java.awt.Color(255, 255, 255));

        LeftPanel.setBackground(new java.awt.Color(249, 247, 201));

        MSSVLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MSSVLabel.setText("Mã số sinh viên");

        MSSVTextField.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        NamHocLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        NamHocLabel.setText("Năm học");

        NamHocComboBox.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        HocKyLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        HocKyLabel.setText("Học kỳ");

        HocKyComboBox.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        HocKyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        HocKyComboBox.setSelectedIndex(-1);

        LietKeBtn.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        LietKeBtn.setText("Liệt kê");
        LietKeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LietKeBtnActionPerformed(evt);
            }
        });

        HoTenLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        HoTenLabel.setText("Họ và tên");

        HoTenTextField.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        HoTenTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        HoTenTextField.setEnabled(false);

        NamSinhLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        NamSinhLabel.setText("Năm sinh");

        NamSinhTextField.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        NamSinhTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NamSinhTextField.setEnabled(false);

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NamHocLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HocKyLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NamSinhLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NamHocComboBox, 0, 255, Short.MAX_VALUE)
                    .addComponent(HocKyComboBox, 0, 255, Short.MAX_VALUE)
                    .addComponent(HoTenTextField)
                    .addComponent(NamSinhTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftPanelLayout.createSequentialGroup()
                .addContainerGap(172, Short.MAX_VALUE)
                .addComponent(LietKeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HoTenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NamSinhTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NamHocComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamHocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HocKyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HocKyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(LietKeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        RightPanel.setBackground(new java.awt.Color(249, 247, 201));

        BangDiemTable.setAutoCreateRowSorter(true);
        BangDiemTable.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N
        BangDiemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Môn học", "Điểm lần 1", "Điểm lần 2", "Điểm trung bình", "Xếp loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BangDiemTable.setGridColor(new java.awt.Color(0, 0, 0));
        BangDiemTable.setRowHeight(35);
        BangDiemTable.setShowGrid(true);
        jScrollPane1.setViewportView(BangDiemTable);

        XepLoaiLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        XepLoaiLabel.setText("Xếp loại");

        TBTextField.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        TBTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TBTextField.setEnabled(false);

        TBLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        TBLabel.setText("Trung bình học kỳ");

        XepLoaiTextField.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        XepLoaiTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        XepLoaiTextField.setEnabled(false);

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGroup(RightPanelLayout.createSequentialGroup()
                        .addComponent(TBLabel)
                        .addGap(30, 30, 30)
                        .addComponent(TBTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(XepLoaiLabel)
                        .addGap(30, 30, 30)
                        .addComponent(XepLoaiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(XepLoaiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XepLoaiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout UnderPanelLayout = new javax.swing.GroupLayout(UnderPanel);
        UnderPanel.setLayout(UnderPanelLayout);
        UnderPanelLayout.setHorizontalGroup(
            UnderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UnderPanelLayout.createSequentialGroup()
                .addComponent(LeftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UnderPanelLayout.setVerticalGroup(
            UnderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(LeftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(UnderPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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


    private void MenuLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuLabelMouseClicked
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_MenuLabelMouseClicked

    private void LietKeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LietKeBtnActionPerformed
        reloadDiem();
    }//GEN-LAST:event_LietKeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangDiemTable;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HoTenLabel;
    private javax.swing.JTextField HoTenTextField;
    private javax.swing.JComboBox<String> HocKyComboBox;
    private javax.swing.JLabel HocKyLabel;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JButton LietKeBtn;
    private javax.swing.JLabel MSSVLabel;
    private javax.swing.JTextField MSSVTextField;
    private javax.swing.JLabel MenuLabel;
    private javax.swing.JPanel MiddelPanel1;
    private javax.swing.JComboBox<String> NamHocComboBox;
    private javax.swing.JLabel NamHocLabel;
    private javax.swing.JLabel NamSinhLabel;
    private javax.swing.JTextField NamSinhTextField;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JLabel TBLabel;
    private javax.swing.JTextField TBTextField;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel UnderPanel;
    private javax.swing.JLabel XepLoaiLabel;
    private javax.swing.JTextField XepLoaiTextField;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
