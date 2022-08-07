package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.WorkProcess;
import com.ruoyi.system.mapper.WorkProcessMapper;
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
    private WorkProcessMapper processMapper;

    @Override
    public List<WorkProcess> getProcessList() {
        WorkProcess workProcess = new WorkProcess();
        return processMapper.selectWorkProcessList(workProcess);
    }
}
