package invest.lixinger.utils.excel.excelUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static invest.lixinger.utils.excel.excelUtils.ExcelUtils_utils_MultipartFile.checkFile;

public class ExcelUtils_2 {

    public static void main(String[] args) throws IOException {
        ExcelUtils_2 eu = new ExcelUtils_2();
        File file = new File("GongYu/src/main/resources/excel/importExcel/ExcelUtils_1_1.xls");
        System.out.println(file.getPath());
        System.out.println("file.length()=" + file.length());
        MultipartFile mulFile = ExcelUtils_utils_MultipartFile.fileToMultipartFile(file);
        List<String[]> list = readExcelFile(mulFile, 0);
        int i = 0;
        for (String[] strs : list) {
            System.out.print("第" + (i++ + 1) + "行：");
            for (String str : strs) {
                System.out.print(str + "|");
            }
            System.out.println();
        }
    }

    public static List<String[]> readExcelFile(MultipartFile excelFile, int startRow) throws IOException {
        // 检查文件
        checkFile(excelFile);
        // 获得工作簿对象
        Workbook workbook = ExcelUtils_utils_MultipartFile.getWorkBook(excelFile);
        // 创建返回对象，把每行中的值作为一个数组，所有的行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (workbook != null) {
            //会把所有sheet读出来
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                // 获取当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                // 获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                if (startRow < 0 || startRow > lastRowNum) {
                    throw new RuntimeException("wrong startRow");
                }
                for (int rowNum = startRow; rowNum <= lastRowNum; rowNum++) {
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    // 获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    // 获得当前行的列数
                    int cellNums = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    // 循环当前行
                    for (int i = firstCellNum; i < cellNums; i++) {
                        Cell cell = row.getCell(i);
                        cells[i] = ExcelUtils_utils.getCellValueString(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }


    public static Workbook createExcelFile(List<String> attributes, List<List<String>> data, String extension) {
        // 1. 创建workbook
        Workbook workbook = null;
        if (StringUtils.isBlank(extension)) {
            return null;
        }
        if (extension.equalsIgnoreCase("xls")) {
            workbook = new HSSFWorkbook();
        } else if (extension.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook();
        }

        if (workbook != null) {
            // 2. 创建sheet
            Sheet sheet = workbook.createSheet("sheet1");
            // 3. 创建row: 添加表头
            Row row0 = sheet.createRow(0);
            for (int i = 0; i < attributes.size(); i++) {
                Cell cell = row0.createCell(i);
                cell.setCellValue(attributes.get(i).trim());
            }
            // 4. 插入数据 零时删除
            if (CollectionUtils.isNotEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    List<String> rowInfo = data.get(i);
                    Row row = sheet.createRow(i + 1);
                    // 添加数据
                    for (int j = 0; j < rowInfo.size(); j++) {
                        row.createCell(j).setCellValue(rowInfo.get(j));
                    }
                }
            }
        }
        return workbook;
    }

}
