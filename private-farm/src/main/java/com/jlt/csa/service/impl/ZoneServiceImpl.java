package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.ZoneMapper;
import com.jlt.csa.domain.Zone;
import com.jlt.csa.service.IZoneService;

/**
 * 农场分区Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-03-24
 */
@Service
public class ZoneServiceImpl implements IZoneService 
{
    @Autowired
    private ZoneMapper zoneMapper;

    /**
     * 查询农场分区
     * 
     * @param code 农场分区主键
     * @return 农场分区
     */
    @Override
    public Zone selectZoneByCode(String code)
    {
        return zoneMapper.selectZoneByCode(code);
    }

    /**
     * 查询农场分区列表
     * 
     * @param zone 农场分区
     * @return 农场分区
     */
    @Override
    public List<Zone> selectZoneList(Zone zone)
    {
        return zoneMapper.selectZoneList(zone);
    }

    /**
     * 新增农场分区
     * 
     * @param zone 农场分区
     * @return 结果
     */
    @Override
    public int insertZone(Zone zone)
    {
        zone.setCreateTime(DateUtils.getNowDate());
        return zoneMapper.insertZone(zone);
    }

    /**
     * 修改农场分区
     * 
     * @param zone 农场分区
     * @return 结果
     */
    @Override
    public int updateZone(Zone zone)
    {
        zone.setUpdateTime(DateUtils.getNowDate());
        return zoneMapper.updateZone(zone);
    }

    /**
     * 批量删除农场分区
     * 
     * @param codes 需要删除的农场分区主键
     * @return 结果
     */
    @Override
    public int deleteZoneByCodes(String[] codes)
    {
        return zoneMapper.deleteZoneByCodes(codes);
    }

    /**
     * 删除农场分区信息
     * 
     * @param code 农场分区主键
     * @return 结果
     */
    @Override
    public int deleteZoneByCode(String code)
    {
        return zoneMapper.deleteZoneByCode(code);
    }
}
