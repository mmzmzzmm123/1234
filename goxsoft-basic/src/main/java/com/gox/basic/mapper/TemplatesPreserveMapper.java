package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.TemplatesPreserve;
import org.springframework.stereotype.Repository;

/**
 * 模板维护Mapper接口
 * 
 * @author gox
 * @date 2021-03-06
 */
@Repository
public interface TemplatesPreserveMapper 
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

    /**
     * 删除模板维护
     * 
     * @param id 模板维护ID
     * @return 结果
     */
    public int deleteTemplatesPreserveById(Long id);

    /**
     * 批量删除模板维护
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTemplatesPreserveByIds(Long[] ids);

    int insertTemplatesPreserveBatch(List<TemplatesPreserve> templates);

    int updateTemplatesBatch(List<TemplatesPreserve> templates);
}
