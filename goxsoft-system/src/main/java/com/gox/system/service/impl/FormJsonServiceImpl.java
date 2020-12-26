package com.gox.system.service.impl;

import java.util.List;
import com.gox.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.FormJsonMapper;
import com.gox.system.domain.FormJson;
import com.gox.system.service.IFormJsonService;

/**
 * 单json存储Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-25
 */
@Service
public class FormJsonServiceImpl implements IFormJsonService 
{
    @Autowired
    private FormJsonMapper formJsonMapper;

    /**
     * 查询单json存储
     * 
     * @param id 单json存储ID
     * @return 单json存储
     */
    @Override
    public FormJson selectFormJsonById(Long id)
    {
        return formJsonMapper.selectFormJsonById(id);
    }

    /**
     * 查询单json存储列表
     * 
     * @param formJson 单json存储
     * @return 单json存储
     */
    @Override
    public List<FormJson> selectFormJsonList(FormJson formJson)
    {
        return formJsonMapper.selectFormJsonList(formJson);
    }

    /**
     * 新增单json存储
     * 
     * @param formJson 单json存储
     * @return 结果
     */
    @Override
    public int insertFormJson(FormJson formJson)
    {
        formJson.setCreateTime(DateUtils.getNowDate());
        return formJsonMapper.insertFormJson(formJson);
    }

    /**
     * 修改单json存储
     * 
     * @param formJson 单json存储
     * @return 结果
     */
    @Override
    public int updateFormJson(FormJson formJson)
    {
        formJson.setUpdateTime(DateUtils.getNowDate());
        return formJsonMapper.updateFormJson(formJson);
    }

    /**
     * 批量删除单json存储
     * 
     * @param ids 需要删除的单json存储ID
     * @return 结果
     */
    @Override
    public int deleteFormJsonByIds(Long[] ids)
    {
        return formJsonMapper.deleteFormJsonByIds(ids);
    }

    /**
     * 删除单json存储信息
     * 
     * @param id 单json存储ID
     * @return 结果
     */
    @Override
    public int deleteFormJsonById(Long id)
    {
        return formJsonMapper.deleteFormJsonById(id);
    }
}
