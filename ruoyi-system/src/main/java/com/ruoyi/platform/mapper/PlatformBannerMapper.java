package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.PlatformBanner;
import org.apache.ibatis.annotations.Param;

/**
 * 平台广告图Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface PlatformBannerMapper {

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
     * 批量修改状态
     *
     * @param ids id集合
     * @param state 状态
     * @return 结果
     * */
    public int updateStateByIds(@Param("ids") List<Long> ids, @Param("state") String state);

    /**
     * 删除平台广告图
     *
     * @param id 平台广告图主键
     * @return 结果
     */
    public int deletePlatformBannerById(Long id);

    /**
     * 批量删除平台广告图
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformBannerByIds(Long[] ids);
}
