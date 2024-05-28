/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Document;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author nguyenthituyetngan
 */
public class DM_TTSV extends javax.swing.JFrame {

    private static DM_TTSV instance;

    private DrawerController drawer;

    private boolean stt = true;
    private Object selected = new Object();

    public static synchronized DM_TTSV getInstance() {
        if (instance == null) {
            instance = new DM_TTSV();
        }
        instance.setVisible(true);
        return instance;
    }

    private static void closeThisUI() {
        instance.drawer.hide();
        instance.dispose();
    }

    public DM_TTSV() {
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
                        if (i == 1) {
                            closeThisUI();
                            DM_LopHoc.getInstance();
                        }
                        if (i == 2) {
                            closeThisUI();
                            DM_MonHoc.getInstance();
                        }
                        if (i == 3) {
                            closeThisUI();
                            BangDiem.getInstance();
                        }
                        if (i == 4) {
                            System.exit(0);
                        }

                    }

                })
                .build();

        Program.connectToDatabase();
        reload();
    }

    public void reload() {
        try {

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("select * from SINHVIEN");

            DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
            m.setRowCount(0);
            while (rs.next()) {
                Object[] obj = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                m.addRow(obj);
            }
            reloadMaLop();
            setValueTF();
            s.close();
        } catch (Exception e) {
        }

    }

    private void setTF() {
        MSSVTextField.setText("");
        HoTenTextField.setText("");
        MLopComboBox.setSelectedItem("- Mã lớp -");
    }

    private void reloadMaLop() {
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT MaLop FROM QLSV.DM_Lop;");

            DefaultComboBoxModel<String> CBModel = (DefaultComboBoxModel<String>) MLopComboBox.getModel();

            while (rs.next()) {
                CBModel.addElement(rs.getString(1));
            }
            CBModel.setSelectedItem("- Mã lớp -");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValueTF() {
        ((AbstractDocument) MSSVTextField.getDocument()).setDocumentFilter(new Document.MaSoDocumentFilter());
        ((AbstractDocument) HoTenTextField.getDocument()).setDocumentFilter(new Document.VNDocumentFilter());
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
        HoTenLabel = new javax.swing.JLabel();
        NamSinhLabel = new javax.swing.JLabel();
        MaLopLabel = new javax.swing.JLabel();
        MSSVTextField = new javax.swing.JTextField();
        HoTenTextField = new javax.swing.JTextField();
        ThemButton = new javax.swing.JButton();
        SuaButton = new javax.swing.JButton();
        XoaButton = new javax.swing.JButton();
        MLopComboBox = new javax.swing.JComboBox<>();
        NamSinhComboBox = new javax.swing.JComboBox<>();
        RightPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        setBackground(new java.awt.Color(236, 219, 186));
        setMinimumSize(new java.awt.Dimension(1136, 650));
        setResizable(false);
        setSize(new java.awt.Dimension(1136, 547));

        HeaderPanel.setBackground(new java.awt.Color(247, 104, 2));

        TitleLabel.setBackground(new java.awt.Color(247, 104, 2));
        TitleLabel.setFont(new java.awt.Font("Lao MN", 1, 36)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("THÔNG TIN SINH VIÊN");

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

        UnderPanel.setBackground(new java.awt.Color(249, 247, 201));

        LeftPanel.setBackground(new java.awt.Color(249, 247, 201));

        MSSVLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MSSVLabel.setText("Mã số sinh viên");

        HoTenLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        HoTenLabel.setText("Họ và tên");

        NamSinhLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        NamSinhLabel.setText("Năm sinh");

        MaLopLabel.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        MaLopLabel.setText("Mã lớp");

        MSSVTextField.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        HoTenTextField.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        ThemButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        ThemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-add-file-20.png"))); // NOI18N
        ThemButton.setText("Thêm");
        ThemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemButtonActionPerformed(evt);
            }
        });

        SuaButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        SuaButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-edit-text-file-20.png"))); // NOI18N
        SuaButton.setText("Sửa");
        SuaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaButtonActionPerformed(evt);
            }
        });

        XoaButton.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        XoaButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asserts/icons8-trash-20.png"))); // NOI18N
        XoaButton.setText("Xoá");
        XoaButton.setName(""); // NOI18N
        XoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaButtonActionPerformed(evt);
            }
        });

        MLopComboBox.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        for (int year = 1950; year <= 2005; year++) {
            NamSinhComboBox.setSelectedItem(null);
            NamSinhComboBox.addItem(String.valueOf(year));
        }
        NamSinhComboBox.setFont(new java.awt.Font("Helvetica", 0, 16)); // NOI18N

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(NamSinhLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(HoTenLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MSSVLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MaLopLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ThemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addComponent(SuaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(HoTenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamSinhComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MLopComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MSSVTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HoTenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NamSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamSinhComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MaLopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MLopComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        RightPanel.setBackground(new java.awt.Color(249, 247, 201));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Helvetica", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã số sinh viên", "Họ và tên", "Năm sinh", "Mã lớp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void ThemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemButtonActionPerformed
        if (MSSVTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (HoTenTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (NamSinhComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
            return;
        }
//        String NamSinh = NamSinhTextField.getText();
//        try {
//            int year = Integer.parseInt(NamSinh);
//            if (year < 1900 || year > 2024) {
//                JOptionPane.showMessageDialog(this, "Dữ liệu năm sinh không hợp lệ!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (MLopComboBox.getSelectedItem().toString().equals("- Mã lớp -")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Statement s = connection.createStatement();
            String mssv = MSSVTextField.getText();
            String ht = HoTenTextField.getText();
            String ns = NamSinhComboBox.getSelectedItem().toString();
            String ml = MLopComboBox.getSelectedItem().toString();
            s.executeUpdate("INSERT INTO `QLSV`.`SINHVIEN` (`MaSV`, `TenSV`, `NamSinh`, `MaLop`) VALUES ('" + mssv
                    + "', '" + ht + "', '" + ns + "', '" + ml + "');");
            s.close();
            reload();
            setTF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi!", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_ThemButtonActionPerformed

    private void SuaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaButtonActionPerformed
        if (stt == true) {
            int r = jTable1.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                Statement s = connection.createStatement();

                Object mssv = jTable1.getModel().getValueAt(r, 0);
                Object hten = jTable1.getModel().getValueAt(r, 1);
                Object ns = jTable1.getModel().getValueAt(r, 2);
                ResultSet rs = s.executeQuery("SELECT MaLop FROM QLSV.SINHVIEN where MaSV = N'" + mssv + "';");
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Lỗi! Không chọn được mã lớp.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Object lop = rs.getString("MaLop");

                MSSVTextField.setText((String) mssv);
                HoTenTextField.setText((String) hten);
                NamSinhComboBox.setSelectedItem((String) ns);
                MLopComboBox.setSelectedItem(lop);
                selected = mssv;
                stt = false;
                jTable1.setEnabled(false);
                ThemButton.setEnabled(false);
                XoaButton.setEnabled(false);
                SuaButton.setText("Cập nhật");
                SuaButton.setSize(140, 40);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (stt == false) {
            if (MSSVTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (HoTenTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (NamSinhComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
                return;
            }
//            String NamSinh = NamSinhTextField.getText();
//            try {
//                int year = Integer.parseInt(NamSinh);
//                if (year <= 1900 || year >= 2024) {
//                    JOptionPane.showMessageDialog(this, "Dữ liệu năm sinh không hợp lệ!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            if (MLopComboBox.getSelectedItem().toString().equals("- Mã lớp -")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Statement s = connection.createStatement();
                String mssv = MSSVTextField.getText();
                String ht = HoTenTextField.getText();
                String ns = NamSinhComboBox.getSelectedItem().toString();
                String ml = MLopComboBox.getSelectedItem().toString();
                s.executeUpdate("UPDATE `QLSV`.`SINHVIEN` SET `MaSV` = '" + mssv
                        + "', `TenSV` = '" + ht + "', `NamSinh` = '" + ns
                        + "', `MaLop` = '" + ml + "' WHERE (`MaSV` = '" + selected + "');");
                s.close();
                stt = true;
                jTable1.setEnabled(true);
                ThemButton.setEnabled(true);
                XoaButton.setEnabled(true);
                SuaButton.setText("Sửa");
                reload();
                setTF();
                return;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi!", JOptionPane.ERROR_MESSAGE);

            }
        }
    }//GEN-LAST:event_SuaButtonActionPerformed

    private void XoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaButtonActionPerformed
        try {
            Statement s = connection.createStatement();
            int r = jTable1.getSelectedRow();
            Object mssv = jTable1.getModel().getValueAt(r, 0);

            int x = s.executeUpdate("DELETE FROM `QLSV`.`SINHVIEN` WHERE (`MaSV` = '" + mssv + "');");
            if (x == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            s.close();
            reload();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_XoaButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int e = evt.getClickCount();
        // Khi nháy đúp chuột
        if (e == 2) {
            int r = jTable1.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Object mssv = jTable1.getModel().getValueAt(r, 0);
            NhapDiem nhapDiemDialog = new NhapDiem(this, rootPaneCheckingEnabled);
            nhapDiemDialog.loadDatafromSV(mssv);
            nhapDiemDialog.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HoTenLabel;
    private javax.swing.JTextField HoTenTextField;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JComboBox<String> MLopComboBox;
    private javax.swing.JLabel MSSVLabel;
    private javax.swing.JTextField MSSVTextField;
    private javax.swing.JLabel MaLopLabel;
    private javax.swing.JLabel MenuLabel;
    private javax.swing.JPanel MiddelPanel1;
    private javax.swing.JComboBox<String> NamSinhComboBox;
    private javax.swing.JLabel NamSinhLabel;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JButton SuaButton;
    private javax.swing.JButton ThemButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel UnderPanel;
    private javax.swing.JButton XoaButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
