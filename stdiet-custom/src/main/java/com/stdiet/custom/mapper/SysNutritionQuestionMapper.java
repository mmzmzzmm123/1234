package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysNutritionQuestion;
import org.apache.ibatis.annotations.Param;

/**
 * 营养知识小问答Mapper接口
 *
 * @author xzj
 * @date 2021-04-13
 */
public interface SysNutritionQuestionMapper
{
    /**
     * 查询营养知识小问答
     *
     * @param id 营养知识小问答ID
     * @return 营养知识小问答
     */
    public SysNutritionQuestion selectSysNutritionQuestionById(Long id);

    /**
     * 查询营养知识小问答列表
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 营养知识小问答集合
     */
    public List<SysNutritionQuestion> selectSysNutritionQuestionList(SysNutritionQuestion sysNutritionQuestion);

    /**
     * 新增营养知识小问答
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 结果
     */
    public int insertSysNutritionQuestion(SysNutritionQuestion sysNutritionQuestion);

    /**
     * 修改营养知识小问答
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 结果
     */
    public int updateSysNutritionQuestion(SysNutritionQuestion sysNutritionQuestion);

    /**
     * 删除营养知识小问答
     *
     * @param id 营养知识小问答ID
     * @return 结果
     */
    public int deleteSysNutritionQuestionById(Long id);

    /**
     * 批量删除营养知识小问答
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNutritionQuestionByIds(Long[] ids);

    /**
     * 根据关键词搜索对应营养知识问答
     * @return
     */
    public List<String> getNutritionQuestionListByKey(SysNutritionQuestion sysNutritionQuestion);

    /**
     * 查询总数
     * @return
     */
    public int getNutritionQuestionCount();

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    public List<SysNutritionQuestion> getNutritionQuestionListByPage(@Param("start")int start, @Param("pageSize")int pageSize);

    /**
     * 更新微信展示状态
     * @param wxShow 是否展示  0不展示 1展示
     * @param ids id数组
     * @return
     */
    public int updateWxShowByIds(@Param("wxShow")Integer wxShow, @Param("array") Long[] ids);
}