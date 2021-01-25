package com.gox.system.service;

import java.util.List;
import com.gox.system.domain.Metadata;

/**
 * 文书类基本元数据Service接口
 * 
 * @author gox
 * @date 2020-12-28
 */
public interface IMetadataService 
{
    /**
     * 查询文书类基本元数据
     * 
     * @param id 文书类基本元数据ID
     * @return 文书类基本元数据
     */
    public Metadata selectMetadataById(Long id);

    /**
     * 查询文书类基本元数据列表
     * 
     * @param metadata 文书类基本元数据
     * @return 文书类基本元数据集合
     */
    public List<Metadata> selectMetadataList(Metadata metadata);

    /**
     * 新增文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    public int insertMetadata(Metadata metadata);

    /**
     * 修改文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    public int updateMetadata(Metadata metadata);

    /**
     * 批量删除文书类基本元数据
     * 
     * @param ids 需要删除的文书类基本元数据ID
     * @return 结果
     */
    public int deleteMetadataByIds(Long[] ids);

    /**
     * 删除文书类基本元数据信息
     * 
     * @param id 文书类基本元数据ID
     * @return 结果
     */
    public int deleteMetadataById(Long id);
    /**
     * 生成档号
     */
    String generateArchivalCode(String[] fields,Object [] values);
}
