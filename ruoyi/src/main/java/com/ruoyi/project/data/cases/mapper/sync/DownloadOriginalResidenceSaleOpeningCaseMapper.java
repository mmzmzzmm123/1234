package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;

import java.util.List;

/**
 * 外网住住宅销售挂牌案例
 *
 * @author lihe
 */
@DS("oldProd")
public interface DownloadOriginalResidenceSaleOpeningCaseMapper {

    /**
     * 获取最新的住宅销售挂牌案例列表
     *
     * @return
     */
    List<OriginalResidenceSaleOpeningCase> download();
}
