package com.gox.basic.service;

import java.util.List;
import com.gox.basic.domain.TemplatesPreserve;

/**
 * 模板维护Service接口
 * 
 * @author gox
 * @date 2021-03-06
 */

public interface ITemplatesPreserveService 
{
    /**
     * 查询模板维护
     * 
     * @param id 模板维护ID
     * @return 模板维护
     */
    public TemplatesPreserve selectTemplatesPreserveById(Long id);

    /**
     * 查询模板维护列表
     * 
     * @param templatesPreserve 模板维护
     * @return 模板维护集合
     */
    public List<TemplatesPreserve> selectTemplatesPreserveList(TemplatesPreserve templatesPreserve);

    /**
     * 新增模板维护
     * 
     * @param templatesPreserve 模板维护
     * @return 结果
     */
    public int insertTemplatesPreserve(TemplatesPreserve templatesPreserve);

    /**
     * 修改模板维护
     * 
     * @param templatesPreserve 模板维护
     * @return 结果
     */
    public int updateTemplatesPreserve(TemplatesPreserve templatesPreserve);
    public int updateTemplatesBatch(List<TemplatesPreserve> templates);
    /**
     * 批量删除模板维护
     * 
     * @param ids 需要删除的模板维护ID
     * @return 结果
     */
    public int deleteTemplatesPreserveByIds(Long[] ids);

    /**
     * 删除模板维护信息
     * 
     * @param id 模板维护ID
     * @return 结果
     */
    public int deleteTemplatesPreserveById(Long id);

    int insertTemplatesPreserveBatch(List<TemplatesPreserve> templates);

    int updateTemplatesPreserveBatch(List<TemplatesPreserve> templates,List<TemplatesPreserve> templates2);
}
