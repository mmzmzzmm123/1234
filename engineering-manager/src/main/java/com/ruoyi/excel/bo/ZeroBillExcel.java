package com.ruoyi.excel.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZeroBillExcel {


    @ExcelProperty(value = "子目号", index = 0)
    private String itemNum;

    @ExcelProperty(value = "子目名称", index = 1)
    private String itemName;

    @ExcelProperty(value = "单位", index = 2)
    private String unit;

    @ExcelProperty(value = "原合同工程量清单", index = 3)
    private BigDecimal inventory;

    @ExcelProperty(value = "单价", index = 4)
    private BigDecimal unitPrice;

    @ExcelProperty(value = "原合同工程量合价", index = 5)
    private BigDecimal combinedPrice;

    @ExcelProperty(value = "0#台账数量", index = 6)
    private BigDecimal zeroCount;

    @ExcelProperty(value = "单价", index = 7)
    private BigDecimal zeroUnitPrice;

    @ExcelProperty(value = "0号台账合价", index = 8)
    private BigDecimal zeroCombinedPrice;


}
