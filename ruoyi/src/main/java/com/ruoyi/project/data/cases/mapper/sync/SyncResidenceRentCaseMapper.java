package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.CleanResidenceRentAggregationCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihe
 */
@DS("calc")
public interface SyncResidenceRentCaseMapper {

    /**
     * 创建案例汇总表
     *
     * @param yearMonth
     * @return
     */
    int createAggregationCaseTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 插入案例汇总表
     *
     * @param cleanResidenceRentAggregationCase
     * @return
     */
    int insertAggregationCaseTable(CleanResidenceRentAggregationCase cleanResidenceRentAggregationCase);

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
    int insertUltimatePriceTable(UltimateResidenceRentBasePrice ultimateOfficeBasePrice);

    /**
     * @param yearMonth
     * @param lastYearMonth
     * @return
     */
    int createView(@Param("yearMonth") Integer yearMonth, @Param("lastYearMonth") Integer lastYearMonth);
}
