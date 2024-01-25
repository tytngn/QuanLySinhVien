/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import view.DM_TTSV;

/**
 *
 * @author nguyenthituyetngan
 */
public class Program {
    
    public static Connection connection;
    
    public static void connectToDatabase() {
        // Thực hiện kết nối đến cơ sở dữ liệu
        try {
            String url = "jdbc:mysql://localhost:3306/QLSV";
            String username = "root";
            String password = "123Abc@@";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        DM_TTSV.getInstance();
    }
}
