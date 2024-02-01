package com.ruoyi.system.service.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.dto.play.QueryMonitorLogDTO;
import com.ruoyi.system.domain.mongdb.MonitorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 15:25
 * @Description : 剧本执行日志
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MonitorLogService {

    private final MongoTemplate mongoTemplate;


    public Page<MonitorLog> logPage(QueryMonitorLogDTO dto) {
        Page<MonitorLog> page = new Page<>(dto.getPage(), dto.getLimit());
        if (StringUtils.isEmpty(dto.getPlayId())) {
            return page;
        }

        Criteria criteria = new Criteria();
        criteria.and("playId").is(dto.getPlayId());

        if (dto.getLocalDateTimeStart() != null || dto.getLocalDateTimeEnd() != null) {
            Criteria createTime = criteria.and("localDateTime");
            if (dto.getLocalDateTimeStart() != null) {
                createTime.gte(dto.getLocalDateTimeStart());
            }
            if (dto.getLocalDateTimeEnd() != null) {
                createTime.lte(dto.getLocalDateTimeEnd());
            }
        }
        if (StrUtil.isNotBlank(dto.getGroupSerialNo())) {
            criteria.and("groupSerialNo").is(dto.getGroupSerialNo());
        }
        if (StrUtil.isNotBlank(dto.getGroupName())) {
            criteria.and("groupName").regex(Pattern.compile("^.*" + dto.getGroupName() + ".*$", Pattern.CASE_INSENSITIVE));
        }
        if (StrUtil.isNotBlank(dto.getDealFunction())) {
            criteria.and("dealFunctionCodes").regex(Pattern.compile("^.*" + dto.getDealFunction() + ".*$", Pattern.CASE_INSENSITIVE));
        }
        Query query = Query.query(criteria);

        long total = mongoTemplate.count(query, MonitorLog.class);
        page.setTotal(total);
        if (total <= 0) {
            return page;
        }
        query.skip((long) (dto.getPage() - 1) * dto.getLimit());
        query.limit(dto.getLimit());
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "createTime")));
        page.setRecords(mongoTemplate.find(query, MonitorLog.class));
        return page;
    }





    public void saveLog(MonitorLog log) {
        if (log == null) {
            return;
        }
        if (StringUtils.isEmpty(log.getId())) {
            log.setId(IdWorker.getIdStr());
        }
        log.setLocalDateTime(LocalDateTime.now());
        mongoTemplate.save(log);
    }


}
