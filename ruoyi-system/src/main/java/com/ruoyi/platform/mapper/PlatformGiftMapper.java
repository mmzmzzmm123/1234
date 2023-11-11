package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.PlatformGift;

/**
 * 平台礼物管理Mapper接口
 *
 * @author Lam
 * @date 2023-09-12
 */
public interface PlatformGiftMapper {

    /**
     * 查询平台礼物管理
     *
     * @param id 平台礼物管理主键
     * @return 平台礼物管理
     */
    public PlatformGift selectPlatformGiftById(Long id);

    /**
     * 查询平台礼物管理列表
     *
     * @param platformGift 平台礼物管理
     * @return 平台礼物管理集合
     */
    public List<PlatformGift> selectPlatformGiftList(PlatformGift platformGift);

    /**
     * 新增平台礼物管理
     *
     * @param platformGift 平台礼物管理
     * @return 结果
     */
    public int insertPlatformGift(PlatformGift platformGift);

    /**
     * 修改平台礼物管理
     *
     * @param platformGift 平台礼物管理
     * @return 结果
     */
    public int updatePlatformGift(PlatformGift platformGift);

    /**
     * 删除平台礼物管理
     *
     * @param id 平台礼物管理主键
     * @return 结果
     */
    public int deletePlatformGiftById(Long id);

    /**
     * 批量删除平台礼物管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformGiftByIds(Long[] ids);
}
