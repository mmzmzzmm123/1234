package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;

import java.util.List;

/**
 * 外网租赁案例
 *
 * @author lihe
 */
@DS("oldProd")
public interface DownloadOriginalResidenceRentOpeningCaseMapper {

    /**
     * 拉取外网租赁案例
     *
     * @return
     */
    List<OriginalResidenceRentOpeningCase> download();
}
