package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("calc")
public interface DownloadOriginalResidenceSaleClosingCaseMapper {

    /**
     * @return
     */
    List<OriginalResidenceSaleClosingCase> download(@Param("yearMonth") Integer yearMonth);
}
