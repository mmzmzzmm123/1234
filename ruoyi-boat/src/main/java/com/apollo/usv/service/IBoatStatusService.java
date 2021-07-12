package com.apollo.usv.service;

import java.util.List;
import com.apollo.usv.domain.BoatStatus;

/**
 * 航船状态Service接口
 * 
 * @author lwq
 * @date 2021-07-02
 */
public interface IBoatStatusService 
{
    /**
     * 查询航船状态
     * 
     * @param id 航船状态ID
     * @return 航船状态
     */
    public BoatStatus selectBoatStatusById(Long id);

    /**
     * 查询航船状态列表
     * 
     * @param boatStatus 航船状态
     * @return 航船状态集合
     */
    public List<BoatStatus> selectBoatStatusList(BoatStatus boatStatus);

    /**
     * 新增航船状态
     * 
     * @param boatStatus 航船状态
     * @return 结果
     */
    public int insertBoatStatus(BoatStatus boatStatus);

    /**
     * 修改航船状态
     * 
     * @param boatStatus 航船状态
     * @return 结果
     */
    public int updateBoatStatus(BoatStatus boatStatus);

    /**
     * 批量删除航船状态
     * 
     * @param ids 需要删除的航船状态ID
     * @return 结果
     */
    public int deleteBoatStatusByIds(Long[] ids);

    /**
     * 删除航船状态信息
     * 
     * @param id 航船状态ID
     * @return 结果
     */
    public int deleteBoatStatusById(Long id);
}
