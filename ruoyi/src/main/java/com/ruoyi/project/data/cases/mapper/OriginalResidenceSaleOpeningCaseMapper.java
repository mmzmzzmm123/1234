package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCaseQueryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住宅销售挂牌案例DAO
 *
 * @author lihe
 */
@DS("compute")
public interface OriginalResidenceSaleOpeningCaseMapper {

    /**
     * 创建表
     *
     * @param yearMonth
     * @return
     */
    int createRawTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 插入住宅销售挂牌案例
     *
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    int insertRawTable(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

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
     *
     * @param yearMonth
     * @return
     */
    int createComputePriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 人工修正价格表
     *
     * @param yearMonth
     * @return
     */
    int createArtificialPriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 结果表
     *
     * @param yearMonth
     * @return
     */
    int createUltimatePriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 分页总数
     *
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    Integer pageCount(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

    /**
     * 分页列表
     *
     * @param queryModel
     * @return
     */
    List<OriginalResidenceSaleOpeningCase> pageList(OriginalResidenceSaleOpeningCaseQueryModel queryModel);
}
