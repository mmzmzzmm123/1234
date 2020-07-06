package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidenceRentClosingCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("calc")
public interface DownloadOriginalResidenceRentClosingCaseMapper {

    /**
     * 租赁成交案例数据下载
     *
     * @return
     */
    List<OriginalResidenceRentClosingCase> download(@Param("yearMonth") Integer yearMonth);
}
