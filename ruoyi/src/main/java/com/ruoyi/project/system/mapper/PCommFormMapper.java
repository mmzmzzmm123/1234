package com.ruoyi.project.system.mapper;

import java.util.List;
import com.ruoyi.project.system.domain.PCommForm;

/**
 * 数据模型Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
public interface PCommFormMapper 
{
    /**
     * 查询数据模型
     * 
     * @param formId 数据模型ID
     * @return 数据模型
     */
    public PCommForm selectPCommFormById(Long formId);

    /**
     * 查询数据模型列表
     * 
     * @param pCommForm 数据模型
     * @return 数据模型集合
     */
    public List<PCommForm> selectPCommFormList(PCommForm pCommForm);

    /**
     * 新增数据模型
     * 
     * @param pCommForm 数据模型
     * @return 结果
     */
    public int insertPCommForm(PCommForm pCommForm);

    /**
     * 修改数据模型
     * 
     * @param pCommForm 数据模型
     * @return 结果
     */
    public int updatePCommForm(PCommForm pCommForm);

    /**
     * 删除数据模型
     * 
     * @param formId 数据模型ID
     * @return 结果
     */
    public int deletePCommFormById(Long formId);

    /**
     * 批量删除数据模型
     * 
     * @param formIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePCommFormByIds(Long[] formIds);
}
