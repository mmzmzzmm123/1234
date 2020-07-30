package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentClosingCase;
import com.ruoyi.project.data.cases.domain.OtherResidenceRentClosingCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 其他住宅租赁案例下载
 *
 * @author lihe
 */
@DS("spider2")
public interface DownloadOtherResidenceRentClosingCaseMapper {

    /**
     * 租赁成交案例数据下载
     *
     * @param yearMonth
     * @return
     */
    List<OtherResidenceRentClosingCase> download(@Param("yearMonth") Integer yearMonth);
}
