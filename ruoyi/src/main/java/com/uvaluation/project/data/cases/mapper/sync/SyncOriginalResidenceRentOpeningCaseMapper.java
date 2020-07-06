package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidenceRentOpeningCase;
import org.apache.ibatis.annotations.Param;

@DS("calc")
public interface SyncOriginalResidenceRentOpeningCaseMapper {

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
