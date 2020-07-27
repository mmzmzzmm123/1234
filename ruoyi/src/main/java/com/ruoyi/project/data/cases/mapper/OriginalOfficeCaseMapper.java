package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OfficeAggregationCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 办公案例DAO
 *
 * @author purple
 */
@DS("compute")
public interface OriginalOfficeCaseMapper {
    /**
     * 创建表
     *
     * @param yearMonth
     */
    void createTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 创建人工修正表
     *
     * @param yearMonth
     */
    void createArtificialTable(@Param("yearMonth") Integer yearMonth);
//
//    /**
//     * 创建案例汇总表
//     *
//     * @param yearMonth
//     */
//    void createAssembleTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 初始化案例汇总表
     *
     * @param yearMonth
     */
    void initAssembleTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 初始化价格表
     *
     * @param yearMonth
     */
    void initPriceTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 获取汇总案例列表
     *
     * @param yearMonth
     * @return
     */
    List<OfficeAggregationCase> getOfficeAggregationCases(@Param("yearMonth") Integer yearMonth);
}
