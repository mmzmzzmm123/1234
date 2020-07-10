package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 下载住宅销售成交案例
 *
 * @author lihe
 * @date 2020年7月10日
 */
@DS("calc")
public interface DownloadOriginalResidenceSaleClosingCaseMapper {

    /**
     * 获取住宅销售成交案例
     *
     * @param yearMonth
     * @return
     */
    List<OriginalResidenceSaleClosingCase> download(@Param("yearMonth") Integer yearMonth);
}
