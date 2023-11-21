package com.ruoyi.psychology.task;

import com.ruoyi.psychology.service.IPsyConsultContractService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("consultContractTask")
public class ContractTask {

    @Resource
    private IPsyConsultContractService consultContractService;

    public void doJob()
    {
        consultContractService.doJob();
    }

}
