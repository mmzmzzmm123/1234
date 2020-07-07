package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCaseQueryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("compute")
public interface OriginalResidenceRentClosingCaseMapper {

    /**
     * 创建表
     *
     * @param yearMonth
     * @return
     */
    int createRawTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 创建清洗完成之后的表ODS_HOUSINGCASELISTED_LJ_YYYYMM
     *
     * @param yearMonth
     * @return
     */
    int createCleanTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 案例汇总表（挂牌、成交）
     *
     * @param yearMonth
     * @return
     */
    int createAssembleTable(@Param("yearMonth") Integer yearMonth);
    /**
     * 作价表
     * @param yearMonth
     * @return
     */
    int createComputeTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 人工修正价格表
     * @param yearMonth
     * @return
     */
    int createArtificialTable(@Param("yearMonth") Integer yearMonth);

    int createUltimateTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 分页总数
     *
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    Integer pageCount(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

    /**
     * @param queryModel
     * @return
     */
    List<OriginalResidenceSaleOpeningCase> pageList(OriginalResidenceSaleOpeningCaseQueryModel queryModel);
}
