package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihe
 */
@DS("calc")
public interface SyncOriginalResidenceRentAggregationCaseMapper {

    /**
     * 创建挂牌案例表
     * @param yearMonth
     */
    int createTable(@Param("yearMonth") Integer yearMonth);

    /**
     *
     * @param originalResidenceRentOpeningCase
     * @return
     */
    int insert(OriginalResidenceRentOpeningCase originalResidenceRentOpeningCase);

}
