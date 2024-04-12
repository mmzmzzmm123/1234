package com.ruoyi.autoee.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.mapper.AFloorMapper;
import com.ruoyi.autoee.domain.AFloor;
import com.ruoyi.autoee.service.IAFloorService;

/**
 * 楼层管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@Service
public class AFloorServiceImpl implements IAFloorService 
{
    @Autowired
    private AFloorMapper aFloorMapper;

    /**
     * 查询楼层管理
     * 
     * @param id 楼层管理主键
     * @return 楼层管理
     */
    @Override
    public AFloor selectAFloorById(Long id)
    {
        return aFloorMapper.selectAFloorById(id);
    }

    /**
     * 查询楼层管理列表
     * 
     * @param aFloor 楼层管理
     * @return 楼层管理
     */
    @Override
    public List<AFloor> selectAFloorList(AFloor aFloor)
    {
        return aFloorMapper.selectAFloorList(aFloor);
    }

    /**
     * 新增楼层管理
     * 
     * @param aFloor 楼层管理
     * @return 结果
     */
    @Override
    public int insertAFloor(AFloor aFloor)
    {
        aFloor.setCreateTime(DateUtils.getNowDate());
		aFloor.setUpdateTime(aFloor.getCreateTime());
        return aFloorMapper.insertAFloor(aFloor);
    }

    /**
     * 修改楼层管理
     * 
     * @param aFloor 楼层管理
     * @return 结果
     */
    @Override
    public int updateAFloor(AFloor aFloor)
    {
        aFloor.setUpdateTime(DateUtils.getNowDate());
        return aFloorMapper.updateAFloor(aFloor);
    }

    /**
     * 批量删除楼层管理
     * 
     * @param ids 需要删除的楼层管理主键
     * @return 结果
     */
    @Override
    public int deleteAFloorByIds(Long[] ids)
    {
        return aFloorMapper.deleteAFloorByIds(ids);
    }

    /**
     * 删除楼层管理信息
     * 
     * @param id 楼层管理主键
     * @return 结果
     */
    @Override
    public int deleteAFloorById(Long id)
    {
        return aFloorMapper.deleteAFloorById(id);
    }
}
