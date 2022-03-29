package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.FarmZone;

/**
 * 农场分区Mapper接口
 * 
 * @author 郏磊涛
 * @date 2022-03-28
 */
public interface FarmZoneMapper 
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
     * 删除农场分区
     * 
     * @param id 农场分区主键
     * @return 结果
     */
    public int deleteFarmZoneById(Long id);

    /**
     * 批量删除农场分区
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFarmZoneByIds(Long[] ids);
}
