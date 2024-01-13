package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.service.PlayExecutionLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 15:25
 * @Description : 剧本执行日志
 */
@Service
public class PlayExecutionLogServiceImpl implements PlayExecutionLogService {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public List<PlayExecutionLog> listByPlayId(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return new ArrayList<>();
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("playId").is(playId));
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.find(query, PlayExecutionLog.class);
    }

    @Override
    public void saveLog(String playId, String content) {
        if (StringUtils.isEmpty(playId) || StringUtils.isEmpty(content)) {
            return;
        }
        PlayExecutionLog log = new PlayExecutionLog();
        log.setId(IdWorker.getIdStr());
        log.setPlayId(playId);
        log.setContent(content);
        log.setCreateTime(new Date());
        mongoTemplate.save(log);
    }
}
