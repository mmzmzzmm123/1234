package com.ruoyi.project.mr.data.service.impl;

import com.ruoyi.project.mr.data.domain.DataCurrentinventory;
import com.ruoyi.project.mr.data.mapper.DataCurrentinventoryMapper;
import com.ruoyi.project.mr.data.service.IDataCurrentinventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 当前库存存储Service业务层处理
 *
 * @author mr
 * @date 2020-01-14
 */
@Service
public class DataCurrentinventoryServiceImpl implements IDataCurrentinventoryService
{
    @Autowired
    private DataCurrentinventoryMapper dataCurrentinventoryMapper;

    /**
     * 查询当前库存存储
     *
     * @param id 当前库存存储ID
     * @return 当前库存存储
     */
    @Override
    public DataCurrentinventory selectDataCurrentinventoryById(String id)
    {
        return dataCurrentinventoryMapper.selectDataCurrentinventoryById(id);
    }

    /**
     * 查询当前库存存储列表
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 当前库存存储
     */
    @Override
    public List<DataCurrentinventory> selectDataCurrentinventoryList(DataCurrentinventory dataCurrentinventory)
    {
        return dataCurrentinventoryMapper.selectDataCurrentinventoryList(dataCurrentinventory);
    }

    /**
     * 查询当前库存存储整合
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 当前库存存储
     */
    @Override
    public List<DataCurrentinventory> selectDataCurrentinventorySum(DataCurrentinventory dataCurrentinventory)
    {
        return dataCurrentinventoryMapper.selectDataCurrentinventorySum(dataCurrentinventory);
    }

    /**
     * 新增当前库存存储
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 结果
     */
    @Override
    public int insertDataCurrentinventory(DataCurrentinventory dataCurrentinventory)
    {
        return dataCurrentinventoryMapper.insertDataCurrentinventory(dataCurrentinventory);
    }

    /**
     * 修改当前库存存储
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 结果
     */
    @Override
    public int updateDataCurrentinventory(DataCurrentinventory dataCurrentinventory)
    {
        return dataCurrentinventoryMapper.updateDataCurrentinventory(dataCurrentinventory);
    }

    /**
     * 批量删除当前库存存储
     *
     * @param ids 需要删除的当前库存存储ID
     * @return 结果
     */
    @Override
    public int deleteDataCurrentinventoryByIds(String[] ids)
    {
        return dataCurrentinventoryMapper.deleteDataCurrentinventoryByIds(ids);
    }

    /**
     * 删除当前库存存储信息
     *
     * @param id 当前库存存储ID
     * @return 结果
     */
    @Override
    public int deleteDataCurrentinventoryById(String id)
    {
        return dataCurrentinventoryMapper.deleteDataCurrentinventoryById(id);
    }
}
