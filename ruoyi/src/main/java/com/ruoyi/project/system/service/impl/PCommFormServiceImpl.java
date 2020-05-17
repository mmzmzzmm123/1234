package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.PCommFormMapper;
import com.ruoyi.project.system.domain.PCommForm;
import com.ruoyi.project.system.service.IPCommFormService;

/**
 * 数据模型Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
@Service
public class PCommFormServiceImpl implements IPCommFormService 
{
    @Autowired
    private PCommFormMapper pCommFormMapper;

    /**
     * 查询数据模型
     * 
     * @param formId 数据模型ID
     * @return 数据模型
     */
    @Override
    public PCommForm selectPCommFormById(Long formId)
    {
        return pCommFormMapper.selectPCommFormById(formId);
    }

    /**
     * 查询数据模型列表
     * 
     * @param pCommForm 数据模型
     * @return 数据模型
     */
    @Override
    public List<PCommForm> selectPCommFormList(PCommForm pCommForm)
    {
        return pCommFormMapper.selectPCommFormList(pCommForm);
    }

    /**
     * 新增数据模型
     * 
     * @param pCommForm 数据模型
     * @return 结果
     */
    @Override
    public int insertPCommForm(PCommForm pCommForm)
    {
        pCommForm.setCreateTime(DateUtils.getNowDate());
        return pCommFormMapper.insertPCommForm(pCommForm);
    }

    /**
     * 修改数据模型
     * 
     * @param pCommForm 数据模型
     * @return 结果
     */
    @Override
    public int updatePCommForm(PCommForm pCommForm)
    {
        pCommForm.setUpdateTime(DateUtils.getNowDate());
        return pCommFormMapper.updatePCommForm(pCommForm);
    }

    /**
     * 批量删除数据模型
     * 
     * @param formIds 需要删除的数据模型ID
     * @return 结果
     */
    @Override
    public int deletePCommFormByIds(Long[] formIds)
    {
        return pCommFormMapper.deletePCommFormByIds(formIds);
    }

    /**
     * 删除数据模型信息
     * 
     * @param formId 数据模型ID
     * @return 结果
     */
    @Override
    public int deletePCommFormById(Long formId)
    {
        return pCommFormMapper.deletePCommFormById(formId);
    }
}
