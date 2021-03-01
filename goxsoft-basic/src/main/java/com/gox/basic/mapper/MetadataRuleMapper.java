package com.gox.basic.mapper;

import com.gox.basic.domain.MetadataRule;
import com.gox.basic.domain.vo.WSDefinition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 元数据定义规则Mapper接口
 *
 * @author gox
 * @date 2021-01-05
 */
@Mapper
public interface MetadataRuleMapper {
    /**
     * 查询元数据定义规则
     *
     * @param id 元数据定义规则ID
     * @return 元数据定义规则
     */
    public MetadataRule selectMetadataRuleById(Long id);

    /**
     * 查询元数据定义
     *
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
     * 删除元数据定义规则
     *
     * @param id 元数据定义规则ID
     * @return 结果
     */
    public int deleteMetadataRuleById(Long id);

    /**
     * 批量删除元数据定义规则
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataRuleByIds(Long[] ids);
}
