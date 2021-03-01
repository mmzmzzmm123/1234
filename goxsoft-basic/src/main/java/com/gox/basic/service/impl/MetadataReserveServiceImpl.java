package com.gox.basic.service.impl;

import com.gox.basic.domain.MetadataReserve;
import com.gox.basic.mapper.MetadataReserveMapper;
import com.gox.basic.service.IMetadataReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 备用元数据Service业务层处理
 *
 * @author gox
 * @date 2021-01-13
 */
@Service
public class MetadataReserveServiceImpl implements IMetadataReserveService {
    @Autowired
    private MetadataReserveMapper metadataReserveMapper;

    /**
     * 查询备用元数据
     *
     * @param id 备用元数据ID
     * @return 备用元数据
     */
    @Override
    public MetadataReserve selectMetadataReserveById(Long id) {
        return metadataReserveMapper.selectMetadataReserveById(id);
    }

    /**
     * 查询备用元数据列表
     *
     * @param metadataReserve 备用元数据
     * @return 备用元数据
     */
    @Override
    public List<MetadataReserve> selectMetadataReserveList(MetadataReserve metadataReserve) {
        return metadataReserveMapper.selectMetadataReserveList(metadataReserve);
    }

    /**
     * 新增备用元数据
     *
     * @param metadataReserve 备用元数据
     * @return 结果
     */
    @Override
    public int insertMetadataReserve(MetadataReserve metadataReserve) {
        return metadataReserveMapper.insertMetadataReserve(metadataReserve);
    }

    /**
     * 修改备用元数据
     *
     * @param metadataReserve 备用元数据
     * @return 结果
     */
    @Override
    public int updateMetadataReserve(MetadataReserve metadataReserve) {
        return metadataReserveMapper.updateMetadataReserve(metadataReserve);
    }

    /**
     * 批量删除备用元数据
     *
     * @param ids 需要删除的备用元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataReserveByIds(Long[] ids) {
        return metadataReserveMapper.deleteMetadataReserveByIds(ids);
    }

    /**
     * 删除备用元数据信息
     *
     * @param id 备用元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataReserveById(Long id) {
        return metadataReserveMapper.deleteMetadataReserveById(id);
    }
}
