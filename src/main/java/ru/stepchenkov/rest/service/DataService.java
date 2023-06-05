package ru.stepchenkov.rest.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
public class DataService {

    public void readFile() throws IOException {
        // указываем имя файла Excel, который нужно обработать
        String fileName = "src/main/resources/data/data.xlsx";

        // открываем файл Excel
        FileInputStream excelFile = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(excelFile);

        // выбираем лист, который нужно обработать
        Sheet sheet = workbook.getSheet("l1");

        // создаем объект StringBuilder для генерации HTML-кода
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<html><head><meta charset=\"UTF-8\"><title>Table</title></head><body>");
        htmlStringBuilder.append("<table border=1>");

        // проходим по всем строкам и столбцам таблицы
        for (Row row : sheet) {
            htmlStringBuilder.append("<tr>");
            for (Cell cell : row) {
                htmlStringBuilder.append("<td>");
                htmlStringBuilder.append(cell.getStringCellValue());
                htmlStringBuilder.append("</td>");
            }
            htmlStringBuilder.append("</tr>");
        }

        // закрываем теги HTML
        htmlStringBuilder.append("</table></body></html>");

        // сохраняем сгенерированный HTML-код в файл
        File htmlFile = new File("src/main/resources/static/table.html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile, StandardCharsets.UTF_8));
        writer.write(htmlStringBuilder.toString());
        writer.close();
        // закрываем файл Excel
        excelFile.close();
    }

    public void addNewRow() {
        String fileName = "src/main/resources/data/data.xlsx";

        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // определяем номер последней строки в таблице
            int lastRowNum = sheet.getLastRowNum();

            // создаем новую строку
            Row row = sheet.createRow(lastRowNum + 1);

            // заполняем строку данными
            row.createCell(0).setCellValue("Андрей");
            row.createCell(1).setCellValue("Степченков");
            row.createCell(2).setCellValue("143");
            row.createCell(3).setCellValue("Рабочий");
            row.createCell(4).setCellValue("1");
            row.createCell(5).setCellValue(LocalDate.now().toString());
            row.createCell(6).setCellValue("16:58");
            row.createCell(7).setCellValue("16:59");

            // сохраняем изменения в файл
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

