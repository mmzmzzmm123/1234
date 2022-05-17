package invest.lixinger.utils.excel;

import invest.lixinger.utils.excel.excelUtils.ExcelUtils_1;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ExcelUtils_1_test {

    // 读取的功能
    @Test
    public void testRead() {
        try {
            ExcelUtils_1 eu = new ExcelUtils_1();
            eu.setExcelPath("src/main/resources/excel/importExcel/ExcelUtils_1_1.xls");

            System.out.println("=======测试Excel 默认 读取========");
            eu.readExcel();

            System.out.println("\n=======测试Excel 从第四行读取，倒数第二行结束========");
            eu = eu.RestoreSettings();  // 还原设定
            eu.setStartReadRow(3);
            eu.setEndReadRow(-1);
            eu.readExcel();

            System.out.println("\n=======测试Excel 读取第二个sheet========");
            eu = eu.RestoreSettings();  // 还原设定
            eu.setSelectedSheetIdx(1);
            eu.readExcel();

            System.out.println("\n=======测试Excel 读取所有的sheet========");
            eu = eu.RestoreSettings();  // 还原设定
            eu.setOnlyReadOneSheet(false);
            eu.readExcel();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 多个Excel的汇总合并功能，把上面的Excel内容合并到另一个Excel表中
    @Test
    public void testMerge() {
        try {
            ExcelUtils_1 eu1 = new ExcelUtils_1();
            ExcelUtils_1 eu2 = new ExcelUtils_1();
            eu1.setExcelPath("src/main/resources/excel/importExcel/ExcelUtils_1_1.xls");
            eu2.setExcelPath("src/main/resources/excel/importExcel/ExcelUtils_1_2.xls");

            System.out.println("\n=======开始操作========");
            eu1.setStartReadRow(3);
            // 明明有重复的，为什么提示重复为0呢？这是因为默认对目标文件进行了覆写，直接把源文件的数据覆盖到目标文件中。所以只会显示源文件的内容。下面代码可解决
            // 下面代码可以自己调
            // eu1.setOverWrite(false);  //是否覆写目标文件（默认覆写）
            // eu1.setComparePos(1);  //设定比较哪一列内容（默认为0，比较第一列内容）
            // eu1.setNeedCompare(false);  //设定是否比较（默认值是true）。只有当不覆盖目标文件时，设置检查重复才有效。
            // 将读取到的eu2数据和合并到eu1中，并存为demo_merge.xls
            eu1.writeExcel(eu2.readExcel(), "src/main/resources/excel/exportExcel/ExcelUtils_1_merge.xls");
            System.out.println("\n=======修改后，1.xls中的内容========");
            ExcelUtils_1 eu3 = new ExcelUtils_1();//用来读取目标xls，用于演示合并结果
            eu3.setExcelPath("src/main/resources/excel/exportExcel/ExcelUtils_1_merge.xls");
            eu3.readExcel();//读取合并后的1.xls的数据

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        ExcelUtils_1 eu = new ExcelUtils_1();
        //从第一行开始读取
        eu.setStartReadRow(1);
        eu.setExcelPath("src/main/java/work/cib/excel/resources/2.xls");
        String dist_xlsPath = "src/main/java/work/cib/excel/resources/getZiChanXiFen.xls";
        List<List<Cell>> rowList;
        try {
            rowList = eu.readExcel();
            // eu.writeExcel_xls(rowList, src_xlspath, dist_xlsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
