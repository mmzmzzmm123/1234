package com.ruoyi.abuwx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.abuwx.mapper.AbucoderBannersMapper;
import com.ruoyi.abuwx.domain.AbucoderBanners;
import com.ruoyi.abuwx.service.IAbucoderBannersService;

/**
 * 轮播图管理Service业务层处理
 * 
 * @author 阿卜Coder QQ932696181
 * @date 2022-06-26
 */
@Service
public class AbucoderBannersServiceImpl implements IAbucoderBannersService 
{
    @Autowired
    private AbucoderBannersMapper abucoderBannersMapper;

    /**
     * 查询轮播图管理
     * 
     * @param id 轮播图管理主键
     * @return 轮播图管理
     */
    @Override
    public AbucoderBanners selectAbucoderBannersById(Long id)
    {
        return abucoderBannersMapper.selectAbucoderBannersById(id);
    }

    /**
     * 查询轮播图管理列表
     * 
     * @param abucoderBanners 轮播图管理
     * @return 轮播图管理
     */
    @Override
    public List<AbucoderBanners> selectAbucoderBannersList(AbucoderBanners abucoderBanners)
    {
        return abucoderBannersMapper.selectAbucoderBannersList(abucoderBanners);
    }

    /**
     * 新增轮播图管理
     * 
     * @param abucoderBanners 轮播图管理
     * @return 结果
     */
    @Override
    public int insertAbucoderBanners(AbucoderBanners abucoderBanners)
    {
        abucoderBanners.setCreateTime(DateUtils.getNowDate());
        return abucoderBannersMapper.insertAbucoderBanners(abucoderBanners);
    }

    /**
     * 修改轮播图管理
     * 
     * @param abucoderBanners 轮播图管理
     * @return 结果
     */
    @Override
    public int updateAbucoderBanners(AbucoderBanners abucoderBanners)
    {
        abucoderBanners.setUpdateTime(DateUtils.getNowDate());
        return abucoderBannersMapper.updateAbucoderBanners(abucoderBanners);
    }

    /**
     * 批量删除轮播图管理
     * 
     * @param ids 需要删除的轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteAbucoderBannersByIds(Long[] ids)
    {
        return abucoderBannersMapper.deleteAbucoderBannersByIds(ids);
    }

    /**
     * 删除轮播图管理信息
     * 
     * @param id 轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteAbucoderBannersById(Long id)
    {
        return abucoderBannersMapper.deleteAbucoderBannersById(id);
    }
}
