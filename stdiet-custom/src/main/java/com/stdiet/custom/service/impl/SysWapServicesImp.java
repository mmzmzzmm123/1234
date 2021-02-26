package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysRecipesPlanListInfo;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.custom.service.ISysRecipesPlanService;
import com.stdiet.custom.service.ISysWapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysWapServicesImp implements ISysWapServices {

    @Autowired
    ISysCustomerService iSysCustomerService;

    @Autowired
    ISysRecipesPlanService iSysRecipesPlanService;

    @Override
    public List<SysRecipesPlanListInfo> getRecipesPlanListInfo(String outId) {
        return iSysRecipesPlanService.selectRecipesPlanListInfo(outId);
    }

    @Override
    public Map<String, Object> getHealthyDataByOutId(String outId) {
        Long cusId = iSysRecipesPlanService.getCusIdByOutId(outId);
        if(cusId > 0) {
            return iSysCustomerService.getPhysicalSignsById(cusId);
        }
        return null;
    }
}
