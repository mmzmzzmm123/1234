package work.cib.excel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ImportExcel {
    public static List<String[]> getImportList(String filePath) {
        List<String[]> rowsData = new ArrayList<>();
        List<String[]> rowsData2 = new ArrayList<>();
        if (filePath.endsWith("xls")) {
            try {
                boolean importData = false;
                int maxIndex = 0;
                HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(filePath));
                HSSFSheet sheet = hwb.getSheetAt(0);
                int rows = sheet.getPhysicalNumberOfRows();
                for (int r = 0; r < rows; r++) {
                    importData = false;
                    String cellValue = "";
                    HSSFRow row = sheet.getRow(r);
                    if (row != null) {
                        int cells = row.getLastCellNum();
                        if (cells > maxIndex) {
                            maxIndex = cells;
                        }
                        String[] rowData = new String[maxIndex + cells];
                        for (int c = row.getFirstCellNum(); c < maxIndex + cells; c++) {
                            if (c >= cells) {
                                rowData[c] = "";
                                continue;
                            }
                            HSSFCell cell = row.getCell((short) c);
                            if (cell != null) {
                                if (cell.getCellType() == CellType.STRING)
                                    cellValue = cell.getStringCellValue();
                                else if (cell.getCellType() == CellType.NUMERIC) {
                                    //cellValue = cell.getNumericCellValue() + "";
                                    //cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cell.setCellType(CellType.STRING);
                                    cellValue = cell.getStringCellValue();
                                } else if (cell.getCellType() == CellType.BOOLEAN) {
                                    cellValue = cell.getStringCellValue();
                                } else {
                                    cellValue = "";
                                }
                            } else {
                                cellValue = "";
                            }
                            if (cellValue.trim().length() > 0) {
                                importData = true;
                            }
                            rowData[c] = cellValue;
                        }

                        if (r > 0 && !importData) {
                            continue;
                        }
                        rowsData.add(rowData);
                        //第一行的话就添加
                        if (r == 0) {
                            rowsData2.add(rowData);
                        }
                        if (r == 0) {
                            maxIndex = rowData.length;
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (filePath.endsWith("xlsx")) {
            try {
                boolean importData = false;
                int maxIndex = 0;
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
                XSSFSheet sheet = workbook.getSheetAt(0);
                int rows = sheet.getPhysicalNumberOfRows();
                for (int r = 0; r < rows; r++) {
                    importData = false;
                    String cellValue = "";
                    XSSFRow row = sheet.getRow(r);
                    if (row != null) {
                        int cells = row.getPhysicalNumberOfCells();
                        if (cells > maxIndex) {
                            maxIndex = cells;
                        }
                        String[] rowData = new String[maxIndex + cells];
                        for (short c = 0; c < maxIndex + cells; c++) {
                            if (c >= cells) {
                                rowData[c] = "";
                                continue;
                            }
                            XSSFCell cell = row.getCell((short) c);
                            if (cell != null) {
                                if (cell.getCellType() == CellType.STRING) {
                                    cellValue = cell.getStringCellValue();
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    //cellValue = cell.getNumericCellValue() + "";
                                    cell.setCellType(CellType.STRING);
                                    cellValue = cell.getStringCellValue();
                                } else if (cell.getCellType() == CellType.BOOLEAN) {
                                    cellValue = cell.getStringCellValue();
                                } else {
                                    cellValue = "";
                                }
                            }
                            if (cellValue.trim().length() > 0) {
                                importData = true;
                            }
                            rowData[c] = cellValue;
                            cellValue = "";
                        }
                        if (r > 0 && !importData) {
                            continue;
                        }
                        rowsData.add(rowData);
                        if (r == 0) {
                            maxIndex = rowData.length;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowsData;
    }

}
