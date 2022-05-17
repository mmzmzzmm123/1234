package invest.lixinger.utils.excel.excelUtils;
/**
 * 兴业银行zpyz项目
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import work.cib.util.DateTimeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Excel文件导出
 */
public class ExcelUtils_3 {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils_3.class);

    public static void main(String[] args) {
        String outPutPath = "";
        String fileName = "";
        List<List<Object>> dataList = new ArrayList<>();
        List<Object> headList = new ArrayList<>();
    }

    /** 宽度自适应*/
    private static void setSizeColumn(Sheet sheet, int size) {
        for (int i = 0; i < size; i++) {
            int columnW = sheet.getColumnWidth(i) / 256;
            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                Row currentRow;
                if (sheet.getRow(j) == null) {
                    currentRow = sheet.createRow(j);
                } else {
                    currentRow = sheet.getRow(j);
                }
                if (currentRow.getCell(i) != null) {
                    Cell cell = currentRow.getCell(i);
                    if (cell.getCellType() == CellType.STRING) {
                        // int length = cell.getStringCellValue().length();
                        int length = cell.getStringCellValue().length();
                        if (columnW < length) {
                            columnW = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(i, columnW * 256);
//            sheet.setColumnWidth(i, (columnW + columnW / 2) * 256);
//            sheet.setColumnWidth(i, columnW * 384);
        }
    }

    /**
     * 导出XLS文件
     */
    public static void createXLSExcelFile(String outPutPath, String fileName, List<List<Object>> dataList, List<Object> headList) {
        FileOutputStream fos = null;
        HSSFWorkbook workBook = null;
        try {
            File parent = new File(outPutPath);
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }
            String filePath = outPutPath + File.separator + fileName + ".xls";
            fos = new FileOutputStream(filePath);
            workBook = new HSSFWorkbook();
            HSSFSheet sheet = workBook.createSheet("exportUserData");
            sheet.setDefaultColumnWidth(16);
            // 数据-------------------------------
            HSSFCellStyle dataStyle = workBook.createCellStyle();
            HSSFFont dataFont = workBook.createFont();
            dataFont.setFontHeightInPoints((short) 12);
            dataStyle.setFont(dataFont);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            // 开始创建数据和格式----------------------------
            for (int i = 0; i < dataList.size(); i++) {
                List o = (List) dataList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                for (int x = 0; x < o.size(); x++) {
                    HSSFCell cell = row.createCell(x);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(dataStyle);
                    Object objVal = o.get(x);
                    if ((objVal == null) || ("".equals(objVal))) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(objVal.toString());
                    }
                }
            }
            // 表头----------------------------------
            HSSFCellStyle headStyle = workBook.createCellStyle();
            HSSFFont headFont = workBook.createFont();
            headFont.setFontName("微软雅黑");
            headFont.setFontHeightInPoints((short) 12);
            headStyle.setFont(headFont);
            headStyle.setAlignment(HorizontalAlignment.CENTER);
            // 开始创建表头数据和格式----------------------------
            HSSFRow titleRow = sheet.createRow(0);
            for (int index = 0; index < headList.size(); index++) {
                HSSFCell cell0 = titleRow.createCell(index);
                cell0.setCellType(CellType.STRING);
                cell0.setCellStyle(headStyle);
                cell0.setCellValue(headList.get(index).toString());
            }

            //宽度自适应
            for (int i = 0; i < headList.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            //中文自适应
            setSizeColumn(sheet, headList.size());
            workBook.write(fos);
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.out.println(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (workBook != null) {
                    workBook.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 导出XKS文件
     */
    public static void createXLSExcelFile1(String outPutPath, String fileName, List<List<Object>> dataList, List<Object> headList, String twoRowCell, String thirdRowCell) {
        FileOutputStream fos = null;
        //	创建excel文件
        HSSFWorkbook workBook = null;
        try {
            File parent = new File(outPutPath);
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }
            String filePath = outPutPath + File.separator + fileName + ".xls";
            fos = new FileOutputStream(filePath);
            workBook = new HSSFWorkbook();
            //	创建工作表
            HSSFSheet sheet = workBook.createSheet("exportUserData");
            //	设置宽度
            sheet.setDefaultColumnWidth(16);

            //设置单元格格式居中
            HSSFCellStyle titleStyle = workBook.createCellStyle();//标题
            HSSFCellStyle headStyle = workBook.createCellStyle();//表头
            HSSFCellStyle dataStyle = workBook.createCellStyle();//数据

            //设置单元格样式
            HSSFFont titleFont = workBook.createFont();
            titleFont.setFontHeightInPoints((short) 12);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            HSSFFont headFont = workBook.createFont();
            headFont.setFontHeightInPoints((short) 12);
            headStyle.setFont(headFont);
            headStyle.setAlignment(HorizontalAlignment.CENTER);

            dataStyle.setFont(titleFont);
            // 居中
//	      dataStyle.setVerticalAlignment(HorizontalAlignment.GENERAL);// 垂直
            dataStyle.setAlignment(HorizontalAlignment.GENERAL);// 水平
            //第一行标题
//	      HSSFCell titleCell = onehssfRow.createCell(0);
//	      titleCell.setCellValue("发票明细_"+DateTimeUtil.formatDate(new java.util.Date(),"yyyyMMdd"));
//	      titleCell.setCellStyle(titleStyle);
            //	合并第一，二，三行所有列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headList.size() - 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, headList.size() - 1));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, headList.size() - 1));
            int fpysLen = 7;
            int sbjgLen = 5;
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 1 + fpysLen));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 1 + fpysLen + 1, 1 + fpysLen + sbjgLen));
//	      sheet.addMergedRegion(new CellRangeAddress(3,3,2,15));
//	      sheet.addMergedRegion(new CellRangeAddress(3,3,17,20));
            //	合并第四行第五行的一列和二列
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
            //	合并第四行第五行的
//	      sheet.addMergedRegion(new CellRangeAddress(3,4,16,16));
            //	设置四五行行高
            HSSFRow fourRow = sheet.createRow(4);
            fourRow.setHeight((short) (24 * 20));

            //添加表头行
            HSSFRow oneRow = sheet.createRow(0);
            HSSFRow twoRow = sheet.createRow(1);
            HSSFRow thirdRow = sheet.createRow(2);

            HSSFCell oneCell = oneRow.createCell(0);
            oneCell.setCellValue("发票明细_" + DateTimeUtils.formatDateTimetoString(new java.util.Date(), "yyyyMMdd"));
            oneCell.setCellStyle(titleStyle);

            HSSFCell twoCell = twoRow.createCell(0);
            twoCell.setCellValue(twoRowCell);
            twoCell.setCellStyle(titleStyle);

            HSSFCell thirdCell = thirdRow.createCell(0);
            thirdCell.setCellValue(thirdRowCell);
            thirdCell.setCellStyle(titleStyle);

            //	设置字体居中
            fourRow.createCell(3).getCellStyle().setAlignment(HorizontalAlignment.CENTER);
            fourRow.createCell(3).getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);
            fourRow.createCell(0).setCellStyle(dataStyle);
            fourRow.createCell(1).setCellStyle(dataStyle);
            fourRow.createCell(2).setCellStyle(dataStyle);

            HSSFRow titleRow = sheet.createRow(3);

            titleRow.createCell(0).setCellValue("项目名称");
            titleRow.createCell(1).setCellValue("小序号");
            titleRow.createCell(2).setCellValue("发票要素");
//	      titleRow.createCell(16).setCellValue("购买服务和内容");
            titleRow.createCell(9).setCellValue("识别结果");

            fourRow.createCell(2).setCellValue("发票代码");
            fourRow.createCell(3).setCellValue("发票号码");
            fourRow.createCell(4).setCellValue("金额");
            fourRow.createCell(5).setCellValue("开票时间");
//	      fourRow.createCell(6).setCellValue("校验码(后六位)");
            fourRow.createCell(6).setCellValue("税后金额");
//	      fourRow.createCell(8).setCellValue("销售地址和电话");
            fourRow.createCell(7).setCellValue("销售方名称");
//	      fourRow.createCell(10).setCellValue("销售方开户行名行号");
//	      fourRow.createCell(11).setCellValue("销售方登记号码");
//	      fourRow.createCell(12).setCellValue("购买者地址和电话");
            fourRow.createCell(8).setCellValue("购买者名称");
//	      fourRow.createCell(14).setCellValue("购买者开户行名行号");
//	      fourRow.createCell(15).setCellValue("购买者注册号");

            fourRow.createCell(9).setCellValue("发票状态");
            fourRow.createCell(10).setCellValue("一次验真结果");
            fourRow.createCell(11).setCellValue("官网验真结果");
            fourRow.createCell(12).setCellValue("二次验真结果");
            fourRow.createCell(13).setCellValue("备注");

            titleRow.setHeightInPoints((short) 30);

            for (int i = 0; i < dataList.size(); i++) {
                List o = (List) dataList.get(i);
                HSSFRow row = sheet.createRow(i + 5);
                for (int x = 0; x < o.size(); x++) {
                    HSSFCell cell = row.createCell(x);
                    cell.setCellType(CellType.STRING);
                    Object objVal = o.get(x);
                    if ((objVal == null) || ("".equals(objVal))) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(objVal.toString());
                    }
                }
            }
            //宽度自适应
            for (int i = 2; i < 14; i++) {
                sheet.autoSizeColumn(i);
            }
            //中文自适应
            setSizeColumn(sheet, 14);

            workBook.write(fos);
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.out.println(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (workBook != null) {
                    workBook.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 导出XSLS文件
     */
    public static void createXLSXExcel(String outPutPath, String fileName, List<List<Object>> dataList, List<Object> headList, String dataTitle,
                                       List<String> codeInfo, String sheetTitle) {
        XSSFWorkbook xssfWb = null;
        SXSSFWorkbook sxssfWorkbook = null;
        SXSSFSheet sxssSheet = null;
        FileOutputStream fos = null;
        SXSSFSheet codessSheet = null;
        try {
            File parent = new File(outPutPath);
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }
            String filePath = outPutPath + File.separator + fileName + ".xlsx";
            fos = new FileOutputStream(filePath);
            /** 内存缓存最大行数 */
            int rowMaxCache = 500;
            xssfWb = new XSSFWorkbook();
            sxssfWorkbook = new SXSSFWorkbook(xssfWb, rowMaxCache);
            CellStyle style = sxssfWorkbook.createCellStyle();
            sxssSheet = sxssfWorkbook.createSheet(dataTitle);
            SXSSFRow titleRow = sxssSheet.createRow(0);
            for (int index = 0; index < headList.size(); index++) {
                SXSSFCell cell0 = titleRow.createCell(index);
                cell0.setCellType(CellType.STRING);
                cell0.setCellValue(headList.get(index).toString());
            }
            for (int i = 0; i < dataList.size(); i++) {
                List o = (List) dataList.get(i);
                SXSSFRow row = sxssSheet.createRow(i + 1);
                for (int x = 0; x < o.size(); x++) {
                    SXSSFCell cell = row.createCell(x);
                    cell.setCellType(CellType.STRING);
                    Object objVal = o.get(x);
                    if ((objVal == null) || ("".equals(objVal)))
                        cell.setCellValue("");
                    else {
                        if (objVal instanceof BigDecimal) {
                            double dvalue = ((BigDecimal) objVal).doubleValue();
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(style);
                            cell.setCellValue(dvalue);
                        } else {
                            cell.setCellValue(objVal.toString());
                        }
                    }
                }
            }
            if (codeInfo != null) {
                codessSheet = sxssfWorkbook.createSheet(sheetTitle);
                for (int i = 0; i < codeInfo.size(); i++) {
                    String code = codeInfo.get(i);
                    SXSSFRow row = codessSheet.createRow(i);
                    SXSSFCell cell = row.createCell(0);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(code);
                }
            }
            sxssfWorkbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 导出XSLS文件
     */
    public static void createXLSXExcel(String outPutPath, String fileName, List<List<Object>> dataList, List<Object> headList, String dataTitle,
                                       List<List<Object>> codeInfo, List<Object> codeInfoHeadList, String sheetTitle) {
        XSSFWorkbook xssfWb = null;
        SXSSFWorkbook sxssfWorkbook = null;
        SXSSFSheet sxssSheet = null;
        FileOutputStream fos = null;
        SXSSFSheet codessSheet = null;
        try {
            File parent = new File(outPutPath);
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }
            String filePath = outPutPath + File.separator + fileName + ".xlsx";
            fos = new FileOutputStream(filePath);
            /**内存缓存最大行数*/
            int rowMaxCache = 500;
            xssfWb = new XSSFWorkbook();
            sxssfWorkbook = new SXSSFWorkbook(xssfWb, rowMaxCache);
            CellStyle style = sxssfWorkbook.createCellStyle();
            sxssSheet = sxssfWorkbook.createSheet(dataTitle);
            SXSSFRow titleRow = sxssSheet.createRow(0);
            for (int index = 0; index < headList.size(); index++) {
                SXSSFCell cell0 = titleRow.createCell(index);
                cell0.setCellType(CellType.STRING);
                cell0.setCellValue(headList.get(index).toString());
            }
            for (int i = 0; i < dataList.size(); i++) {
                List o = (List) dataList.get(i);
                SXSSFRow row = sxssSheet.createRow(i + 1);
                for (int x = 0; x < o.size(); x++) {
                    SXSSFCell cell = row.createCell(x);
                    cell.setCellType(CellType.STRING);
                    Object objVal = o.get(x);
                    if ((objVal == null) || ("".equals(objVal)))
                        cell.setCellValue("");
                    else {
                        if (objVal instanceof BigDecimal) {
                            double dvalue = ((BigDecimal) objVal).doubleValue();
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(style);
                            cell.setCellValue(dvalue);
                        } else {
                            cell.setCellValue(objVal.toString());
                        }
                    }
                }
            }
            if (codeInfo != null) {
                codessSheet = sxssfWorkbook.createSheet(sheetTitle);
                SXSSFRow titleRow1 = codessSheet.createRow(0);
                for (int index = 0; index < codeInfoHeadList.size(); index++) {
                    SXSSFCell cell0 = titleRow1.createCell(index);
                    cell0.setCellType(CellType.STRING);
                    cell0.setCellValue(codeInfoHeadList.get(index).toString());
                }
                for (int i = 0; i < codeInfo.size(); i++) {
                    List o = (List) codeInfo.get(i);
                    SXSSFRow row = codessSheet.createRow(i + 1);
                    for (int x = 0; x < o.size(); x++) {
                        SXSSFCell cell = row.createCell(x);
                        cell.setCellType(CellType.STRING);
                        Object objVal = o.get(x);
                        if ((objVal == null) || ("".equals(objVal)))
                            cell.setCellValue("");
                        else {
                            if (objVal instanceof BigDecimal) {
                                double dvalue = ((BigDecimal) objVal).doubleValue();
                                style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                                cell.setCellStyle(style);
                                cell.setCellValue(dvalue);
                            } else {
                                cell.setCellValue(objVal.toString());
                            }
                        }
                    }
                }
            }
            sxssfWorkbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 导出多表头XSLS文件
     */
    public void createMultiHeadXLSXExcel(String outPutPath, String fileName, List<List<Object>> dataList, List<List<Object[]>> headList, String sheetTitle) {
        XSSFWorkbook xssfWb = null;
        SXSSFWorkbook sxssfWorkbook = null;
        SXSSFSheet sxssSheet = null;
        FileOutputStream fos = null;
        try {
            File parent = new File(outPutPath);
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }
            String filePath = outPutPath + File.separator + fileName + ".xlsx";
            fos = new FileOutputStream(filePath);
            /**内存缓存最大行数*/
            int rowMaxCache = 500;
            xssfWb = new XSSFWorkbook();
            sxssfWorkbook = new SXSSFWorkbook(xssfWb, rowMaxCache);
            CellStyle style = sxssfWorkbook.createCellStyle();
            CellStyle headStyle = sxssfWorkbook.createCellStyle();
            sxssSheet = sxssfWorkbook.createSheet(sheetTitle);
            Font headFont = sxssfWorkbook.createFont();
            //      headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);//加粗
            headFont.setFontName("微软雅黑");
            //     headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
            //     headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//居中对齐
            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());//背景色：橘色
            headStyle.setWrapText(true);//自动换行
//		      headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		      headStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		      headStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		      headStyle.setBorderTop(CellStyle.BORDER_THIN);
//		      headStyle.setBorderRight(CellStyle.BORDER_THIN);
            headStyle.setFont(headFont);
            SXSSFRow titleRow = null;
            /**多表头处理*/
            sxssSheet.setDefaultColumnWidth(10);
            for (int index = 0; index < headList.size(); index++) {
                List<Object[]> headArr = headList.get(index);
                titleRow = sxssSheet.createRow(index);
                SXSSFCell cell0 = null;
                for (int i = 0; i < headArr.size(); i++) {
                    Object[] head = headArr.get(i);
                    String field = (String) head[0];
                    int rowBg = (Integer) head[1] - 1;
                    int rowEnd = (Integer) head[2] - 1;
                    int colBg = (Integer) head[3] - 1;
                    int colEnd = (Integer) head[4] - 1;
                    if ((rowEnd - rowBg) > 0 || (colEnd - colBg) > 0) {
                        sxssSheet.addMergedRegion(new CellRangeAddress(rowBg, rowEnd, colBg, colEnd));
                    }
                    cell0 = titleRow.createCell(colBg);
                    cell0.setCellType(CellType.STRING);
                    cell0.setCellStyle(headStyle);
                    cell0.setCellValue(field);
                }
            }
            SXSSFCell cell = null;
            for (int i = 0; i < dataList.size(); i++) {
                List o = (List) dataList.get(i);
                SXSSFRow row = sxssSheet.createRow(i + headList.size());
                for (int x = 0; x < o.size(); x++) {
                    cell = row.createCell(x);
                    cell.setCellType(CellType.STRING);
                    Object objVal = o.get(x);
                    if ((objVal == null) || ("".equals(objVal)))
                        cell.setCellValue("");
                    else {
                        if (objVal instanceof BigDecimal) {
                            double dvalue = ((BigDecimal) objVal).doubleValue();
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(style);
                            cell.setCellValue(dvalue);
                        } else if (objVal instanceof Double) {
                            double dvalue = ((BigDecimal) objVal).doubleValue();
                            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(style);
                            cell.setCellValue(dvalue);
                        } else {
                            cell.setCellValue(objVal.toString());
                        }
                    }
                }
            }
            sxssfWorkbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 文件下载
     */
    public static void ExportExcelFile(HttpServletResponse response, String fileName) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
//		 String downloadPath_ = CodeCheckUtils.getMbtmz("PlatformConfiguration", "downloadFilePath");
        String downloadPath_ = "src/main/work/cib/excel/resources/ExcelUtils_2.xls";
//		 String downloadPath_ = CodeCheckUtils.getMbtmz("Excelpath", "4");
        String downloadPath = downloadPath_ + File.separator;
        File file = new File(downloadPath + fileName + ".xls" + ".dsm");
        //File file = new File(downloadPath+fileName+".xls");
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xls" + ".dsm", "UTF-8"));
            //response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName+".xls", "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int len;
            while (-1 != (len = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, len);
            }
        } catch (Exception e) {
            logger.error("Excel文件下载异常:", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                logger.error("Excel文件下载异常:", e);
            }
        }
    }


}
