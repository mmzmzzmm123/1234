package com.onethinker.onethinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.onethinker.domain.Banner;
import com.onethinker.onethinker.mapper.BannerMapper;
import com.onethinker.onethinker.service.IBannerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@Service
@Log4j2
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Resource
    private BannerMapper bannerMapper;

    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    @Override
    public Banner selectBannerById(Long id) {
        return bannerMapper.selectBannerById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图
     */
    @Override
    public List<Banner> selectBannerList(Banner banner) {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner) {
                banner.setCreateTime(DateUtils.getNowDate());
            return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner) {
                banner.setUpdateTime(DateUtils.getNowDate());
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] ids) {
        return bannerMapper.deleteBannerByIds(ids);
    }

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long id) {
        return bannerMapper.deleteBannerById(id);
    }
}
