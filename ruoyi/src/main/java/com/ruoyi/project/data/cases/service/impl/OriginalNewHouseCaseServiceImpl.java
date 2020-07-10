package com.ruoyi.project.data.cases.service.impl;

import com.ruoyi.common.constant.LabelConstants;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.mapper.OriginalNewHouseCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalNewHouseCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalNewHouseCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 一手房成交案例Service
 *
 * @author lihe
 */
@Service
public class OriginalNewHouseCaseServiceImpl implements IOriginalNewHouseCaseService {

    @Autowired
    private OriginalNewHouseCaseMapper originalNewHouseCaseMapper;

    @Override
    public List<OriginalNewHouseCase> selectList(OriginalNewHouseCase originalNewHouseCase) {
        return originalNewHouseCaseMapper.pageList(originalNewHouseCase);
    }

    @Override
    public int selectCount(OriginalNewHouseCase originalNewHouseCase) {
        return originalNewHouseCaseMapper.pageCount(originalNewHouseCase);
    }
}
