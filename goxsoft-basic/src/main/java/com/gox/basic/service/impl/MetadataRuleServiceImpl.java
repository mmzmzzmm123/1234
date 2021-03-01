package com.gox.basic.service.impl;

import com.gox.basic.domain.MetadataRule;
import com.gox.basic.domain.vo.WSDefinition;
import com.gox.basic.mapper.MetadataRuleMapper;
import com.gox.basic.service.IMetadataRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 元数据定义规则Service业务层处理
 *
 * @author gox
 * @date 2021-01-05
 */
@Service
public class MetadataRuleServiceImpl implements IMetadataRuleService {
    @Autowired
    private MetadataRuleMapper metadataRuleMapper;

    /**
     * 查询元数据定义规则
     *
     * @param id 元数据定义规则ID
     * @return 元数据定义规则
     */
    @Override
    public MetadataRule selectMetadataRuleById(Long id) {
        return metadataRuleMapper.selectMetadataRuleById(id);
    }

    /**
     * 查询元数据定义
     *
     * @return 元数据定义
     */
    @Override
    public List<WSDefinition> selectMetadata(String nameCn) {
        return metadataRuleMapper.selectMetadata(nameCn);
    }

    /**
     * 查询元数据定义规则列表
     *
     * @param metadataRule 元数据定义规则
     * @return 元数据定义规则
     */
    @Override
    public List<MetadataRule> selectMetadataRuleList(MetadataRule metadataRule) {
        return metadataRuleMapper.selectMetadataRuleList(metadataRule);
    }

    /**
     * 新增元数据定义规则
     *
     * @param metadataRule 元数据定义规则
     * @return 结果
     */
    @Override
    public int insertMetadataRule(MetadataRule metadataRule) {
        return metadataRuleMapper.insertMetadataRule(metadataRule);
    }

    /**
     * 修改元数据定义规则
     *
     * @param metadataRule 元数据定义规则
     * @return 结果
     */
    @Override
    public int updateMetadataRule(MetadataRule metadataRule) {
        return metadataRuleMapper.updateMetadataRule(metadataRule);
    }

    /**
     * 批量删除元数据定义规则
     *
     * @param ids 需要删除的元数据定义规则ID
     * @return 结果
     */
    @Override
    public int deleteMetadataRuleByIds(Long[] ids) {
        return metadataRuleMapper.deleteMetadataRuleByIds(ids);
    }

    /**
     * 删除元数据定义规则信息
     *
     * @param id 元数据定义规则ID
     * @return 结果
     */
    @Override
    public int deleteMetadataRuleById(Long id) {
        return metadataRuleMapper.deleteMetadataRuleById(id);
    }
}
