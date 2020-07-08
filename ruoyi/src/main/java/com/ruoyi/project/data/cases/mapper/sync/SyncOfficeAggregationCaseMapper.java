package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OfficeAggregationCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihe
 */
@DS("calc")
public interface SyncOfficeAggregationCaseMapper {

    /**
     * 创建案例合并库
     *
     * @param yearMonth
     * @return
     */
    int createAggregationCaseTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 推送到CALC库
     *
     * @param officeAggregationCase
     * @return
     */
    int insertAggregationCase(OfficeAggregationCase officeAggregationCase);

    /**
     * 创建最终价格表
     *
     * @param yearMonth
     * @return
     */
    int createUltimatePriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 备份价格表
     *
     * @param yearMonth
     * @param operateDate
     * @return
     */
    int dumpPriceTable(@Param("yearMonth") Integer yearMonth, @Param("operateDate") String operateDate);

    /**
     * 清空价格表
     *
     * @param yearMonth
     * @return
     */
    int clearPriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 插入最终价格
     *
     * @param ultimateOfficeBasePrice
     * @return
     */
    int insertUltimatePriceTable(UltimateOfficeBasePrice ultimateOfficeBasePrice);


}
