package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.system.domain.dto.play.QueryExecutionLogDTO;
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
    public Page<PlayExecutionLog> logPage(QueryExecutionLogDTO dto) {
        Page<PlayExecutionLog> page = new Page<>(dto.getPage(),dto.getLimit());
        if (StringUtils.isEmpty(dto.getPlayId())) {
            return page;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("playId").is(dto.getPlayId()));
        if (StringUtils.isNotBlank(dto.getMaxId())){
            query.addCriteria(Criteria.where("id").gt(dto.getMaxId()));
        }
        if (StringUtils.isNotBlank(dto.getMinId())){
            query.addCriteria(Criteria.where("id").lt(dto.getMinId()));
        }
        long total = mongoTemplate.count(query, PlayExecutionLog.class);
        page.setTotal(total);
        if (total <= 0) {
            return page;
        }
        query.skip((dto.getPage() - 1) * dto.getLimit());
        query.limit(dto.getLimit());
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
        page.setRecords(mongoTemplate.find(query, PlayExecutionLog.class));
        return page;
    }

    @Override
    public PlayExecutionLog getOne(String playId, String groupId) {
        if (StringUtils.isEmpty(playId) || StringUtils.isEmpty(groupId)){
             return null;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("playId").is(playId));
        query.addCriteria(Criteria.where("groupId").is(groupId));
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.findOne(query,PlayExecutionLog.class);

    }

    @Override
    public void saveLog(PlayExecutionLog log) {
        if (log == null){
            return;
        }
        if (log.getType() == null){
            return;
        }
        if (StringUtils.isEmpty(log.getPlayId()) || StringUtils.isEmpty(log.getContent())){
            return;
        }

        if (StringUtils.isEmpty(log.getId())){
            log.setId(IdWorker.getIdStr());
        }
        log.setCreateTime(new Date());
        mongoTemplate.save(log);
    }

    @Override
    public long getMonitorTriggerNum(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return 0L;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("playId").is(playId));
        query.addCriteria(Criteria.where("type").is(PlayLogTyper.Advertising_Monitoring));
        return mongoTemplate.count(query, PlayExecutionLog.class);

    }
}
