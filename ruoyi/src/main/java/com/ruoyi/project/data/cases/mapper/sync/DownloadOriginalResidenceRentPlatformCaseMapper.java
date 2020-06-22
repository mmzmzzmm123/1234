package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;

import java.util.List;

@DS("spider")
public interface DownloadOriginalResidenceRentPlatformCaseMapper {

    /**
     * @return
     */
    List<OriginalResidenceRentOpeningCase> download();
}
