package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 创建案例汇总表
     *
     * @param yearMonth
     */
    void createAssembleTable(@Param("yearMonth") Integer yearMonth);

    void initAssembleTable(@Param("yearMonth") Integer yearMonth);
    void initPriceTable(@Param("yearMonth") Integer yearMonth);
}
