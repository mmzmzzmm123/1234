package com.ruoyi.excel.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MixtureResolveExcel {
    @ExcelProperty(value = "序号", index = 0)
    private String itemNum;

    @ExcelProperty(value = "混合料名称", index = 1)
    private String itemName;

    @ExcelProperty(value = "使用部位", index = 2)
    private String partUsed;

    @ExcelProperty(value = "混合料数量", index = 3)
    private BigDecimal sumQuantity;

    @ExcelProperty(value = "配合比", index = 4)
    private BigDecimal proportion;

    @ExcelProperty(value = "材料名称", index = 5)
    private String materialsName;

    @ExcelProperty(value = "单位名称", index = 6)
    private String unit;

    @ExcelProperty(value = "理论数量", index = 7)
    private BigDecimal theoryCount;

    @ExcelProperty(value = "水泥型号", index = 8)
    private String cement325R;
    @ExcelProperty(value = "水泥型号42.5R", index = 9)
    private String cement425R;
    @ExcelProperty(value = "水泥型号430R", index = 10)
    private String cement430R;
    @ExcelProperty(value = "减水剂", index = 15)
    private Integer waterReducer;
    @ExcelProperty(value = "膨胀剂", index = 16)
    private Integer swellingAgent;
}
