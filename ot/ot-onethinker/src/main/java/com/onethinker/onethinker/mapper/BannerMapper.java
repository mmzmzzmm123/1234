package com.onethinker.onethinker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.onethinker.domain.Banner;

import java.util.List;

/**
 * 轮播图Mapper接口
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
public interface BannerMapper extends BaseMapper<Banner> {
    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    public Banner selectBannerById(Long id);

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 删除轮播图
     *
     * @param id 轮播图主键
     * @return 结果
     */
    public int deleteBannerById(Long id);

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] ids);
}
