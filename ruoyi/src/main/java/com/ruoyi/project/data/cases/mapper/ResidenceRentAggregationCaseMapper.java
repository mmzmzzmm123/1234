package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.CleanResidenceRentAggregationCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCaseQueryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 汇总案例查询
 *
 * @author lihe
 */
@DS("compute")
public interface ResidenceRentAggregationCaseMapper {

    /**
     * 获取每月汇总案例列表
     *
     * @param yearMonth
     * @return
     */
    List<CleanResidenceRentAggregationCase> getMonthly(@Param("yearMonth") Integer yearMonth);

}
