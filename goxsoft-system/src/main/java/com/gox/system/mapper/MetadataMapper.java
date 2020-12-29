package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.Metadata;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文书类基本元数据Mapper接口
 * 
 * @author gox
 * @date 2020-12-28
 */
@Mapper
public interface MetadataMapper 
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
     * 删除文书类基本元数据
     * 
     * @param id 文书类基本元数据ID
     * @return 结果
     */
    public int deleteMetadataById(Long id);

    /**
     * 批量删除文书类基本元数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataByIds(Long[] ids);
}
