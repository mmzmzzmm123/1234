package com.ruoyi.web.controller.business;

import com.ruoyi.system.domain.InstrumentOpRequest;
import com.ruoyi.system.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("redis")
public class InstrumentController {
    @Autowired
    InstrumentService instrumentService;

    /**
     * 执行redis
     * @param op
     * @return
     */
    @PostMapping("/its/r")
    public String invokeRedis(@RequestBody InstrumentOpRequest.RedisOp op){
        return instrumentService.invokeRedis(op);
    }

    /**
     * 批量执行redis
     * @param ops
     * @return
     */
    @PostMapping("/its/rs")
    public List<String> batchInvokeRedis(@RequestBody List<InstrumentOpRequest.RedisOp> ops){
        return instrumentService.batchInvokeRedis(ops);
    }
}
