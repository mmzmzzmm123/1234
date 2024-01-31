package com.ruoyi.system.service;

import com.ruoyi.system.domain.InstrumentOpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InstrumentService {

    /**
     *
     * title: 执行redis操作
     *
     * @param op
     */
    public String invokeRedis(@RequestBody InstrumentOpRequest.RedisOp op);


    /**
     *
     * title:批量 执行redis操作
     *
     * @param
     * */
    public List<String> batchInvokeRedis(@RequestBody List<InstrumentOpRequest.RedisOp> ops);
}
