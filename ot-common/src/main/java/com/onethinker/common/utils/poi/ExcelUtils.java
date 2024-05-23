package com.onethinker.common.utils.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static <T> List<T> readExcel(File file, Class<T> clazz) throws IOException {
        List<T> list = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int numColumns = headerRow.getPhysicalNumberOfCells();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                T obj = convertRowToObject(row, clazz, numColumns);
                list.add(obj);
            }
        }
        return list;
    }

    private static <T> T convertRowToObject(Row row, Class<T> clazz, int numColumns) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            for (int j = 0; j < numColumns; j++) {
                Cell cell = row.getCell(j);
                String fieldName = row.getSheet().getRow(0).getCell(j).getStringCellValue();
                fieldName = convertHeaderToFieldName(fieldName);
                setFieldValue(obj, fieldName, getCellValue(cell));
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertHeaderToFieldName(String header) {
        // 这里可以添加逻辑将Excel表头转换为对象的字段名称
        return header;
    }

    private static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
