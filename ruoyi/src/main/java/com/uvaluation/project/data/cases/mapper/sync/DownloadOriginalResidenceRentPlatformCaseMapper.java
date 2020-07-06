package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidencePlatformRentOpeningCase;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@DS("spider")
public interface DownloadOriginalResidenceRentPlatformCaseMapper {

    /**
     * @return
     */
    List<OriginalResidencePlatformRentOpeningCase> download(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
