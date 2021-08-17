package com.stdiet.custom.mapper;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysPreSaleSurvey;
import org.apache.ibatis.annotations.Param;

/**
 * 售前调查，销售给客户发送简易信息调查链接Mapper接口
 *
 * @author xzj
 * @date 2021-07-05
 */
public interface SysPreSaleSurveyMapper
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
     * 删除售前调查，销售给客户发送简易信息调查链接
     *
     * @param id 售前调查，销售给客户发送简易信息调查链接ID
     * @return 结果
     */
    public int deleteSysPreSaleSurveyById(Long id);

    /**
     * 批量删除售前调查，销售给客户发送简易信息调查链接
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPreSaleSurveyByIds(Long[] ids);

    /**
     * 根据客户唯一标识查询问卷
     * @return
     */
    public SysPreSaleSurvey getSysPreSaleSurveyByKey(@Param("customerKey")String customerKey);

    /**
     * 查询字典Label值
     * @param dictType
     * @param dictValue
     * @return
     */
    public List<Map<String,Object>> getDictLabelByIds(@Param("dictType")String dictType, @Param("dictValue")String dictValue);
}