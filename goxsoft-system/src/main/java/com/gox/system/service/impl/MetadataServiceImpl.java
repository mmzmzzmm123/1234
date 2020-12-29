package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.MetadataMapper;
import com.gox.system.domain.Metadata;
import com.gox.system.service.IMetadataService;

/**
 * 文书类基本元数据Service业务层处理
 * 
 * @author gox
 * @date 2020-12-28
 */
@Service
public class MetadataServiceImpl implements IMetadataService 
{
    @Autowired
    private MetadataMapper metadataMapper;

    /**
     * 查询文书类基本元数据
     * 
     * @param id 文书类基本元数据ID
     * @return 文书类基本元数据
     */
    @Override
    public Metadata selectMetadataById(Long id)
    {
        return metadataMapper.selectMetadataById(id);
    }

    /**
     * 查询文书类基本元数据列表
     * 
     * @param metadata 文书类基本元数据
     * @return 文书类基本元数据
     */
    @Override
    public List<Metadata> selectMetadataList(Metadata metadata)
    {
        return metadataMapper.selectMetadataList(metadata);
    }

    /**
     * 新增文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    public int insertMetadata(Metadata metadata)
    {
        return metadataMapper.insertMetadata(metadata);
    }

    /**
     * 修改文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    public int updateMetadata(Metadata metadata)
    {
        return metadataMapper.updateMetadata(metadata);
    }

    /**
     * 批量删除文书类基本元数据
     * 
     * @param ids 需要删除的文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataByIds(Long[] ids)
    {
        return metadataMapper.deleteMetadataByIds(ids);
    }

    /**
     * 删除文书类基本元数据信息
     * 
     * @param id 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataById(Long id)
    {
        return metadataMapper.deleteMetadataById(id);
    }
}
