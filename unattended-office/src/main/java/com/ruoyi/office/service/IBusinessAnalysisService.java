package com.ruoyi.office.service;

import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.vo.BusinessAnalysisH5Vo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 经营分析接口
 * 
 * @author binhan
 * @date 2023-09-17
 */
public interface IBusinessAnalysisService
{
    public BusinessAnalysisH5Vo getBusinessAnalysis(Long userId);
}
