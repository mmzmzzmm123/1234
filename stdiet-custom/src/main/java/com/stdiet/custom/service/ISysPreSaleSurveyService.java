package com.stdiet.custom.service;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysPreSaleSurvey;

/**
 * 售前调查，销售给客户发送简易信息调查链接Service接口
 *
 * @author xzj
 * @date 2021-07-05
 */
public interface ISysPreSaleSurveyService
{
    /**
     * 查询售前调查，销售给客户发送简易信息调查链接
     *
     * @param id 售前调查，销售给客户发送简易信息调查链接ID
     * @return 售前调查，销售给客户发送简易信息调查链接
     */
    public SysPreSaleSurvey selectSysPreSaleSurveyById(Long id);

    /**
     * 查询售前调查，销售给客户发送简易信息调查链接列表
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 售前调查，销售给客户发送简易信息调查链接集合
     */
    public List<SysPreSaleSurvey> selectSysPreSaleSurveyList(SysPreSaleSurvey sysPreSaleSurvey);

    /**
     * 新增售前调查，销售给客户发送简易信息调查链接
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 结果
     */
    public int insertSysPreSaleSurvey(SysPreSaleSurvey sysPreSaleSurvey);

    /**
     * 修改售前调查，销售给客户发送简易信息调查链接
     *
     * @param sysPreSaleSurvey 售前调查，销售给客户发送简易信息调查链接
     * @return 结果
     */
    public int updateSysPreSaleSurvey(SysPreSaleSurvey sysPreSaleSurvey);

    /**
     * 批量删除售前调查，销售给客户发送简易信息调查链接
     *
     * @param ids 需要删除的售前调查，销售给客户发送简易信息调查链接ID
     * @return 结果
     */
    public int deleteSysPreSaleSurveyByIds(Long[] ids);

    /**
     * 删除售前调查，销售给客户发送简易信息调查链接信息
     *
     * @param id 售前调查，销售给客户发送简易信息调查链接ID
     * @return 结果
     */
    public int deleteSysPreSaleSurveyById(Long id);

    /**
     * 根据客户唯一标识查询问卷
     * @return
     */
    public SysPreSaleSurvey getSysPreSaleSurveyByKey(String customerKey);
}