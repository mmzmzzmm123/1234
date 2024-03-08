package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Banner;

/**
 * banner管理Service接口
 *
 * @author carol
 * @date 2024-03-06
 */
public interface IBannerService
{
    /**
     * 查询banner管理
     *
     * @param id banner管理主键
     * @return banner管理
     */
    public Banner selectBannerById(Long id);

    /**
     * 查询banner管理列表
     *
     * @param banner banner管理
     * @return banner管理集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增banner管理
     *
     * @param banner banner管理
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改banner管理
     *
     * @param banner banner管理
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 批量删除banner管理
     *
     * @param ids 需要删除的banner管理主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] ids);

    /**
     * 删除banner管理信息
     *
     * @param id banner管理主键
     * @return 结果
     */
    public int deleteBannerById(Long id);

    /**
     * 通过场景获取banner
     * @param scene
     * @return
     */
    List<Banner> selectBannerByScene(int scene);
}
