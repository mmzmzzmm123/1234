package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCaseQueryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("compute")
public interface OriginalResidenceSaleClosingCaseMapper {

    /**
     * 创建表
     *
     * @param yearMonth
     * @return
     */
    int createTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 原始成交案例
     * @param yearMonth
     * @return
     */
    int createClosingCaseRawTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 创建清洗完成之后的表ODS_HOUSINGCASELISTED_LJ_YYYYMM
     * @param yearMonth
     * @return
     */
    int createCleanTable(@Param("yearMonth") Integer yearMonth);

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
