package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BannerMapper;
import com.ruoyi.system.domain.Banner;
import com.ruoyi.system.service.IBannerService;

/**
 * banner管理Service业务层处理
 *
 * @author carol
 * @date 2024-03-06
 */
@Service
public class BannerServiceImpl implements IBannerService
{
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 查询banner管理
     *
     * @param id banner管理主键
     * @return banner管理
     */
    @Override
    public Banner selectBannerById(Long id)
    {
        return bannerMapper.selectBannerById(id);
    }

    /**
     * 查询banner管理列表
     *
     * @param banner banner管理
     * @return banner管理
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增banner管理
     *
     * @param banner banner管理
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setCreateTime(DateUtils.getNowDate());
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改banner管理
     *
     * @param banner banner管理
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除banner管理
     *
     * @param ids 需要删除的banner管理主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] ids)
    {
        return bannerMapper.deleteBannerByIds(ids);
    }

    /**
     * 删除banner管理信息
     *
     * @param id banner管理主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long id)
    {
        return bannerMapper.deleteBannerById(id);
    }

    @Override
    public List<Banner> selectBannerByScene(int scene) {

        return bannerMapper.selectByScene(scene);
    }
}
