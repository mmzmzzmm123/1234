package com.baoli.template.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.template.mapper.BaoliBizTemplateMapper;
import com.baoli.template.domain.BaoliBizTemplate;
import com.baoli.template.service.IBaoliBizTemplateService;

/**
 * 模板管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-22
 */
@Service
public class BaoliBizTemplateServiceImpl implements IBaoliBizTemplateService 
{
    @Autowired
    private BaoliBizTemplateMapper baoliBizTemplateMapper;

    /**
     * 查询模板管理
     * 
     * @param id 模板管理主键
     * @return 模板管理
     */
    @Override
    public BaoliBizTemplate selectBaoliBizTemplateById(Long id)
    {
        return baoliBizTemplateMapper.selectBaoliBizTemplateById(id);
    }

    /**
     * 查询模板管理列表
     * 
     * @param baoliBizTemplate 模板管理
     * @return 模板管理
     */
    @Override
    public List<BaoliBizTemplate> selectBaoliBizTemplateList(BaoliBizTemplate baoliBizTemplate)
    {
        return baoliBizTemplateMapper.selectBaoliBizTemplateList(baoliBizTemplate);
    }

    /**
     * 新增模板管理
     * 
     * @param baoliBizTemplate 模板管理
     * @return 结果
     */
    @Override
    public int insertBaoliBizTemplate(BaoliBizTemplate baoliBizTemplate)
    {
        return baoliBizTemplateMapper.insertBaoliBizTemplate(baoliBizTemplate);
    }

    /**
     * 修改模板管理
     * 
     * @param baoliBizTemplate 模板管理
     * @return 结果
     */
    @Override
    public int updateBaoliBizTemplate(BaoliBizTemplate baoliBizTemplate)
    {
        return baoliBizTemplateMapper.updateBaoliBizTemplate(baoliBizTemplate);
    }

    /**
     * 批量删除模板管理
     * 
     * @param ids 需要删除的模板管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizTemplateByIds(Long[] ids)
    {
        return baoliBizTemplateMapper.deleteBaoliBizTemplateByIds(ids);
    }

    /**
     * 删除模板管理信息
     * 
     * @param id 模板管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizTemplateById(Long id)
    {
        return baoliBizTemplateMapper.deleteBaoliBizTemplateById(id);
    }
}
