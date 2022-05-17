package invest.lixinger.utils.excel.excelUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static invest.lixinger.utils.excel.excelUtils.ExcelUtils_utils.judgeReadPathExist;


public class ExcelUtils_1 extends ExcelUtils_1_variable {

    public ExcelUtils_1() {
    }

    /**
     * 还原设定（其实是重新new一个新的对象并返回）
     */
    public ExcelUtils_1 RestoreSettings() {
        ExcelUtils_1 instance = new ExcelUtils_1();
        instance.setExcelPath(this.getExcelPath());
        return instance;
    }

    public List<List<Cell>> readExcel() throws IOException {
        String excelPath = this.getExcelPath();
        judgeReadPathExist(excelPath);
        return readExcel(excelPath);
    }

    public List<List<Cell>> readExcel(String excelPath) throws IOException {
        Workbook wb = null;
        List<List<Cell>> rowList = new ArrayList<>();
        wb = ExcelUtils_utils.getWorkbook(new File(excelPath), excelPath.substring(excelPath.lastIndexOf(".") + 1));
        rowList = readExcel(wb);
        return rowList;
    }

    /**
     * 通用readExcel,xls和xlsx都是用这个
     */
    private List<List<Cell>> readExcel(Workbook wb) {
        List<Row> rowList = new ArrayList<>();
        List<List<Cell>> allRowList = new ArrayList<>();
        int sheetCount = 1;
        Sheet sheet = null;
        //只操作一个sheet
        if (this.isOnlyReadOneSheet()) {
            // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
            sheet = this.getSelectedSheetName().equals("") ? wb.getSheetAt(this.getSelectedSheetIdx()) : wb.getSheet(this.getSelectedSheetName());
        } else {//操作多个sheet
            sheetCount = wb.getNumberOfSheets();  //获取可以操作的总数量
        }

        // 获取sheet数目
        for (int t = this.getStartSheetIdx(); t < sheetCount + this.getEndSheetIdx(); t++) {
            // 获取设定操作的sheet
            if (!this.isOnlyReadOneSheet()) {
                sheet = wb.getSheetAt(t);
            }
            //获取最后行号
            assert sheet != null;
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum > 0) {    //如果>0，表示有数据
                ExcelUtils_utils.out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：", true);
            }

            Row row = null;
            for (int i = this.getStartReadRow(); i <= lastRowNum + this.getEndReadRow(); i++) {
                row = sheet.getRow(i);
                allRowList.add(new ArrayList<>());

                if (row != null) {
                    ExcelUtils_utils.out("第" + (i + 1) + "行：", this.isPrintMsg(), false);
                    // 获取每一单元格的值
                    rowList.add(row);
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        allRowList.get(i).add(row.getCell(j));
                        String value = ExcelUtils_utils.getCellValueString(row.getCell(j));
                        //只会把非空值打印出来
                        if (!value.equals("")) {
                            ExcelUtils_utils.out(value + " | ", this.isPrintMsg(), false);
                        }
                    }
                    ExcelUtils_utils.out("", this.isPrintMsg());
                } else {
                    ExcelUtils_utils.out("--------------第" + (i + 1) + "行为null，已经被跳过-------------------", true);
                }
            }
        }
        return allRowList;
    }

    public void writeExcel(List<List<Cell>> fromRowList, String savePath) throws IOException {
        String toXlsPath = this.getExcelPath();
        ExcelUtils_utils.judgeWritePathExist(fromRowList, toXlsPath, savePath);
        Workbook toWb = null;
        toWb = ExcelUtils_utils.getWorkbook(new File(savePath), savePath.substring(savePath.lastIndexOf(".") + 1));
        writeExcel(toWb, fromRowList, savePath);
    }


    /**
     * 通用修改Excel，并另存为
     * fromRowList:为src数据
     */
    private void writeExcel(Workbook toWb, List<List<Cell>> fromRowList, String savePath) {
        if (toWb == null) {
            ExcelUtils_utils.out("操作文档不能为空！", true);
            return;
        }
        Sheet toSheet = toWb.getSheetAt(0);
        // 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行
        int lastRowNum = this.isOverWrite() ? this.getStartReadRow() : toSheet.getLastRowNum() + 1;
        int newRow = 0;  // 新添加的行数
        ExcelUtils_utils.out("要添加的数据总行数为：" + fromRowList.size(), true);
        for (List<Cell> fromRow : fromRowList) {
            if (fromRow == null) continue;
            // comparePos：只比较这个常量
            // 判断fromRowList和toWb有多少重复的数据行
            int pos = findInExcel(toSheet, fromRow);
            // 如果数据行已经存在则删除后建新行。
            Row toRow = null;
            if (pos >= 0) {
                toSheet.removeRow(toSheet.getRow(pos));
                toRow = toSheet.createRow(pos);
            } else {
                toRow = toSheet.createRow(lastRowNum + newRow++);
            }

            // 循环每列创建单元格，前面还有一个row的循环
            for (int i = 0; i < fromRow.size(); i++) {
                CellStyle newstyle = toWb.createCellStyle();
                Cell toCell = toRow.createCell(i);
                // 复制单元格的值到新的单元格
                toCell.setCellValue(ExcelUtils_utils.getCellValueString(fromRow.get(i)));
                // 假如不用continue会出错
                if (fromRow.get(i) == null) continue;
                newstyle.cloneStyleFrom(fromRow.get(i).getCellStyle());
                toCell.setCellStyle(newstyle);
                // 自动宽度
                // sheet.autoSizeColumn(i);
            }
        }
        ExcelUtils_utils.out("其中检测到重复行数为:" + (fromRowList.size() - newRow) + " ，追加行数为：" + newRow, true);

        // 合并单元格
        setMergedRegion(toSheet);

        try {
            // 将数据写入Excel中
            FileOutputStream outputStream = new FileOutputStream(savePath);
            toWb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            ExcelUtils_utils.out("写入Excel时发生错误！ ", true);
            e.printStackTrace();
        }
    }

    /**
     * 查找某行数据是否在Excel表中存在，返回行数。
     */
    private int findInExcel(Sheet toSheet, List<Cell> fromRow) {
        int rowPos = -1;
        try {
            // 如果覆写目标文件||不需要比较，则直接返回
            if (this.isOverWrite() || !this.isNeedCompare()) {
                return rowPos;
            }
            for (int i = this.getStartReadRow(); i <= toSheet.getLastRowNum() + this.getEndReadRow(); i++) {
                Row toR = toSheet.getRow(i);
                if (toR != null && fromRow != null) {
                    String v1 = ExcelUtils_utils.getCellValueString(toR.getCell(this.getComparePos()));
                    String v2 = ExcelUtils_utils.getCellValueString(fromRow.get(this.getComparePos()));
                    if (v1.equals(v2)) {
                        rowPos = i;
                        break;
                    }
                }
                System.out.println("--------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowPos;
    }

    /**
     * 获取合并单元格的值
     */
    public void setMergedRegion(Sheet toSheet) {
        int sheetMergeCount = toSheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            // 获取合并单元格位置
            CellRangeAddress ca = toSheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            // 如果第一个合并单元格格式在正式数据的上面，则跳过
            if (this.getStartReadRow() - 1 > firstRow) {
                continue;
            }
            int lastRow = ca.getLastRow();
            // 合并的行数
            int mergeRows = lastRow - firstRow;
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            // 根据合并的单元格位置和大小，调整所有的数据行格式，
            for (int j = lastRow + 1; j <= toSheet.getLastRowNum(); j++) {
                // 设定合并单元格
                toSheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
                // 跳过已合并的行
                j = j + mergeRows;
            }
        }
    }

}
