/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author nguyenthituyetngan
 */
public class Document {

    // Trường hợp đối với mã số sinh viên chỉ chứa ký tự chữ cái và chữ số
    public static class MaSoDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (isValid(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (isValid(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        // Kiểm tra xem chuỗi có chứa chữ và số không
        private boolean isValid(String text) {
            return text.matches("[a-zA-Z0-9 ]*");
        }
    }

    // Trường hợp đối với Họ tên chỉ chứa các ký tự chữ cái
    public static class VNDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            if (isValid(text)) {
                super.insertString(fb, offset, text, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValid(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
//            int docLength = fb.getDocument().getLength();
//            String currentText = fb.getDocument().getText(0, docLength);
//            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
//
//            if (isValid(newText)) {
//                super.replace(fb, offset, length, text, attrs);
//            } else {
//                // Xoá dữ liệu đã thay thế
//                fb.remove(offset, length);
//            }
        }

        private boolean isValid(String text) {
            // Kiểm tra xem văn bản chỉ chứa chữ cái tiếng Việt, khoảng trắng và không có ký tự đặc biệt hoặc chữ số
            //return text.matches("[\\p{L} \\s]+");
            return text.matches("[\\p{L} \\s]*");
        }
    }
    
    // Trường hợp chấp nhận chuỗi đầu vào là số thực
    public static class RealNumberFilter extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        // Chỉ chấp nhận các ký tự số và dấu chấm
        if (isValid(text)) {
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        // Chỉ chấp nhận các ký tự số và dấu chấm
        if (isValid(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValid(String text) {
        // Kiểm tra xem chuỗi có chỉ chứa số và dấu chấm không
        return text.matches("\\d*\\.?\\d*");
    }
}

    
}

