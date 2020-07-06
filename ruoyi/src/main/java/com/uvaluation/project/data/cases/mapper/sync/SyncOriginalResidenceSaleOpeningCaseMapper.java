package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import org.apache.ibatis.annotations.Param;

@DS("calc")
public interface SyncOriginalResidenceSaleOpeningCaseMapper {

    /**
     * 创建挂牌案例表
     * @param yearMonth
     */
    int createTable(@Param("yearMonth") Integer yearMonth);

    /**
     *
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    int insert(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

}
