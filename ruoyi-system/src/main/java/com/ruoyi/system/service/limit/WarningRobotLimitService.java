package com.ruoyi.system.service.limit;

public interface WarningRobotLimitService {


    /**
     * 限流
     * @param key
     * @return
     */
    public Long getWarningRobotLimit(String key,Integer interval,Integer limitCount);
}
