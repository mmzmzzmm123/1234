package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalOfficeCase;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 爬取库中的办公案例
 *
 * @author lihe
 */
@DS("spider")
public interface DownloadOriginalOfficeCaseMapper {
    /**
     * 下载办公案例
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<OriginalOfficeCase> download(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
