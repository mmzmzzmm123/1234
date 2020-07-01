package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalOfficeCase;

import java.util.Date;
import java.util.List;

@DS("spider")
public interface DownloadOriginalOfficeCaseMapper {
    /**
     * 下载案例
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<OriginalOfficeCase> download(Date startDate, Date endDate);
}
