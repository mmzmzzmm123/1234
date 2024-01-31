package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.redis.RedisHandler;
import com.ruoyi.system.domain.InstrumentOpRequest;
import com.ruoyi.system.service.InstrumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InstrumentServiceImpl implements InstrumentService {

    @Override
    public String invokeRedis(InstrumentOpRequest.RedisOp op) {
        if ("get".equalsIgnoreCase(op.getOp().trim())) {
            return RedisHandler.get(op.getKey());
        }
        if ("set".equalsIgnoreCase(op.getOp().trim())) {
            RedisHandler.set(op.getKey(), op.getValue());
            return "ok";
        }
        if ("del".equalsIgnoreCase(op.getOp().trim())) {
            RedisHandler.del(op.getKey());
            return "ok";
        }
        if ("lrange".equalsIgnoreCase(op.getOp().trim())) {
            return JSON.toJSONString(RedisHandler.lrange(op.getKey(), 0, -1));
        }
        if ("lpush".equalsIgnoreCase(op.getOp().trim())) {
            RedisHandler.lpush(op.getKey(), op.getValue());
            return "ok";
        }
        if ("lpush".equalsIgnoreCase(op.getOp().trim())) {
            RedisHandler.rpush(op.getKey(), op.getValue());
            return "ok";
        }
        return "不支持的redis 操作";
    }

    @Override
    public List<String> batchInvokeRedis(List<InstrumentOpRequest.RedisOp> ops) {
        List<String> res = Lists.newArrayList();
        for (InstrumentOpRequest.RedisOp op : ops) {
            try {
                res.add(invokeRedis(op));
            } catch (Exception e) {
                log.error("invoke工具失败", e);
            }
        }
        return res;
    }
}
