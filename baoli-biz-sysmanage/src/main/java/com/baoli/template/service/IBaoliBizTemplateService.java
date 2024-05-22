package com.baoli.template.service;

import java.util.List;
import com.baoli.template.domain.BaoliBizTemplate;

/**
 * 模板管理Service接口
 * 
 * @author niujs
 * @date 2024-05-22
 */
public interface IBaoliBizTemplateService 
{
    /**
     * 查询模板管理
     * 
     * @param id 模板管理主键
     * @return 模板管理
     */
    public BaoliBizTemplate selectBaoliBizTemplateById(Long id);

    /**
     * 查询模板管理列表
     * 
     * @param baoliBizTemplate 模板管理
     * @return 模板管理集合
     */
    public List<BaoliBizTemplate> selectBaoliBizTemplateList(BaoliBizTemplate baoliBizTemplate);

    /**
     * 新增模板管理
     * 
     * @param baoliBizTemplate 模板管理
     * @return 结果
     */
    public int insertBaoliBizTemplate(BaoliBizTemplate baoliBizTemplate);

    /**
     * 修改模板管理
     * 
     * @param baoliBizTemplate 模板管理
     * @return 结果
     */
    public int updateBaoliBizTemplate(BaoliBizTemplate baoliBizTemplate);

    /**
     * 批量删除模板管理
     * 
     * @param ids 需要删除的模板管理主键集合
     * @return 结果
     */
    public int deleteBaoliBizTemplateByIds(Long[] ids);

    /**
     * 删除模板管理信息
     * 
     * @param id 模板管理主键
     * @return 结果
     */
    public int deleteBaoliBizTemplateById(Long id);
}
