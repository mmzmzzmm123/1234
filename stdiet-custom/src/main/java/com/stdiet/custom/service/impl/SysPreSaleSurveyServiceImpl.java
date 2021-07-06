package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysPreSaleSurveyMapper;
import com.stdiet.custom.domain.SysPreSaleSurvey;
import com.stdiet.custom.service.ISysPreSaleSurveyService;

/**
 * 售前调查，销售给客户发送简易信息调查链接Service业务层处理
 *
 * @author xzj
 * @date 2021-07-05
 */
@Service
public class SysPreSaleSurveyServiceImpl implements ISysPreSaleSurveyService
{
    @Autowired
    private SysPreSaleSurveyMapper sysPreSaleSurveyMapper;

    /**
     * 查询售前调查，销售给客户发送简易信息调查链接
     *
     * @param id 售前调查，销售给客户发送简易信息调查链接ID
     * @return 售前调查，销售给客户发送简易信息调查链接
     */
    @Override
    public SysPreSaleSurvey selectSysPreSaleSurveyById(Long id)
    {
        return sysPreSaleSurveyMapper.selectSysPreSaleSurveyById(id);
    }

    /**
     * 查询售前调查，销售给客户发送简易信息调查链接列表
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 售前调查，销售给客户发送简易信息调查链接
     */
    @Override
    public List<SysPreSaleSurvey> selectSysPreSaleSurveyList(SysPreSaleSurvey sysPreSaleSurvey)
    {
        return sysPreSaleSurveyMapper.selectSysPreSaleSurveyList(sysPreSaleSurvey);
    }

    /**
     * 新增售前调查，销售给客户发送简易信息调查链接
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 结果
     */
    @Override
    public int insertSysPreSaleSurvey(SysPreSaleSurvey sysPreSaleSurvey)
    {
        sysPreSaleSurvey.setCreateTime(DateUtils.getNowDate());
        return sysPreSaleSurveyMapper.insertSysPreSaleSurvey(sysPreSaleSurvey);
    }

    /**
     * 修改售前调查，销售给客户发送简易信息调查链接
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 结果
     */
    @Override
    public int updateSysPreSaleSurvey(SysPreSaleSurvey sysPreSaleSurvey)
    {
        sysPreSaleSurvey.setUpdateTime(DateUtils.getNowDate());
        return sysPreSaleSurveyMapper.updateSysPreSaleSurvey(sysPreSaleSurvey);
    }

    /**
     * 批量删除售前调查，销售给客户发送简易信息调查链接
     *
     * @param ids 需要删除的售前调查，销售给客户发送简易信息调查链接ID
     * @return 结果
     */
    @Override
    public int deleteSysPreSaleSurveyByIds(Long[] ids)
    {
        return sysPreSaleSurveyMapper.deleteSysPreSaleSurveyByIds(ids);
    }

    /**
     * 删除售前调查，销售给客户发送简易信息调查链接信息
     *
     * @param id 售前调查，销售给客户发送简易信息调查链接ID
     * @return 结果
     */
    @Override
    public int deleteSysPreSaleSurveyById(Long id)
    {
        return sysPreSaleSurveyMapper.deleteSysPreSaleSurveyById(id);
    }

    /**
     * 根据客户唯一标识查询问卷
     * @return
     */
    @Override
    public SysPreSaleSurvey getSysPreSaleSurveyByKey(String customerKey){
        return sysPreSaleSurveyMapper.getSysPreSaleSurveyByKey(customerKey);
    }
}