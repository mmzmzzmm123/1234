package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.FarmZoneMapper;
import com.jlt.csa.domain.FarmZone;
import com.jlt.csa.service.IFarmZoneService;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 农场分区Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-03-28
 */
@Service
public class FarmZoneServiceImpl implements IFarmZoneService 
{
    @Autowired
    private FarmZoneMapper farmZoneMapper;

    /**
     * 查询农场分区
     * 
     * @param id 农场分区主键
     * @return 农场分区
     */
    @Override
    public FarmZone selectFarmZoneById(Long id)
    {
        return farmZoneMapper.selectFarmZoneById(id);
    }

    /**
     * 查询农场分区列表
     * 
     * @param farmZone 农场分区
     * @return 农场分区
     */
    @Override
    public List<FarmZone> selectFarmZoneList(FarmZone farmZone)
    {
        return farmZoneMapper.selectFarmZoneList(farmZone);
    }

    /**
     * 新增农场分区
     * 
     * @param farmZone 农场分区
     * @return 结果
     */
    @Override
    public int insertFarmZone(FarmZone farmZone)
    {
        return farmZoneMapper.insertFarmZone(farmZone);
    }

    /**
     * 修改农场分区
     * 
     * @param farmZone 农场分区
     * @return 结果
     */
    @Override
    public int updateFarmZone(FarmZone farmZone)
    {
        return farmZoneMapper.updateFarmZone(farmZone);
    }

    /**
     * 批量删除农场分区
     * 
     * @param ids 需要删除的农场分区主键
     * @return 结果
     */
    @Override
    public int deleteFarmZoneByIds(Long[] ids)
    {
        return farmZoneMapper.deleteFarmZoneByIds(ids);
    }

    /**
     * 删除农场分区信息
     * 
     * @param id 农场分区主键
     * @return 结果
     */
    @Override
    public int deleteFarmZoneById(Long id)
    {
        return farmZoneMapper.deleteFarmZoneById(id);
    }
}
