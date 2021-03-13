package com.gox.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.MetadataTempMapper;
import com.gox.basic.domain.MetadataTemp;
import com.gox.basic.service.IMetadataTempService;

/**
 *   Service业务层处理
 * 
 * @author gox
 * @date 2021-03-10
 */
@Service
public class MetadataTempServiceImpl implements IMetadataTempService 
{
    @Autowired
    private MetadataTempMapper metadataTempMapper;

    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    @Override
    public MetadataTemp selectMetadataTempById(Long id)
    {
        return metadataTempMapper.selectMetadataTempById(id);
    }

    /**
     * 查询  列表
     * 
     * @param metadataTemp   
     * @return   
     */
    @Override
    public List<MetadataTemp> selectMetadataTempList(MetadataTemp metadataTemp)
    {
        return metadataTempMapper.selectMetadataTempList(metadataTemp);
    }

    /**
     * 新增  
     * 
     * @param metadataTemp   
     * @return 结果
     */
    @Override
    public int insertMetadataTemp(MetadataTemp metadataTemp)
    {
        return metadataTempMapper.insertMetadataTemp(metadataTemp);
    }

    /**
     * 修改  
     * 
     * @param metadataTemp   
     * @return 结果
     */
    @Override
    public int updateMetadataTemp(MetadataTemp metadataTemp)
    {
        return metadataTempMapper.updateMetadataTemp(metadataTemp);
    }

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteMetadataTempByIds(Long[] ids)
    {
        return metadataTempMapper.deleteMetadataTempByIds(ids);
    }

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteMetadataTempById(Long id)
    {
        return metadataTempMapper.deleteMetadataTempById(id);
    }
}
