package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysWxBannerImage;
import com.stdiet.custom.dto.response.BannerResponse;

/**
 * 小程序banner图Mapper接口
 *
 * @author xzj
 * @date 2021-07-13
 */
public interface SysWxBannerImageMapper
{
    /**
     * 查询小程序banner图
     *
     * @param id 小程序banner图ID
     * @return 小程序banner图
     */
    public SysWxBannerImage selectSysWxBannerImageById(Long id);

    /**
     * 查询小程序banner图列表
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 小程序banner图集合
     */
    public List<SysWxBannerImage> selectSysWxBannerImageList(SysWxBannerImage sysWxBannerImage);

    /**
     * 新增小程序banner图
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 结果
     */
    public int insertSysWxBannerImage(SysWxBannerImage sysWxBannerImage);

    /**
     * 修改小程序banner图
     *
     * @param sysWxBannerImage 小程序banner图
     * @return 结果
     */
    public int updateSysWxBannerImage(SysWxBannerImage sysWxBannerImage);

    /**
     * 删除小程序banner图
     *
     * @param id 小程序banner图ID
     * @return 结果
     */
    public int deleteSysWxBannerImageById(Long id);

    /**
     * 批量删除小程序banner图
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWxBannerImageByIds(Long[] ids);

    /**
     * 根据优先级排序获取banner图
     * @return
     */
    List<BannerResponse> getBannerListOrderByOrderNum(SysWxBannerImage sysWxBannerImage);
}