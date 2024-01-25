/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Program;
import static controller.Program.connection;
import java.awt.Color;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nguyenthituyetngan
 */
public class DM_LopHoc extends javax.swing.JFrame {

    private static DM_LopHoc instance;

    private DrawerController drawer;

    

    public static synchronized DM_LopHoc getInstance() {
        if (instance == null) {
            instance = new DM_LopHoc();
        }
        instance.setVisible(true);
        return instance;
    }

    private static void closeThisUI() {
        instance.drawer.hide();
        instance.dispose();
    }

    public DM_LopHoc() {
        initComponents();

        drawer = Drawer.newDrawer(this)
                .header(new Header())
                .separator(2, Color.BLACK)
                .drawerBackground(new Color(244, 242, 229))
                .enableScroll(true)
                .space(30)
                .addChild(new DrawerItem("THÔNG TIN SINH VIÊN").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addChild(new DrawerItem("DANH MỤC LỚP HỌC").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addChild(new DrawerItem("DANH MỤC MÔN HỌC").icon(new ImageIcon(getClass().getResource("/asserts/Folder-Desktop-icon.png"))).build())
                .addFooter(new DrawerItem("THOÁT").icon(new ImageIcon(getClass().getResource("/asserts/exit-icon.png"))).build())
                .event(new EventDrawer() {
                    @Override
                    public void selected(int i, DrawerItem di) {
                        //di.initMouseOver();
                        di.effectColor(new Color(20, 85, 65)).build();
                        if (i == 0) {
                            closeThisUI();
                            DM_TTSV.getInstance();
                        }
                        if (i == 2) {
                            closeThisUI();
                            DM_MonHoc.getInstance();
                        }
                        if (i == 3) {
                            System.exit(0);
                        }

                    }

                })
                .build();

        Program.connectToDatabase();
        reload();
    }

    private void reload() {
        try {

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("select * from DM_Lop");

            DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
            m.setRowCount(0);
            while (rs.next()) {
                Object[] obj = {rs.getString(1), rs.getString(2)};
                m.addRow(obj);
            }
            s.close();
        } catch (Exception e) {
        }

    }
    
    private void setTF() {
        MaLopTextField.setText("");
        TenLopTextField.setText("");
    }

//    public void connectToDatabase() {
//        // Thực hiện kết nối đến cơ sở dữ liệu
//        try {
//            String url = "jdbc:mysql://localhost:3306/QLSV";
//            String username = "root";
//            String password = "123Abc@@";
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
        MaLopTextField = new javax.swing.JTextField();
        TenLopTextField = new javax.swing.JTextField();
        ThemButton = new javax.swing.JButton();
        SuaButton = new javax.swing.JButton();
        XoaButton = new javax.swing.JButton();
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
        setSize(new java.awt.Dimension(1136, 547));

        HeaderPanel.setBackground(new java.awt.Color(247, 104, 2));

        TitleLabel.setBackground(new java.awt.Color(247, 104, 2));
        TitleLabel.setFont(new java.awt.Font("Lao MN", 1, 36)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("DANH MỤC LỚP HỌC");

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

        UnderPanel.setBackground(new java.awt.Color(244, 242, 229));

        LeftPanel.setBackground(new java.awt.Color(244, 242, 229));

        MSSVLabel.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        MSSVLabel.setText("Mã lớp");

        HoTenLabel.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        HoTenLabel.setText("Tên lớp");

        MaLopTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaLopTextFieldActionPerformed(evt);
            }
        });

        ThemButton.setFont(new java.awt.Font("Helvetica", 1, 16)); // NOI18N
        ThemButton.setText("Thêm");
        ThemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemButtonActionPerformed(evt);
            }
        });

        SuaButton.setFont(new java.awt.Font("Helvetica", 1, 16)); // NOI18N
        SuaButton.setText("Sửa");
        SuaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaButtonActionPerformed(evt);
            }
        });

        XoaButton.setFont(new java.awt.Font("Helvetica", 1, 16)); // NOI18N
        XoaButton.setText("Xoá");
        XoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(HoTenLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(MSSVLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TenLopTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(MaLopTextField)))
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addComponent(ThemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(SuaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MSSVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaLopTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HoTenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TenLopTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(248, 248, 248)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        RightPanel.setBackground(new java.awt.Color(244, 242, 229));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã lớp", "Tên lớp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
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
        if (MaLopTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Không có dữ liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (TenLopTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu!", "Không có dữ liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Statement s = connection.createStatement();
            String m = MaLopTextField.getText();
            String t = TenLopTextField.getText();
            s.executeUpdate("INSERT INTO QLSV.DM_Lop (MaLop, TenLop) VALUES ('" + m + "','" + t + "');");
            s.close();
            reload();
            setTF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi!", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_ThemButtonActionPerformed

    private void SuaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaButtonActionPerformed
        try {
            Statement s = connection.createStatement();
            int r = jTable1.getSelectedRow();
            Object m = jTable1.getModel().getValueAt(r, 0);

            int x = s.executeUpdate("UPDATE `QLSV`.`DM_Lop` SET `MaLop` = 'B01' WHERE (`MaLop` = 'hdhs');");
            if (x == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            s.close();
            reload();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_SuaButtonActionPerformed

    private void XoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaButtonActionPerformed
        try {
            Statement s = connection.createStatement();
            int r = jTable1.getSelectedRow();
            Object m = jTable1.getModel().getValueAt(r, 0);

            int x = s.executeUpdate("DELETE FROM `QLSV`.`DM_Lop` WHERE (`MaLop` = '" + m + "');");
            if (x == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            s.close();
            reload();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dữ liệu!", "Lỗi!", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_XoaButtonActionPerformed

    private void MaLopTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaLopTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaLopTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HoTenLabel;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JLabel MSSVLabel;
    private javax.swing.JTextField MaLopTextField;
    private javax.swing.JLabel MenuLabel;
    private javax.swing.JPanel MiddelPanel1;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JButton SuaButton;
    private javax.swing.JTextField TenLopTextField;
    private javax.swing.JButton ThemButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel UnderPanel;
    private javax.swing.JButton XoaButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
