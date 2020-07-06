package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("calc")
public interface DownloadOriginalResidenceSaleClosingCaseMapper {

    /**
     * @return
     */
    List<OriginalResidenceSaleClosingCase> download(@Param("yearMonth") Integer yearMonth);
}
