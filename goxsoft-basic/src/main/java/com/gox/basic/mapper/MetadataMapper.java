package com.gox.basic.mapper;

import com.gox.basic.domain.Metadata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 文书类基本元数据Mapper接口
 *
 * @author gox
 * @date 2020-12-28
 */
@Mapper
@Repository
public interface MetadataMapper {
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
    public int insertMetadataBatch(Collection<Metadata> metadatas);
    /**
     * 修改文书类基本元数据
     *
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    public int updateMetadata(Metadata metadata);
    public int updateMetadataBatch(List<Metadata> metadata);

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

    /**
     * id数组查询
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    List<Metadata> selectMetadataByIds(Long[] ids);
}
