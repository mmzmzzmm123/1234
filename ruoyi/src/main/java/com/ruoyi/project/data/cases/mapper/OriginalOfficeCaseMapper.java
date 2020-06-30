package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;

@DS("compute")
public interface OriginalOfficeCaseMapper {
    /**
     * 创建表
     *
     * @param yearMonth
     */
    void createTable(Integer yearMonth);

    /**
     * 创建人工修正表
     *
     * @param yearMonth
     */
    void createArtificialTable(Integer yearMonth);
}
