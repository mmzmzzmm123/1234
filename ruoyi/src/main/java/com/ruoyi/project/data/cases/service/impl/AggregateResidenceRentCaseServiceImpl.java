package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.domain.AggregateResidenceRentCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentClosingCase;
import com.ruoyi.project.data.cases.domain.OtherResidenceRentClosingCase;
import com.ruoyi.project.data.cases.mapper.AggregateResidenceRentCaseMapper;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOtherResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncResidenceRentCaseMapper;
import com.ruoyi.project.data.cases.service.IAggregateResidenceRentCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 住宅租赁汇总案例Service
 *
 * @author lihe
 */
@Service
@DS("compute")
public class AggregateResidenceRentCaseServiceImpl implements IAggregateResidenceRentCaseService {

    @Autowired
    private AggregateResidenceRentCaseMapper aggregateResidenceRentCaseMapper;

    @Override
    public int pageCount(AggregateResidenceRentCase aggregateResidenceRentCase) {
        return aggregateResidenceRentCaseMapper.selectPageCount(aggregateResidenceRentCase);
    }

    @Override
    public List<AggregateResidenceRentCase> pageList(AggregateResidenceRentCase aggregateResidenceRentCase) {
        return aggregateResidenceRentCaseMapper.selectPageList(aggregateResidenceRentCase);
    }

    @Override
    public List<VueSelectModel> yearMonthList() {
        return aggregateResidenceRentCaseMapper.yearMonthList();
    }
}
