package ru.stepchenkov.rest.util;

import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;

public class ExcelReader extends JFrame {
    public void showWindow() {

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);

        try {
            FileInputStream file = new FileInputStream("src/main/resources/data/data.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    textArea.append(cell.toString() + "\t");
                }
                textArea.append("\n");
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}