package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BlSysCarousel;

/**
 * 轮播图管理Mapper接口
 * 
 * @author niujs
 * @date 2024-04-10
 */
public interface BlSysCarouselMapper 
{
    /**
     * 查询轮播图管理
     * 
     * @param id 轮播图管理主键
     * @return 轮播图管理
     */
    public BlSysCarousel selectBlSysCarouselById(Long id);

    /**
     * 查询轮播图管理列表
     * 
     * @param blSysCarousel 轮播图管理
     * @return 轮播图管理集合
     */
    public List<BlSysCarousel> selectBlSysCarouselList(BlSysCarousel blSysCarousel);

    /**
     * 新增轮播图管理
     * 
     * @param blSysCarousel 轮播图管理
     * @return 结果
     */
    public int insertBlSysCarousel(BlSysCarousel blSysCarousel);

    /**
     * 修改轮播图管理
     * 
     * @param blSysCarousel 轮播图管理
     * @return 结果
     */
    public int updateBlSysCarousel(BlSysCarousel blSysCarousel);

    /**
     * 删除轮播图管理
     * 
     * @param id 轮播图管理主键
     * @return 结果
     */
    public int deleteBlSysCarouselById(Long id);

    /**
     * 批量删除轮播图管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlSysCarouselByIds(Long[] ids);
}
