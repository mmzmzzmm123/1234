package com.ruoyi.user.service;

import com.ruoyi.user.domain.PsyUserIntegralRecord;
import com.ruoyi.user.request.IntegralSearchReq;
import com.ruoyi.user.vo.PsyUserIntegralRecordVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户积分记录Service接口
 * 
 * @author ruoyi
 * @date 2023-07-10
 */
public interface IPsyUserIntegralRecordService
{

    List<PsyUserIntegralRecordVO> getList(IntegralSearchReq req);

    int getIntegral(BigDecimal amount, Integer type);

    Integer getUserIntegral(HttpServletRequest request);

    void insert(PsyUserIntegralRecord data);

}
