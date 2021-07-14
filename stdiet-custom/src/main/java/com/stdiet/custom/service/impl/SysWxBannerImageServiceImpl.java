package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.dto.response.BannerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxBannerImageMapper;
import com.stdiet.custom.domain.SysWxBannerImage;
import com.stdiet.custom.service.ISysWxBannerImageService;

/**
 * 小程序banner图Service业务层处理
 *
 * @author xzj
 * @date 2021-07-13
 */
@Service
public class SysWxBannerImageServiceImpl implements ISysWxBannerImageService
{
    @Autowired
    private SysWxBannerImageMapper sysWxBannerImageMapper;

    /**
     * 查询小程序banner图
     *
     * @param id 小程序banner图ID
     * @return 小程序banner图
     */
    @Override
    public SysWxBannerImage selectSysWxBannerImageById(Long id)
    {
        return sysWxBannerImageMapper.selectSysWxBannerImageById(id);
    }

    /**
     * 查询小程序banner图列表
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 小程序banner图
     */
    @Override
    public List<SysWxBannerImage> selectSysWxBannerImageList(SysWxBannerImage sysWxBannerImage)
    {
        return sysWxBannerImageMapper.selectSysWxBannerImageList(sysWxBannerImage);
    }

    /**
     * 新增小程序banner图
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 结果
     */
    @Override
    public int insertSysWxBannerImage(SysWxBannerImage sysWxBannerImage)
    {
        sysWxBannerImage.setCreateTime(DateUtils.getNowDate());
        return sysWxBannerImageMapper.insertSysWxBannerImage(sysWxBannerImage);
    }

    /**
     * 修改小程序banner图
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 结果
     */
    @Override
    public int updateSysWxBannerImage(SysWxBannerImage sysWxBannerImage)
    {
        sysWxBannerImage.setUpdateTime(DateUtils.getNowDate());
        return sysWxBannerImageMapper.updateSysWxBannerImage(sysWxBannerImage);
    }

    /**
     * 批量删除小程序banner图
     *
     * @param ids 需要删除的小程序banner图ID
     * @return 结果
     */
    @Override
    public int deleteSysWxBannerImageByIds(Long[] ids)
    {
        return sysWxBannerImageMapper.deleteSysWxBannerImageByIds(ids);
    }

    /**
     * 删除小程序banner图信息
     *
     * @param id 小程序banner图ID
     * @return 结果
     */
    @Override
    public int deleteSysWxBannerImageById(Long id)
    {
        return sysWxBannerImageMapper.deleteSysWxBannerImageById(id);
    }

    /**
     * 根据优先级排序获取banner图
     * @return
     */
    @Override
    public List<BannerResponse> getBannerListOrderByOrderNum(SysWxBannerImage sysWxBannerImage){
        return sysWxBannerImageMapper.getBannerListOrderByOrderNum(sysWxBannerImage);
    }
}