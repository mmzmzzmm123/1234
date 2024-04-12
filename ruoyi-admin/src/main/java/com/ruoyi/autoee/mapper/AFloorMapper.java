package com.ruoyi.autoee.mapper;

import java.util.List;
import com.ruoyi.autoee.domain.AFloor;

/**
 * 楼层管理Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
public interface AFloorMapper 
{
    /**
     * 查询楼层管理
     * 
     * @param id 楼层管理主键
     * @return 楼层管理
     */
    public AFloor selectAFloorById(Long id);

    /**
     * 查询楼层管理列表
     * 
     * @param aFloor 楼层管理
     * @return 楼层管理集合
     */
    public List<AFloor> selectAFloorList(AFloor aFloor);

    /**
     * 新增楼层管理
     * 
     * @param aFloor 楼层管理
     * @return 结果
     */
    public int insertAFloor(AFloor aFloor);

    /**
     * 修改楼层管理
     * 
     * @param aFloor 楼层管理
     * @return 结果
     */
    public int updateAFloor(AFloor aFloor);

    /**
     * 删除楼层管理
     * 
     * @param id 楼层管理主键
     * @return 结果
     */
    public int deleteAFloorById(Long id);

    /**
     * 批量删除楼层管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAFloorByIds(Long[] ids);
}
