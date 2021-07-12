package com.apollo.usv.mapper;

import java.util.List;
import com.apollo.usv.domain.Boat;

/**
 * 航船Mapper接口
 * 
 * @author lwq
 * @date 2021-07-02
 */
public interface BoatMapper 
{
    /**
     * 查询航船
     * 
     * @param id 航船ID
     * @return 航船
     */
    public Boat selectBoatById(Long id);

    /**
     * 查询航船列表
     * 
     * @param boat 航船
     * @return 航船集合
     */
    public List<Boat> selectBoatList(Boat boat);

    /**
     * 新增航船
     * 
     * @param boat 航船
     * @return 结果
     */
    public int insertBoat(Boat boat);

    /**
     * 修改航船
     * 
     * @param boat 航船
     * @return 结果
     */
    public int updateBoat(Boat boat);

    /**
     * 删除航船
     * 
     * @param id 航船ID
     * @return 结果
     */
    public int deleteBoatById(Long id);

    /**
     * 批量删除航船
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBoatByIds(Long[] ids);
}
