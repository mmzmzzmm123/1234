package com.ruoyi.project.mr.data.mapper;

import com.ruoyi.project.mr.data.domain.DataCurrentinventory;
import java.util.List;

/**
 * 当前库存存储Mapper接口
 *
 * @author mr
 * @date 2020-01-14
 */
public interface DataCurrentinventoryMapper
{
    /**
     * 查询当前库存存储
     *
     * @param id 当前库存存储ID
     * @return 当前库存存储
     */
    public DataCurrentinventory selectDataCurrentinventoryById(String id);

    /**
     * 查询当前库存存储列表
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 当前库存存储集合
     */
    public List<DataCurrentinventory> selectDataCurrentinventoryList(DataCurrentinventory dataCurrentinventory);

    /**
     * 查询当前库存存储整合列表
     *
     * @param dataCurrentinventory 当前库存存储整合
     * @return 当前库存存储集合
     */
    public List<DataCurrentinventory> selectDataCurrentinventorySum(DataCurrentinventory dataCurrentinventory);

    /**
     * 新增当前库存存储
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 结果
     */
    public int insertDataCurrentinventory(DataCurrentinventory dataCurrentinventory);

    /**
     * 修改当前库存存储
     *
     * @param dataCurrentinventory 当前库存存储
     * @return 结果
     */
    public int updateDataCurrentinventory(DataCurrentinventory dataCurrentinventory);

    /**
     * 删除当前库存存储
     *
     * @param id 当前库存存储ID
     * @return 结果
     */
    public int deleteDataCurrentinventoryById(String id);

    /**
     * 批量删除当前库存存储
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataCurrentinventoryByIds(String[] ids);
}
