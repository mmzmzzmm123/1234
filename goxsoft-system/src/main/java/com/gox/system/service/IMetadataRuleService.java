package com.gox.system.service;

import java.util.List;
import com.gox.system.domain.MetadataRule;
import com.gox.system.domain.vo.WSDefinition;

/**
 * 元数据定义规则Service接口
 * 
 * @author gox
 * @date 2021-01-05
 */
public interface IMetadataRuleService 
{
    /**
     * 查询元数据定义规则
     * 
     * @param id 元数据定义规则ID
     * @return 元数据定义规则
     */
    public MetadataRule selectMetadataRuleById(Long id);

    /**
     * 查询元数据定义
     * @return 元数据定义
     */
    List<WSDefinition> selectMetadata(String nameCn);
    /**
     * 查询元数据定义规则列表
     * 
     * @param metadataRule 元数据定义规则
     * @return 元数据定义规则集合
     */
    public List<MetadataRule> selectMetadataRuleList(MetadataRule metadataRule);

    /**
     * 新增元数据定义规则
     * 
     * @param metadataRule 元数据定义规则
     * @return 结果
     */
    public int insertMetadataRule(MetadataRule metadataRule);

    /**
     * 修改元数据定义规则
     * 
     * @param metadataRule 元数据定义规则
     * @return 结果
     */
    public int updateMetadataRule(MetadataRule metadataRule);

    /**
     * 批量删除元数据定义规则
     * 
     * @param ids 需要删除的元数据定义规则ID
     * @return 结果
     */
    public int deleteMetadataRuleByIds(Long[] ids);

    /**
     * 删除元数据定义规则信息
     * 
     * @param id 元数据定义规则ID
     * @return 结果
     */
    public int deleteMetadataRuleById(Long id);
}
