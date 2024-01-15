package com.ruoyi.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.excel.bo.CalculationDirectCostExcel;
import com.ruoyi.excel.bo.CalculationMaterialCostExcel;
import lombok.Data;

import java.util.List;

/**
 * @author HEHAO
 * @date 2024/1/15 15:53
 */
@Data
public class CalculationMaterialCostListener extends AnalysisEventListener<CalculationMaterialCostExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    /**
     * 缓存的数据
     */
    private List<CalculationMaterialCostExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private CalculationMaterialCostExcel calculationMaterialCostExcel;

    public CalculationMaterialCostListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        calculationMaterialCostExcel = new CalculationMaterialCostExcel();
    }


    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            Integer columnIndex = ((ExcelDataConvertException) exception).getColumnIndex() + 1;
            Integer rowIndex = ((ExcelDataConvertException) exception).getRowIndex() + 1;
            String message = "第" + rowIndex + "行，第" + columnIndex + "列,数据格式有误，请核实";
            System.out.println("导入数据转换出现错误： " + message);
        } else if (exception instanceof RuntimeException) {
            System.out.println("导入错误：" + exception);
            if (exception.getMessage().contains("列与模板上顺序不符，请勿修改表头模板")) {
                throw new RuntimeException(exception.getMessage());
            }
        }
    }

    @Override
    public void invoke(CalculationMaterialCostExcel data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:{}" + data);
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        System.out.println("解析完了");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        System.out.println("{}条数据，开始存储数据库！" + cachedDataList.size());
//        demoDAO.save(cachedDataList);
        System.out.println("存储数据库成功！");
    }
}
