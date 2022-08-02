package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Process;
import com.ruoyi.system.mapper.ProcessMapper;
import com.ruoyi.system.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 13:51
 * @description
 */
@Service
public class ProcessServiceImpl implements IProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Override
    public List<Process> getProcessList() {
        return processMapper.selectAllProcess();
    }
}
