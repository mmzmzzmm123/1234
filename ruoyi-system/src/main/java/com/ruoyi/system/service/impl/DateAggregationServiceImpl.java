package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.model.DataCodeMsgResponse;
import com.ruoyi.system.service.IDataAggregationService;
import org.springframework.stereotype.Service;

/**
 * 短信接口服务。对接文档请参考：http://47.97.21.146:9090/doc/sms#codemsg
 *
 * @author genius
 * @date 2021-07-13
 */
@Service
public class DateAggregationServiceImpl implements IDataAggregationService {

    @Override
    public DataCodeMsgResponse untrustworthyPersonnel(String phone, String code) {
        return null;
    }
}
