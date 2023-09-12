package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.PlatformBanner;

/**
 * 平台广告图Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IPlatformBannerService {

    /**
     * 查询平台广告图
     *
     * @param id 平台广告图主键
     * @return 平台广告图
     */
    public PlatformBanner selectPlatformBannerById(Long id);

    /**
     * 查询平台广告图列表
     *
     * @param platformBanner 平台广告图
     * @return 平台广告图集合
     */
    public List<PlatformBanner> selectPlatformBannerList(PlatformBanner platformBanner);

    /**
     * 新增平台广告图
     *
     * @param platformBanner 平台广告图
     * @return 结果
     */
    public int insertPlatformBanner(PlatformBanner platformBanner);

    /**
     * 修改平台广告图
     *
     * @param platformBanner 平台广告图
     * @return 结果
     */
    public int updatePlatformBanner(PlatformBanner platformBanner);

    /**
     * 批量删除平台广告图
     *
     * @param ids 需要删除的平台广告图主键集合
     * @return 结果
     */
    public int deletePlatformBannerByIds(Long[] ids);

    /**
     * 删除平台广告图信息
     *
     * @param id 平台广告图主键
     * @return 结果
     */
    public int deletePlatformBannerById(Long id);
}
