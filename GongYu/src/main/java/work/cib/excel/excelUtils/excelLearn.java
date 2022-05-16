package work.cib.excel.excelUtils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//https://blog.csdn.net/qq_35029061/article/details/84361102?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164553776116780271564096%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=164553776116780271564096&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-3-84361102.first_rank_v2_pc_rank_v29&utm_term=excel+poi&spm=1018.2226.3001.4187
public class excelLearn {
    //设置数据类型
    @Test
    public void test1() throws Exception {
        Workbook wb = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页
        Row row = sheet.createRow(0); // 创建一个行
        Cell cell = row.createCell(0); // 创建一个单元格  第1列
        cell.setCellValue(new Date());  // 给单元格设置值

        row.createCell(1).setCellValue(1);
        row.createCell(2).setCellValue("一个字符串");
        row.createCell(3).setCellValue(true);
        row.createCell(4).setCellValue(String.valueOf(CellType.NUMERIC));  //自己进行过更改
        row.createCell(5).setCellValue(false);

        FileOutputStream fileOut = new FileOutputStream("src/main/java/work/cib/excel/resources/ExcelUtils_2.xls");
        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //设置时间
    @Test
    public void test2() throws Exception {
        Workbook wb = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页
        Row row = sheet.createRow(0); // 创建一个行

        //---------------------------------
        Cell cell = row.createCell(0); // 创建一个单元格  第1列
        cell.setCellValue(new Date());  // 给单元格设置值

        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle(); //单元格样式类
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyy-mm-dd hh:mm:ss"));

        //---------------------------------
        cell = row.createCell(1); // 第二列
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        //---------------------------------
        cell = row.createCell(2);  // 第三列
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);
        FileOutputStream fileOut = new FileOutputStream("src/main/java/work/cib/excel/exportExcel/demo2.xls");
        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //合并单元格
    @Test
    public void test3() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new Date());
        //合并列------------------------------------------------------------
        cell.setCellValue("合并列");
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        //合并行------------------------------------------------------------
        HSSFCell cell1 = row.createCell(6);
        cell1.setCellValue("合并行");
        region = new CellRangeAddress(0, 5, 6, 6);
        sheet.addMergedRegion(region);

        //文档输出
        FileOutputStream out = new FileOutputStream("src/main/work/cib/excel/outputExcel" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //遍历工作薄的行和列
    @Test
    public void test4() throws Exception {
        InputStream is = new FileInputStream("src/main/work/cib/excel/resources/ExcelUtils_2.xls");
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet hssfSheet = wb.getSheetAt(0); // 获取第一个Sheet页
//        HSSFSheet sheet = wb.getSheet("sheet1");
        if (hssfSheet == null) {
            return;
        }
        // 遍历行Row
        for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            // 遍历列Cell
            for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
                HSSFCell hssfCell = hssfRow.getCell(cellNum);
                if (hssfCell == null) {
                    continue;
                }
                System.out.print(" " + getValue(hssfCell));
            }
            System.out.println();
        }
    }

    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    //文本提取
    @Test
    public void test5() throws Exception {
        InputStream is = new FileInputStream("src/main/work/cib/excel/resources/ExcelUtils_2.xls");
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        ExcelExtractor excelExtractor = new ExcelExtractor(wb);
        excelExtractor.setIncludeSheetNames(true);  // 我们不需要Sheet页的名字
        System.out.println(excelExtractor.getText());
    }

    //单元格对齐方式
    @Test
    public void test6() throws Exception {
        Workbook wb = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = wb.createSheet("一个Sheet页");  // 创建第一个Sheet页
        Row row = sheet.createRow(2); // 创建一个行
        row.setHeightInPoints(30);

        createCell(wb, row, 0, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
        createCell(wb, row, 1, HorizontalAlignment.FILL, VerticalAlignment.JUSTIFY);
        createCell(wb, row, 2, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
        createCell(wb, row, 3, HorizontalAlignment.RIGHT, VerticalAlignment.DISTRIBUTED);

        FileOutputStream fileOut = new FileOutputStream("src/main/work/cib/excel/resources/ExcelUtils_2.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    private static void createCell(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign) {
        Cell cell = row.createCell(column);  // 创建单元格
        cell.setCellValue(new HSSFRichTextString("Align It"));  // 设置值
        CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
        cellStyle.setAlignment(halign);  // 设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对其方式
        cell.setCellStyle(cellStyle); // 设置单元格样式
    }

    //边框
    @Test
    public void test7() throws Exception {
        Workbook wb = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页
        Row row = sheet.createRow(1); // 创建一个行
        Cell cell = row.createCell(1); // 创建一个单元格
        cell.setCellValue(4);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); // 底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色

        cellStyle.setBorderLeft(BorderStyle.HAIR);  // 左边边框
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex()); // 左边边框颜色

        cellStyle.setBorderRight(BorderStyle.DASHED); // 右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());  // 右边边框颜色

        cellStyle.setBorderTop(BorderStyle.DOUBLE); // 上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边边框颜色

        cell.setCellStyle(cellStyle);
        FileOutputStream fileOut = new FileOutputStream("src/main/work/cib/excel/resources/ExcelUtils_2.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    //Excel的单元格操作
    @Test
    public void test8() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //设置日期---------------------------------------------------------------------------
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new Date());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);
        //设置保留2位小数--使用Excel内嵌的格式------------------------------------------------
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(12.3456789);
        style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell1.setCellStyle(style);
        //设置货币格式--使用自定义的格式-------------------------------------------------------
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(12345.6789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,###"));
        cell2.setCellStyle(style);
        //设置百分比格式--使用自定义的格式-----------------------------------------------------
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(0.123456789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell3.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式---------------------------------------------------
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(12345.56);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));  //只能取整
        cell4.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式-------------------------------------------------
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell5.setCellStyle(style);

        //文档输出
        FileOutputStream out = new FileOutputStream("src/main/java/work/cib/excel/exportExcel/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建文档摘要信息
    @Test
    public void test9() throws IOException {
        //创建HSSFWorkbook工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格,从0开始
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("a");
        //一下为简写
        row.createCell(1).setCellValue("aa");
        row.createCell(2).setCellValue(new Date());
        row.createCell(3).setCellValue("aaaa");

        //创建文档信息
        workbook.createInformationProperties();
        //获取DocumentSummaryInformation对象
        DocumentSummaryInformation documentSummaryInformation = workbook.getDocumentSummaryInformation();
        documentSummaryInformation.setCategory("Excel文件");//类别
        documentSummaryInformation.setManager("龚宇");//管理者
        documentSummaryInformation.setCompany("CIB");//公司

        //文档输出
        FileOutputStream out = new FileOutputStream("src/main/work/cib/excel/resources/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建批注
    @Test
    public void test10() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        //创建批注位置(row1-row2:直接理解为高度，col1-col2：直接理解为宽度)
        HSSFClientAnchor anchor = patr.createAnchor(1, 1, 1, 1, 2, 1, 5, 3);
        //创建批注
        HSSFComment comment = patr.createCellComment(anchor);
        //设置批注内容
        comment.setString(new HSSFRichTextString("这是一个批注段落！"));
        //设置批注作者
        comment.setAuthor("gongyu");
        //设置批注默认显示
        comment.setVisible(true);
        HSSFCell cell = sheet.createRow(2).createCell(1);
        cell.setCellValue("测试");
        //把批注赋值给单元格
        cell.setCellComment(comment);
        //文档输出
        FileOutputStream out = new FileOutputStream("src/main/work/cib/excel/resources/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建页眉页脚
    @Test
    public void test11() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("GongYu");// 创建工作表(Sheet)
        HSSFHeader header = sheet.getHeader();//得到页眉
        header.setLeft("页眉左边");
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer = sheet.getFooter();//得到页脚
        footer.setLeft("页脚左边");
        footer.setRight("页脚右边");
        footer.setCenter("页脚中间");

        //文档输出
        FileOutputStream out = new FileOutputStream("src/main/work/cib/excel/resources/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }
}
