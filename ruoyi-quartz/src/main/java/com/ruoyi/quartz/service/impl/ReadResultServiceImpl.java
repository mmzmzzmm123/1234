package com.ruoyi.quartz.service.impl;

import com.ruoyi.quartz.domain.ReadResult;
import com.ruoyi.quartz.mapper.ReadResultMapper;
import com.ruoyi.quartz.service.IReadResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 15:32
 * @description
 */
@Service
public class ReadResultServiceImpl implements IReadResultService {

    @Autowired
    private ReadResultMapper readResultMapper;

    @Override
    public void saveOrUpdateReadResult(List<ReadResult> readResults) {
        List<ReadResult> databases = readResultMapper.selectAllReadResult();
        if (CollectionUtils.isEmpty(databases)){
            readResults.forEach(readResult ->{
                readResultMapper.saveReadResult(readResult);
            });
        } else {
            for (ReadResult database : databases) {
                for (ReadResult readResult : readResults) {
                    if (readResult.getSlaveId().equals(database.getSlaveId()) && readResult.getAddress().equals(database.getAddress())) {
                        database.setValue(readResult.getValue());
                    }
                }
            }
            databases.forEach(readResult ->{
                readResultMapper.updateReadResult(readResult);
            });
        }
    }
}
