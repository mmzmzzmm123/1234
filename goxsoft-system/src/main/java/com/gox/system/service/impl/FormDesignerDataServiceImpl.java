package com.gox.system.service.impl;

import java.util.List;

import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.system.domain.form.FieldsItem;
import com.gox.system.service.IFieldsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.FormDesignerDataMapper;
import com.gox.system.domain.form.FormDesignerData;
import com.gox.system.service.IFormDesignerDataService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class FormDesignerDataServiceImpl implements IFormDesignerDataService 
{
    @Autowired
    private FormDesignerDataMapper formDesignerDataMapper;
    @Autowired
    private IFieldsItemService fieldsItemService;
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public FormDesignerData selectFormDesignerDataById(Long id)
    {
        return formDesignerDataMapper.selectFormDesignerDataById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<FormDesignerData> selectFormDesignerDataList(FormDesignerData formDesignerData)
    {
        return formDesignerDataMapper.selectFormDesignerDataList(formDesignerData);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertFormDesignerData(FormDesignerData formDesignerData)
    {
        Long formId = SnowflakesTools.WORKER.nextId();
        List<FieldsItem> fields = formDesignerData.getFields();
        fieldsItemService.insertFieldsItems(fields,formId);
        formDesignerData.setId(formId);
        return formDesignerDataMapper.insertFormDesignerData(formDesignerData);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param formDesignerData 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateFormDesignerData(FormDesignerData formDesignerData)
    {
        return formDesignerDataMapper.updateFormDesignerData(formDesignerData);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteFormDesignerDataByIds(Long[] ids)
    {
        return formDesignerDataMapper.deleteFormDesignerDataByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteFormDesignerDataById(Long id)
    {
        return formDesignerDataMapper.deleteFormDesignerDataById(id);
    }
}
