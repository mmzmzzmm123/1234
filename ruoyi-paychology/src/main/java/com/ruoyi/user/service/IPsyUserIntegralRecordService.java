package com.ruoyi.user.service;

import com.ruoyi.user.domain.PsyUserIntegralRecord;

import java.math.BigDecimal;

/**
 * 用户积分记录Service接口
 * 
 * @author ruoyi
 * @date 2023-07-10
 */
public interface IPsyUserIntegralRecordService
{

    int getIntegral(BigDecimal amount, Integer type);

    void insert(PsyUserIntegralRecord data);

}
