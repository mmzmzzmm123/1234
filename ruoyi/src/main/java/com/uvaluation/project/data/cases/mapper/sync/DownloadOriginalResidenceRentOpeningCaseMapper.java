package com.uvaluation.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.cases.domain.OriginalResidenceRentOpeningCase;

import java.util.List;

@DS("oldProd")
public interface DownloadOriginalResidenceRentOpeningCaseMapper {

    /**
     * @return
     */
    List<OriginalResidenceRentOpeningCase> download();
}
