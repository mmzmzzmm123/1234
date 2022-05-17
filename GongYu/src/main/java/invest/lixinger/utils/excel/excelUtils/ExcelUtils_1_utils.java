package invest.lixinger.utils.excel.excelUtils;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtils_1_utils {
    //创建一个m行n列的cell列表
    public static List<List<Cell>>  createMNCellList(int m,int n,String ext){
        Workbook wb=null;
        if ("xls".equals(ext)) {
            wb = new HSSFWorkbook();
            wb.createSheet("Sheet1");
        } else if ("xlsx".equals(ext)) {
            wb = new XSSFWorkbook();
            wb.createSheet("Sheet1");
        }

        List<List<Cell>> getZiChanXiFenExcelList = new ArrayList<>();
        Sheet sheet=wb.getSheetAt(0);
        for (int i = 0; i < m; i++) {
            getZiChanXiFenExcelList.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                Row row = sheet.createRow(j);
                Cell cell=row.createCell(j);
                getZiChanXiFenExcelList.get(i).add(cell);
            }
        }
        return getZiChanXiFenExcelList;
    }
    //从一个excel中得到m行n列的表格
    public static List<List<Cell>> getDefineRC(List<List<Cell>>list,int rowLen,int columnLen){
        List<List<Cell>> getZiChanXiFen = new ArrayList<>();
        for (int i = 0; i < rowLen; i++) {
            List<Cell> row = list.get(i);
            list.add(row);
            getZiChanXiFen.add(new ArrayList<>());
            ExcelUtils_1_utils.out("第" + (i + 1) + "行：", true, false);
            for (int j = 0; j < columnLen; j++) {
                Object value = ExcelUtils_1_utils.getCellValueObj(row.get(j));
                getZiChanXiFen.get(i).add(row.get(j));
                ExcelUtils_1_utils.out(value + " | ", true, false);
            }
            System.out.println();
        }
        return getZiChanXiFen;
    }
    public static Workbook getWorkbook(File file, String ext) throws IOException {

        Workbook wb = null;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            if ("xls".equals(ext)) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(ext)) {
                wb = new XSSFWorkbook(fis);
            }
        } else {
            System.out.println("路径不存在，新创建了一个表格，表名为sheet1");
            if ("xls".equals(ext)) {
                wb = new HSSFWorkbook();
                wb.createSheet("Sheet1");
            } else if ("xlsx".equals(ext)) {
                wb = new XSSFWorkbook();
                wb.createSheet("Sheet1");
            }
        }

        return wb;
    }

    /**
     * 读取单元格的值
     */
    public static String getCellValueString(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    if (cell.getCachedFormulaResultType() == CellType.STRING) {
                        result = cell.getCellFormula();
                    } else {
                        result = cell.getNumericCellValue();  //Cannot get a NUMERIC value from a STRING formula cell
                    }
//                    System.out.println(cell.getCachedFormulaResultType());
//                    result=cell.getStringCellValue();  //Cannot get a STRING value from a NUMERIC formula cell
//                    result=cell.getNumericCellValue();  //Cannot get a NUMERIC value from a STRING formula cell
//                    result = cell.getCellFormula();
//                    System.out.println(result);
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    public static Object getCellValueObj(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
        }
        return result;
    }


    /**
     * 判断fromRowList、call_xlsPath、savePath是否存在
     */
    public static void judgeWritePathExist(List<List<Cell>> fromRowList, String toXlsPath, String savePath) throws IOException {
        // 判断列表是否有数据，如果没有数据，则返回
        if (fromRowList == null || fromRowList.size() == 0) {
            out("fromRowList,不能为空", true);
            throw new IOException("文件不能为空");
        }
        // 判断文件路径是否为空
        if (toXlsPath == null || toXlsPath.equals("")) {
            out("callXlsPath,文件路径不能为空", true);
            throw new IOException("文件路径不能为空");
        }
        // 判断文件路径是否为空
        if (savePath == null || savePath.equals("")) {
            out("savePath,不能为空", true);
            throw new IOException("文件路径不能为空");
        }
    }

    public static void judgeReadPathExist(String excelPath) throws IOException {
        if (excelPath.equals("")) {
            throw new IOException("文件路径不能为空！");
        } else {
            File file = new File(excelPath);
            if (!file.exists()) {
                throw new IOException("文件不存在！");
            }
        }
    }


    /**
     * 打印消息
     */
    public static void out(String msg, boolean isPrintMsg) {
        if (isPrintMsg) {
            out(msg, true, true);
        }
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     * @param tr  换行
     */
    public static void out(String msg, boolean isPrintMsg, boolean tr) {
        if (isPrintMsg) {
            System.out.print(msg + (tr ? "\n" : ""));
        }
    }

    @Test
    public void testEvaluator() throws Exception {
        String path = "E:\\GongYu\\src\\main\\java\\work\\cib\\excel\\resources\\cangwei.xls";
        FileInputStream fileInputStream = new FileInputStream(path);
        //获取工作簿，这里使用的07版
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        //获取工作表
        Sheet sheet = workbook.getSheetAt(0);
        //获取公式所在单元格
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(4);
        //后面使用它来执行计算公式
        FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        //获取单元格内容的类型
        CellType cellType = cell.getCellType();
        //判断是否存储的为公式，此处本可以不加判断
        if (cellType.equals(CellType.FORMULA)) {
            //获取公式，可以理解为已String类型获取cell的值输出
            String cellFormula = cell.getCellFormula();
            System.out.println(cellFormula);
            //执行公式，此处cell的值就是公式
            CellValue evaluate = formulaEvaluator.evaluate(cell);
            System.out.println(evaluate.formatAsString());
        }
    }
    /**
     * 复制一个单元格样式到目的单元格样式
     */
    public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        // 边框和边框颜色
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());

        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        // 背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        // 数据格式
        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
//         toStyle.setFont(fromStyle.getFontIndex(null));
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());  // 首行缩进
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());  // 旋转
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());

    }
}
