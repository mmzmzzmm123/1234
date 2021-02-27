package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysRecipesPlanListInfo;

import java.util.List;
import java.util.Map;

public interface ISysWapServices {

    List<SysRecipesPlanListInfo> getRecipesPlanListInfo(String outId);

    Map<String, Object> getHealthyDataByOutId(String outId);
}
