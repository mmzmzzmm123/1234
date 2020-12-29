package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ElectronicAttributesMapper;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;

/**
 * 电子文件信息Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class ElectronicAttributesServiceImpl implements IElectronicAttributesService 
{
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;

    /**
     * 查询电子文件信息
     * 
     * @param id 电子文件信息ID
     * @return 电子文件信息
     */
    @Override
    public ElectronicAttributes selectElectronicAttributesById(Long id)
    {
        return electronicAttributesMapper.selectElectronicAttributesById(id);
    }

    /**
     * 查询电子文件信息列表
     * 
     * @param electronicAttributes 电子文件信息
     * @return 电子文件信息
     */
    @Override
    public List<ElectronicAttributes> selectElectronicAttributesList(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.selectElectronicAttributesList(electronicAttributes);
    }

    /**
     * 新增电子文件信息
     * 
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int insertElectronicAttributes(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.insertElectronicAttributes(electronicAttributes);
    }

    /**
     * 修改电子文件信息
     * 
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int updateElectronicAttributes(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.updateElectronicAttributes(electronicAttributes);
    }

    /**
     * 批量删除电子文件信息
     * 
     * @param ids 需要删除的电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesByIds(Long[] ids)
    {
        return electronicAttributesMapper.deleteElectronicAttributesByIds(ids);
    }

    /**
     * 删除电子文件信息信息
     * 
     * @param id 电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesById(Long id)
    {
        return electronicAttributesMapper.deleteElectronicAttributesById(id);
    }
}
