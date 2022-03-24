package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.Zone;

/**
 * 农场分区Service接口
 * 
 * @author 郏磊涛
 * @date 2022-03-24
 */
public interface IZoneService 
{
    /**
     * 查询农场分区
     * 
     * @param code 农场分区主键
     * @return 农场分区
     */
    public Zone selectZoneByCode(String code);

    /**
     * 查询农场分区列表
     * 
     * @param zone 农场分区
     * @return 农场分区集合
     */
    public List<Zone> selectZoneList(Zone zone);

    /**
     * 新增农场分区
     * 
     * @param zone 农场分区
     * @return 结果
     */
    public int insertZone(Zone zone);

    /**
     * 修改农场分区
     * 
     * @param zone 农场分区
     * @return 结果
     */
    public int updateZone(Zone zone);

    /**
     * 批量删除农场分区
     * 
     * @param codes 需要删除的农场分区主键集合
     * @return 结果
     */
    public int deleteZoneByCodes(String[] codes);

    /**
     * 删除农场分区信息
     * 
     * @param code 农场分区主键
     * @return 结果
     */
    public int deleteZoneByCode(String code);
}
