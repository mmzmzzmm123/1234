package com.gox.basic.service;

import com.gox.basic.domain.form.FormDesignerData;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface IFormDesignerDataService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public FormDesignerData selectFormDesignerDataById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<FormDesignerData> selectFormDesignerDataList(FormDesignerData formDesignerData);

    /**
     * 新增【请填写功能名称】
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 结果
     */
    public int insertFormDesignerData(FormDesignerData formDesignerData);

    /**
     * 修改【请填写功能名称】
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 结果
     */
    public int updateFormDesignerData(FormDesignerData formDesignerData);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFormDesignerDataByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFormDesignerDataById(Long id);
}
