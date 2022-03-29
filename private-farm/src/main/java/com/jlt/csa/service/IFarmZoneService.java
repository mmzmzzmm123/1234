package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.FarmZone;

/**
 * 农场分区Service接口
 * 
 * @author 郏磊涛
 * @date 2022-03-28
 */
public interface IFarmZoneService 
{
    /**
     * 查询农场分区
     * 
     * @param id 农场分区主键
     * @return 农场分区
     */
    public FarmZone selectFarmZoneById(Long id);

    /**
     * 查询农场分区列表
     * 
     * @param farmZone 农场分区
     * @return 农场分区集合
     */
    public List<FarmZone> selectFarmZoneList(FarmZone farmZone);

    /**
     * 新增农场分区
     * 
     * @param farmZone 农场分区
     * @return 结果
     */
    public int insertFarmZone(FarmZone farmZone);

    /**
     * 修改农场分区
     * 
     * @param farmZone 农场分区
     * @return 结果
     */
    public int updateFarmZone(FarmZone farmZone);

    /**
     * 批量删除农场分区
     * 
     * @param ids 需要删除的农场分区主键集合
     * @return 结果
     */
    public int deleteFarmZoneByIds(Long[] ids);

    /**
     * 删除农场分区信息
     * 
     * @param id 农场分区主键
     * @return 结果
     */
    public int deleteFarmZoneById(Long id);
}
