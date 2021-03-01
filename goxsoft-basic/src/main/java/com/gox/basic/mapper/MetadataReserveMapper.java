package com.gox.basic.mapper;

import com.gox.basic.domain.MetadataReserve;

import java.util.List;

/**
 * 备用元数据Mapper接口
 *
 * @author gox
 * @date 2021-01-13
 */
public interface MetadataReserveMapper {
    /**
     * 查询备用元数据
     *
     * @param id 备用元数据ID
     * @return 备用元数据
     */
    public MetadataReserve selectMetadataReserveById(Long id);

    /**
     * 查询备用元数据列表
     *
     * @param metadataReserve 备用元数据
     * @return 备用元数据集合
     */
    public List<MetadataReserve> selectMetadataReserveList(MetadataReserve metadataReserve);

    /**
     * 新增备用元数据
     *
     * @param metadataReserve 备用元数据
     * @return 结果
     */
    public int insertMetadataReserve(MetadataReserve metadataReserve);

    /**
     * 修改备用元数据
     *
     * @param metadataReserve 备用元数据
     * @return 结果
     */
    public int updateMetadataReserve(MetadataReserve metadataReserve);

    /**
     * 删除备用元数据
     *
     * @param id 备用元数据ID
     * @return 结果
     */
    public int deleteMetadataReserveById(Long id);

    /**
     * 批量删除备用元数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataReserveByIds(Long[] ids);
}
