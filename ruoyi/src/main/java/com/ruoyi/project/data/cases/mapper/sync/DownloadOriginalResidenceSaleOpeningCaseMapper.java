package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;

import java.util.List;

@DS("oldProd")
public interface DownloadOriginalResidenceSaleOpeningCaseMapper {

    /**
     * @return
     */
    List<OriginalResidenceSaleOpeningCase> download();
}
