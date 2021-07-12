package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.BoatMapper;
import com.apollo.usv.domain.Boat;
import com.apollo.usv.service.IBoatService;

/**
 * 航船Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-02
 */
@Service
public class BoatServiceImpl implements IBoatService 
{
    @Autowired
    private BoatMapper boatMapper;

    /**
     * 查询航船
     * 
     * @param id 航船ID
     * @return 航船
     */
    @Override
    public Boat selectBoatById(Long id)
    {
        return boatMapper.selectBoatById(id);
    }

    /**
     * 查询航船列表
     * 
     * @param boat 航船
     * @return 航船
     */
    @Override
    public List<Boat> selectBoatList(Boat boat)
    {
        return boatMapper.selectBoatList(boat);
    }

    /**
     * 新增航船
     * 
     * @param boat 航船
     * @return 结果
     */
    @Override
    public int insertBoat(Boat boat)
    {
        return boatMapper.insertBoat(boat);
    }

    /**
     * 修改航船
     * 
     * @param boat 航船
     * @return 结果
     */
    @Override
    public int updateBoat(Boat boat)
    {
        return boatMapper.updateBoat(boat);
    }

    /**
     * 批量删除航船
     * 
     * @param ids 需要删除的航船ID
     * @return 结果
     */
    @Override
    public int deleteBoatByIds(Long[] ids)
    {
        return boatMapper.deleteBoatByIds(ids);
    }

    /**
     * 删除航船信息
     * 
     * @param id 航船ID
     * @return 结果
     */
    @Override
    public int deleteBoatById(Long id)
    {
        return boatMapper.deleteBoatById(id);
    }
}
