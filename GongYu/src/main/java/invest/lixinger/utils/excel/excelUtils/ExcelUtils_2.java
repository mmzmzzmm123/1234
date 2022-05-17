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

public class ExcelUtils_2 {
    public ExcelUtils_2() {
    }
    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";

    public static void main(String[] args) throws IOException {
        ExcelUtils_2 eu = new ExcelUtils_2();
        File file = new File("src/main/java/work/cib/excel/resources/cangwei.xls");
        System.out.println(file.getPath());
        System.out.println("file.length()=" + file.length());
        MultipartFile mulFile = eu.fileToMultipartFile(file);
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

    /**
     * * 读取excel文件
     * 会把所有的sheet都读出来
     */
    public static List<String[]> readExcelFile(MultipartFile excelFile, int startRow) throws IOException {
        // 检查文件
        checkFile(excelFile);
        // 获得工作簿对象
        Workbook workbook = getWorkBook(excelFile);
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
                        cells[i] = getCellValue(cell);
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
        if (extension.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook();
        } else if (extension.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook();
        }

        if (workbook != null) {
            // 2. 创建sheet
            Sheet sheet = workbook.createSheet("sheet1");
            // 3. 创建row: 添加属性行
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

    /**
     * 获取当前列数据
     */
    private static String getCellValue(Cell cell) {
        Object cellValue = "";
        if (cell == null) {
            return cellValue.toString();
        }

        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }

        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue.toString();
    }

    /**
     * 获得工作簿对象
     *
     * @param excelFile excel文件
     */
    public static Workbook getWorkBook(MultipartFile excelFile) {
        // 获得文件名
        String fileName = excelFile.getOriginalFilename();
        // 创建Workbook工作簿对象，表示整个excel
        Workbook workbook = null;
        try {
            // 获得excel文件的io流
            InputStream is = excelFile.getInputStream();
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 检查文件
     *
     * @param excelFile excel文件
     */
    public static void checkFile(MultipartFile excelFile) throws IOException {
        //判断文件是否存在
        if (null == excelFile) {
            throw new FileNotFoundException("文件不存在");
        }
        //获得文件名
        String fileName = excelFile.getOriginalFilename();
        System.out.println("fileName=====" + fileName);
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    private File multipartFileToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
