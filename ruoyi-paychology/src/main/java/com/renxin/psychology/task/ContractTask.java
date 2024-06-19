package com.renxin.psychology.task;

import com.renxin.psychology.service.IPsyConsultContractService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("consultContractTask")
public class ContractTask {

    @Resource
    private IPsyConsultContractService consultContractService;

    public void doJob()
    {
        consultContractService.doJob();
//        consultContractService.initData();
    }

}
