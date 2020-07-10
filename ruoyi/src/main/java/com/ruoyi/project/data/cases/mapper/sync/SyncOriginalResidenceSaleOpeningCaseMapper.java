package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import org.apache.ibatis.annotations.Param;

/**
 * 推送住宅销售挂牌案例DAO
 * @author lihe
 */
@DS("calc")
public interface SyncOriginalResidenceSaleOpeningCaseMapper {

    /**
     * 创建挂牌案例表
     *
     * @param yearMonth
     * @return
     */
    int createRawTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 插入挂牌案例表
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    int insertRawTable(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

}
