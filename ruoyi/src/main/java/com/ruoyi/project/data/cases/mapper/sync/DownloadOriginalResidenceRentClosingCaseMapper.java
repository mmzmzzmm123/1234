package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentClosingCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住宅租赁成交案例下载
 *
 * @author lihe
 */
@DS("calc")
public interface DownloadOriginalResidenceRentClosingCaseMapper {

    /**
     * 租赁成交案例数据下载
     *
     * @param yearMonth
     * @return
     */
    List<OriginalResidenceRentClosingCase> download(@Param("yearMonth") Integer yearMonth);

}
