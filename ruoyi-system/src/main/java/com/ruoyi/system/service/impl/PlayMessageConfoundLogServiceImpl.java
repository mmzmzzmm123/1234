package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;
import com.ruoyi.system.mapper.PlayMessageConfoundLogMapper;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.PlayMessageConfoundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
@Slf4j
public class PlayMessageConfoundLogServiceImpl extends ServiceImpl<PlayMessageConfoundLogMapper, PlayMessageConfoundLog>
        implements PlayMessageConfoundLogService {

    @Resource
    private PlayMessageConfoundService playMessageConfoundService;


    @Override
    public Page<QueryConfoundLogVO> page(QueryConfoundLogDTO dto) {
        Page<QueryConfoundLogVO> page = new Page<>(dto.getPage(), dto.getLimit());
        baseMapper.page(page, dto);
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            String splitRegex = PlayConstants.splitRegex;
            for (QueryConfoundLogVO record : page.getRecords()) {
                if (StringUtils.isEmpty(record.getResultContent())) {
                    continue;
                }
                List<String> resultList = ListTools.newArrayList(record.getResultContent().split(splitRegex));
                record.setResultList(resultList);
            }
        }
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void retry(ConfoundRetryDTO dto) {
        if (CollectionUtils.isEmpty(dto.getIds())) {
            return;
        }
        List<PlayMessageConfoundLog> confoundLogs = super.listByIds(dto.getIds());
        Map<Integer, PlayMessageConfound> confoundMap = new HashMap<>();
        for (PlayMessageConfoundLog confoundLog : confoundLogs) {
            PlayMessageConfound confound = confoundMap.get(confoundLog.getMessageConfoundId());
            if (confound == null) {
                confound = playMessageConfoundService.getById(confoundLog.getMessageConfoundId());
                if (confound == null) {
                    continue;
                }
                confoundMap.put(confound.getId(), confound);
            }
//            this.retryingConfusion(confoundLog, confound);
        }
    }
}




